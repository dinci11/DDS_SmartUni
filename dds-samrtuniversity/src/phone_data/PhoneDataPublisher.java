package phone_data;

import com.rti.dds.infrastructure.InstanceHandle_t;
import publishers.PublisherBase;

import java.util.Date;

public class PhoneDataPublisher extends PublisherBase<PhoneDataDataWriter> {
    public PhoneDataPublisher(int domainID, String topicName) {
        super(domainID, topicName);
    }

    @Override
    protected void setTypeName() {
        typeName = PhoneDataTypeSupport.get_type_name();
        PhoneDataTypeSupport.register_type(participant, typeName);
    }

    public void WritePhoneData(int deviceID, double temperature, double humidity, Date timeStamp){
        PhoneData data = new PhoneData();
        data.deviceID = deviceID;
        data.temperature = temperature;
        data.humidity = humidity;
        data.timeStamp = timeStamp.getTime();

        InstanceHandle_t instanceHandle = InstanceHandle_t.HANDLE_NIL;
        writer.write(data, instanceHandle);
        System.out.println("Publishing: "+temperature+", "+ humidity);
    }
}
