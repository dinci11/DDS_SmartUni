package phone_data;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import subscribers.SubscriberBase;

public class PhoneDataSubscriber extends SubscriberBase<PhoneDataDataReader, PhoneDataSubscriber.IPhoneDataDataAvailable> {

    public PhoneDataSubscriber(int domainID, String topicName, PhoneDataSubscriber.IPhoneDataDataAvailable handler) {
        super(domainID, topicName, handler);

    }

    @Override
    protected void createListener() {
        listener = new PhoneDataSubscriber.PhoneDataListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = PhoneDataTypeSupport.get_type_name();
        PhoneDataTypeSupport.register_type(participant, typeName);
    }

    public class PhoneDataListener extends DataReaderAdapter {

        PhoneDataSeq phoneDataSeq;
        SampleInfoSeq infoSeq;
        String typeName;
        public PhoneDataSubscriber.IPhoneDataDataAvailable handler;

        public PhoneDataListener(String typeName, PhoneDataSubscriber.IPhoneDataDataAvailable handler) {
            this.typeName = typeName;
            phoneDataSeq = new PhoneDataSeq();
            infoSeq = new SampleInfoSeq();
            this.handler = handler;
        }


        public void on_data_available(DataReader reader) {
            PhoneDataDataReader phoneDataDataReader = (PhoneDataDataReader) reader;

            try {
                phoneDataDataReader.take(
                        phoneDataSeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);

                for (int i = 0; i < phoneDataSeq.size(); i++) {
                    SampleInfo info = infoSeq.get(i);

                    if (info.valid_data) {
                        PhoneData data = phoneDataSeq.get(i);
                        System.out.println(typeName + " --> Data: " + data.deviceID + " - " + data.temperature + " - " + data.humidity + " - " + data.timeStamp);
                        handler.onPhoneDataDataAvailable(data);
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No data to process
            } finally {
                phoneDataDataReader.return_loan(phoneDataSeq, infoSeq);
            }
        }
    }

    public interface IPhoneDataDataAvailable {
        void onPhoneDataDataAvailable(PhoneData phoneData);
    }

}
