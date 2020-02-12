package publishers;

import com.rti.dds.infrastructure.InstanceHandle_t;
import gen.Cooling;
import gen.Heating;
import gen.HeatingDataWriter;
import gen.HeatingTypeSupport;

public class HeatingPublisher extends PublisherBase<HeatingDataWriter>{
    public HeatingPublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = HeatingTypeSupport.get_type_name();
        HeatingTypeSupport.register_type(participant, typeName);
    }

    public void WriteHeatingData(double intensity){
        Heating instance = new Heating();
        instance.intensity = intensity;
        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(instance,instanceHandle);
        System.out.println("Publishing: "+intensity);
    }
}
