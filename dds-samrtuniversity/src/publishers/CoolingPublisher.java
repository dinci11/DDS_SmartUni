package publishers;

import com.rti.dds.infrastructure.InstanceHandle_t;
import gen.Cooling;
import gen.CoolingDataWriter;
import gen.CoolingTypeSupport;

public class CoolingPublisher extends PublisherBase<CoolingDataWriter> {

    public CoolingPublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = CoolingTypeSupport.get_type_name();
        CoolingTypeSupport.register_type(participant, typeName);
    }

    public void WriteCoolingData(double intensity){
        Cooling coolingInstance = new Cooling();
        coolingInstance.intensity = intensity;
        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(coolingInstance,instanceHandle);
        System.out.println("Publishing: "+intensity);
    }
}
