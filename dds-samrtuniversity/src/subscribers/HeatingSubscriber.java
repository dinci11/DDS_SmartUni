package subscribers;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import gen.*;

public class HeatingSubscriber extends SubscriberBase<HeatingDataReader, HeatingSubscriber.IHeater> {

    public HeatingSubscriber(int domainID, String topicName, IHeater heater)
    {
        super(domainID, topicName, heater);
    }

    @Override
    protected void createListener() {
        listener = new HeatingListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = HeatingTypeSupport.get_type_name();
        HeatingTypeSupport.register_type(participant, typeName);
    }

    public interface IHeater {
        void heating(double intensity);
    }

    public class HeatingListener extends DataReaderAdapter {

        HeatingSeq heatingSeq;
        SampleInfoSeq infoSeq;
        String typeName;
        IHeater heater;

        public HeatingListener(String typeName, IHeater heater) {
            heatingSeq = new HeatingSeq();
            infoSeq = new SampleInfoSeq();
            this.typeName = typeName;
            this.heater = heater;
        }

        public void on_data_available(DataReader reader) {
            HeatingDataReader heatingDataReader = (HeatingDataReader) reader;

            try {
                heatingDataReader.take(
                        heatingSeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);

                for (int i = 0; i < heatingSeq.size(); i++) {
                    SampleInfo info = infoSeq.get(i);

                    if (info.valid_data) {
                        Heating data = heatingSeq.get(i);
                        System.out.println(typeName + " --> Intesity: " + data.intensity);
                        heater.heating(data.intensity);
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No data to process
            } finally {
                heatingDataReader.return_loan(heatingSeq, infoSeq);
            }
        }
    }

}

