package subscribers;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import gen.*;

public class CoolingSubscriber extends SubscriberBase<CoolingDataReader, CoolingSubscriber.ICooler> {


    public CoolingSubscriber(int domainID, String topicName, ICooler cooler) {
        super(domainID, topicName, cooler);
    }

    @Override
    protected void createListener() {
        listener = new CoolingListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = CoolingTypeSupport.get_type_name();
        CoolingTypeSupport.register_type(participant, typeName);
    }

    public interface ICooler{
        void cooling(double intensity);
    }

    private class CoolingListener extends DataReaderAdapter {

        CoolingSeq coolingSeq;
        SampleInfoSeq infoSeq;
        String typeName;
        ICooler cooler;

        public CoolingListener(String typeName, ICooler cooler){
            coolingSeq = new CoolingSeq();
            infoSeq = new SampleInfoSeq();
            this.typeName = typeName;
            this.cooler = cooler;
        }

        public void on_data_available(DataReader dataReader){
            CoolingDataReader coolingDataReader = (CoolingDataReader) dataReader;
            try {
                coolingDataReader.take(
                        coolingSeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);
                for (int i =0; i<coolingSeq.size();i++){
                    SampleInfo info =  infoSeq.get(i);

                    if(info.valid_data){
                        Cooling data = coolingSeq.get(i);
                        System.out.println(typeName+" --> Intesity: "+data.intensity);
                        cooler.cooling(data.intensity);
                    }
                }
            }
            catch (RETCODE_NO_DATA noData){
                System.out.printf("No data to process");
            }
            finally {
                coolingDataReader.return_loan(coolingSeq, infoSeq);
            }
        }
    }
}
