package subscribers;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import gen.Humidity;
import gen.HumidityDataReader;
import gen.HumiditySeq;
import gen.HumidityTypeSupport;

import java.util.Date;

public class HumiditySubscriber extends SubscriberBase<HumidityDataReader, HumiditySubscriber.IHumidityDataAvailable> {

    public HumiditySubscriber(int domainID, String topicName,  IHumidityDataAvailable handler) {
        super(domainID, topicName, handler);

    }

    @Override
    protected void createListener() {
        listener = new HumidityListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = HumidityTypeSupport.get_type_name();
        HumidityTypeSupport.register_type(participant, typeName);
    }

    public class HumidityListener extends DataReaderAdapter {

        HumiditySeq humiditySeq;
        SampleInfoSeq infoSeq;
        String typeName;
        public IHumidityDataAvailable handler;

        public HumidityListener(String typeName,  IHumidityDataAvailable handler) {
            this.typeName = typeName;
            humiditySeq = new HumiditySeq();
            infoSeq = new SampleInfoSeq();
            this.handler = handler;
        }


        public void on_data_available(DataReader reader) {
            HumidityDataReader humidityDataReader = (HumidityDataReader) reader;

            try {
                humidityDataReader.take(
                        humiditySeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);

                for (int i = 0; i < humiditySeq.size(); i++) {
                    SampleInfo info = infoSeq.get(i);

                    if (info.valid_data) {
                        Humidity data = humiditySeq.get(i);
                        System.out.println(typeName+" --> Data: " + data.value + " - " + data.timeStamp);
                        handler.onHumidityDataAvailable(data);
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No data to process
            } finally {
                humidityDataReader.return_loan(humiditySeq, infoSeq);
            }
        }
    }

    public interface IHumidityDataAvailable{
        void onHumidityDataAvailable(Humidity humidity);
    }

}
