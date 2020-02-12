package publishers;

import com.rti.dds.infrastructure.InstanceHandle_t;
import gen.Humidity;
import gen.HumidityDataWriter;
import gen.HumidityTypeSupport;

import java.util.Date;

public class HumidityPublisher extends PublisherBase<HumidityDataWriter> {

    public HumidityPublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = HumidityTypeSupport.get_type_name();
        HumidityTypeSupport.register_type(participant, typeName);
    }

    public void WriteHumidityData(int value, Date timeStamp){
        Humidity humidityInstance = new Humidity();
        humidityInstance.value = value;
        humidityInstance.timeStamp = timeStamp.getTime();
        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(humidityInstance,instanceHandle);
        System.out.println("Publishing: "+value+" - "+timeStamp);
    }
}
