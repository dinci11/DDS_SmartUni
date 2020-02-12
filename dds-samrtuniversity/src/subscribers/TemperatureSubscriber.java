package subscribers;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import gen.Temperature;
import gen.TemperatureDataReader;
import gen.TemperatureSeq;
import gen.TemperatureTypeSupport;

import java.util.Date;

public class TemperatureSubscriber extends SubscriberBase<TemperatureDataReader, TemperatureSubscriber.ITemperatureDataAvailable> {


    public TemperatureSubscriber(int domainID, String topicName,  ITemperatureDataAvailable handler) {
        super(domainID, topicName, handler);
    }

    @Override
    protected void createListener() {
        listener = new TemperatureSubscriber.TemperatureListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = TemperatureTypeSupport.get_type_name();
        TemperatureTypeSupport.register_type(participant, typeName);
    }

    public class TemperatureListener extends DataReaderAdapter {

        TemperatureSeq temperatureSeq;
        SampleInfoSeq infoSeq;
        String typeName;
        ITemperatureDataAvailable handler;

        public TemperatureListener(String typeName,  ITemperatureDataAvailable handler) {
            this.typeName = typeName;
            temperatureSeq = new TemperatureSeq();
            infoSeq = new SampleInfoSeq();
            this.handler = handler;
        }

        public void on_data_available(DataReader reader) {
            TemperatureDataReader temperatureDataReader = (TemperatureDataReader) reader;

            try {
                temperatureDataReader.take(
                        temperatureSeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);

                for (int i = 0; i < temperatureSeq.size(); i++) {
                    SampleInfo info = infoSeq.get(i);

                    if (info.valid_data) {
                        Temperature data = temperatureSeq.get(i);
                        System.out.println(typeName+" --> Data: " + data.value + " - " + data.timeStamp);
                        handler.onTemperatureDataAvailable(data);
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No data to process
            } finally {
                temperatureDataReader.return_loan(temperatureSeq, infoSeq);
            }
        }
    }

    public interface ITemperatureDataAvailable{
        void onTemperatureDataAvailable(Temperature temperature);
    }

}
