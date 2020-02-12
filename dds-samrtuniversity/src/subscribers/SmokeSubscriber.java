package subscribers;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import gen.Smoke;
import gen.SmokeDataReader;
import gen.SmokeSeq;
import gen.SmokeTypeSupport;

import java.util.Date;

public class SmokeSubscriber extends SubscriberBase<SmokeDataReader, SmokeSubscriber.ISmokeDataAvailable> {

    public SmokeSubscriber(int domainID, String topicName,  ISmokeDataAvailable handler) {
        super(domainID, topicName, handler);
    }

    @Override
    protected void createListener() {
        listener = new SmokeSubscriber.SmokeListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = SmokeTypeSupport.get_type_name();
        SmokeTypeSupport.register_type(participant, typeName);
    }

    public class SmokeListener extends DataReaderAdapter {

        SmokeSeq smokeSeq;
        SampleInfoSeq infoSeq;
        String typeName;
        ISmokeDataAvailable handler;

        public SmokeListener(String typeName,  ISmokeDataAvailable handler) {
            this.typeName = typeName;
            smokeSeq = new SmokeSeq();
            infoSeq = new SampleInfoSeq();
            this.handler = handler;
        }

        public void on_data_available(DataReader reader) {
            SmokeDataReader smokeDataReader = (SmokeDataReader) reader;

            try {
                smokeDataReader.take(
                        smokeSeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);

                for (int i = 0; i < smokeSeq.size(); i++) {
                    SampleInfo info = infoSeq.get(i);

                    if (info.valid_data) {
                        Smoke data = smokeSeq.get(i);
                        System.out.println(typeName+" --> Data: " + data.value + " - " + data.timeStamp);
                        handler.onSmokeDataAvailable(data);
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No data to process
            } finally {
                smokeDataReader.return_loan(smokeSeq, infoSeq);
            }
        }
    }

    public interface ISmokeDataAvailable{
        void onSmokeDataAvailable(Smoke smoke);
    }

}
