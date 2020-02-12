

/*
WARNING: THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.

This file was generated from .idl using "rtiddsgen".
The rtiddsgen tool is part of the RTI Connext distribution.
For more information, type 'rtiddsgen -help' at a command shell
or consult the RTI Connext manual.
*/

package phone_data;

import com.rti.dds.infrastructure.*;
import com.rti.dds.infrastructure.Copyable;
import java.io.Serializable;
import com.rti.dds.cdr.CdrHelper;

public class PhoneData   implements Copyable, Serializable{

    public double temperature = (double)0;
    public double humidity = (double)0;
    public long timeStamp = (long)0;
    public int deviceID = (int)0;

    public PhoneData() {

    }
    public PhoneData (PhoneData other) {

        this();
        copy_from(other);
    }

    public static Object create() {

        PhoneData self;
        self = new  PhoneData();
        self.clear();
        return self;

    }

    public void clear() {

        temperature = (double)0;
        humidity = (double)0;
        timeStamp = (long)0;
        deviceID = (int)0;
    }

    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }        

        if(getClass() != o.getClass()) {
            return false;
        }

        PhoneData otherObj = (PhoneData)o;

        if(temperature != otherObj.temperature) {
            return false;
        }
        if(humidity != otherObj.humidity) {
            return false;
        }
        if(timeStamp != otherObj.timeStamp) {
            return false;
        }
        if(deviceID != otherObj.deviceID) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int __result = 0;
        __result += (int)temperature;
        __result += (int)humidity;
        __result += (int)timeStamp;
        __result += (int)deviceID;
        return __result;
    }

    /**
    * This is the implementation of the <code>Copyable</code> interface.
    * This method will perform a deep copy of <code>src</code>
    * This method could be placed into <code>PhoneDataTypeSupport</code>
    * rather than here by using the <code>-noCopyable</code> option
    * to rtiddsgen.
    * 
    * @param src The Object which contains the data to be copied.
    * @return Returns <code>this</code>.
    * @exception NullPointerException If <code>src</code> is null.
    * @exception ClassCastException If <code>src</code> is not the 
    * same type as <code>this</code>.
    * @see Copyable#copy_from(Object)
    */
    public Object copy_from(Object src) {

        PhoneData typedSrc = (PhoneData) src;
        PhoneData typedDst = this;

        typedDst.temperature = typedSrc.temperature;
        typedDst.humidity = typedSrc.humidity;
        typedDst.timeStamp = typedSrc.timeStamp;
        typedDst.deviceID = typedSrc.deviceID;

        return this;
    }

    public String toString(){
        return toString("", 0);
    }

    public String toString(String desc, int indent) {
        StringBuffer strBuffer = new StringBuffer();        

        if (desc != null) {
            CdrHelper.printIndent(strBuffer, indent);
            strBuffer.append(desc).append(":\n");
        }

        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("temperature: ").append(temperature).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("humidity: ").append(humidity).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("timeStamp: ").append(timeStamp).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("deviceID: ").append(deviceID).append("\n");  

        return strBuffer.toString();
    }

}
