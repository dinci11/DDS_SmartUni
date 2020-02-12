package publishers;

import com.rti.dds.infrastructure.InstanceHandle_t;
import gen.Temperature;
import gen.TemperatureDataWriter;
import gen.TemperatureTypeSupport;

import java.util.Date;

public class TemperaturePublisher extends PublisherBase<TemperatureDataWriter> {

    public TemperaturePublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = TemperatureTypeSupport.get_type_name();
        TemperatureTypeSupport.register_type(participant, typeName);
    }

    public void WriteTemperatureData(int value, Date timeStamp){
        Temperature temperatureInstance = new Temperature();
        temperatureInstance.value = value;
        temperatureInstance.timeStamp = timeStamp.getTime();
        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(temperatureInstance,instanceHandle);
        System.out.println("Publishing: "+value+" - "+timeStamp);
    }
}
