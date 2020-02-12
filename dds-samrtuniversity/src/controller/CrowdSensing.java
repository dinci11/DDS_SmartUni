package controller;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CrowdSensing implements Runnable {

    private static InfluxDB influxDB;
    private static final String dbURL = "http://localhost:8086";
    private static final String dbUsername = "app";
    private static final String dbPassword = "app";
    private static final String dbName = "smartuniversity";
    private static final String phoneDataMeasurementName = "crowd";
    private static boolean isDBAlive = false;
    private boolean stop;

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int MAGIC = 1000000;
    private long meanTime = 0L;

    KMeansPlusPlusClusterer<DoublePoint> clusterer;

    public CrowdSensing() {
        stop = false;
        clusterer = new KMeansPlusPlusClusterer<>(2, 50);
        influxDB = InfluxDBFactory.connect(dbURL, dbUsername, dbPassword);
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            throw new InfluxDBException("Database NOT found!");
        }
        isDBAlive = true;
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        influxDB.setDatabase(dbName);
        influxDB.enableBatch(BatchOptions.DEFAULTS);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                System.out.println("Crowd sensing...");
                double averageTemp = 0;
                double averageHum = 0;
                long minTime = (System.currentTimeMillis() - (10 * SECOND)) * MAGIC;
                String query = "SELECT MEDIAN(temp), MEDIAN(hum) FROM crowd WHERE time>" + minTime+" GROUP BY device";
//                String query = "SELECT MEDIAN(temp), MEDIAN(hum) FROM crowd WHERE time>1578512073658000000 AND time<1578512396548000000 GROUP BY device";
                System.out.println("Query: " + query);
                QueryResult queryResult = influxDB.query(new Query(query, dbName));
                List<QueryResult.Result> results = queryResult.getResults();

                ArrayList<DoublePoint> temperatures = new ArrayList<>();
                ArrayList<DoublePoint> humidities = new ArrayList<>();
                meanTime = System.currentTimeMillis() - 5 * SECOND;

                if (results.get(0).getSeries() != null) {
                    for (QueryResult.Series device : results.get(0).getSeries()) {

                        List<String> collumns = device.getColumns();
                        int TEMPERATURE = collumns.indexOf("median");
                        int HUMIDITY = collumns.indexOf("median_1");
                        int TIME = collumns.indexOf("time");
                        int DEVICE = collumns.indexOf("device");

                        List<Object> values = device.getValues().get(0);
                        double temp = (double) values.get(TEMPERATURE);
                        double hum = (double) values.get(HUMIDITY);

                        temperatures.add(new DoublePoint(new double[]{temp}));
                        humidities.add(new DoublePoint(new double[]{hum}));

                    }

                    Clustering(temperatures, humidities);
                } else {
                    System.out.println("No query result");
                }
                Thread.sleep(3 * SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void Clustering(ArrayList<DoublePoint> temperatures, ArrayList<DoublePoint> humidities) {

        try {
            var temperatureClusters = clusterer.cluster(temperatures);
            var humidityClusters = clusterer.cluster(humidities);
            System.out.println("Clusterd");
            int clusterCounter = 1;
            for (var cluster : temperatureClusters) {
                double tempCenter = cluster.getCenter().getPoint()[0];
                int tempCount = cluster.getPoints().size();
                addToDB("temp", "cluster"+clusterCounter, tempCenter, tempCount);
                System.out.println(tempCenter + " - " + tempCount);
                clusterCounter++;
            }
            clusterCounter = 1;
            for (var cluster : humidityClusters) {
                double humCenter = cluster.getCenter().getPoint()[0];
                int humCount = cluster.getPoints().size();
                addToDB("hum", "cluster" + clusterCounter, humCenter, humCount);
                System.out.println(humCenter + " - " + humCount);
                clusterCounter++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToDB(String dataType, String clusterName, double center, int count) {
        influxDB.write(Point.measurement(dataType+"cluster")
                .time(meanTime, TimeUnit.MILLISECONDS)
                .tag("cluster",clusterName)
                .addField("center", center)
                .addField("count", count)
                .build());
    }

    private Double getMedian(ArrayList<Double> values) {
        Double median = 0.0;

        values.sort((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else
                return 0;
        });

        if (values.size() % 2 == 0) {
            median = (values.get(values.size() / 2) + values.get((values.size() / 2) - 1)) / 2;
        } else {
            median = values.get(values.size() / 2);
        }

        return median;
    }

    public void Stop() {
        stop = true;
        influxDB.close();
    }
}
