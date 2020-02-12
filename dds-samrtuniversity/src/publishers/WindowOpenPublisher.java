package publishers;

import com.rti.dds.infrastructure.InstanceHandle_t;
import gen.WindowOpen;
import gen.WindowOpenDataWriter;
import gen.WindowOpenTypeSupport;

public class WindowOpenPublisher extends PublisherBase<WindowOpenDataWriter> {

    public WindowOpenPublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = WindowOpenTypeSupport.get_type_name();
        WindowOpenTypeSupport.register_type(participant, typeName);
    }

    public void WriteWindowOpenData(double intensity){
        WindowOpen windowOpenInstance = new WindowOpen();
        windowOpenInstance.intensity = intensity;
        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(windowOpenInstance,instanceHandle);
        System.out.println("Publishing: "+intensity);
    }
}
