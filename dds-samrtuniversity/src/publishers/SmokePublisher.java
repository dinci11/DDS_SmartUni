package publishers;

import com.rti.dds.infrastructure.InstanceHandle_t;
import gen.Smoke;
import gen.SmokeDataWriter;
import gen.SmokeTypeSupport;

import java.util.Date;

public class SmokePublisher extends PublisherBase<SmokeDataWriter> {

    public SmokePublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = SmokeTypeSupport.get_type_name();
        SmokeTypeSupport.register_type(participant, typeName);
    }

    public void WriteSmokeData(int value, Date timeStamp){
        Smoke smokeInstance = new Smoke();
        smokeInstance.value = value;
        smokeInstance.timeStamp = timeStamp.getTime();
        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(smokeInstance,instanceHandle);
        System.out.println("Publishing: "+value+" - "+timeStamp);
    }
}
