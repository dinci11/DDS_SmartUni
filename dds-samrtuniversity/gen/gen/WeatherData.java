

/*
WARNING: THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.

This file was generated from .idl using "rtiddsgen".
The rtiddsgen tool is part of the RTI Connext distribution.
For more information, type 'rtiddsgen -help' at a command shell
or consult the RTI Connext manual.
*/

package gen;

import com.rti.dds.infrastructure.*;
import com.rti.dds.infrastructure.Copyable;
import java.io.Serializable;
import com.rti.dds.cdr.CdrHelper;

public class WeatherData   implements Copyable, Serializable{

    public int outTemperature = (int)0;
    public int outHumidity = (int)0;
    public int windSpeed = (int)0;

    public WeatherData() {

    }
    public WeatherData (WeatherData other) {

        this();
        copy_from(other);
    }

    public static Object create() {

        WeatherData self;
        self = new  WeatherData();
        self.clear();
        return self;

    }

    public void clear() {

        outTemperature = (int)0;
        outHumidity = (int)0;
        windSpeed = (int)0;
    }

    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }        

        if(getClass() != o.getClass()) {
            return false;
        }

        WeatherData otherObj = (WeatherData)o;

        if(outTemperature != otherObj.outTemperature) {
            return false;
        }
        if(outHumidity != otherObj.outHumidity) {
            return false;
        }
        if(windSpeed != otherObj.windSpeed) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int __result = 0;
        __result += (int)outTemperature;
        __result += (int)outHumidity;
        __result += (int)windSpeed;
        return __result;
    }

    /**
    * This is the implementation of the <code>Copyable</code> interface.
    * This method will perform a deep copy of <code>src</code>
    * This method could be placed into <code>WeatherDataTypeSupport</code>
    * rather than here by using the <code>-noCopyable</code> option
    * to rtiddsgen.
    * 
    * @param src The Object which contains the data to be copied.
    * @return Returns <code>this</code>.
    * @exception NullPointerException If <code>src</code> is null.
    * @exception ClassCastException If <code>src</code> is not the 
    * same type as <code>this</code>.
    * @see com.rti.dds.infrastructure.Copyable#copy_from(java.lang.Object)
    */
    public Object copy_from(Object src) {

        WeatherData typedSrc = (WeatherData) src;
        WeatherData typedDst = this;

        typedDst.outTemperature = typedSrc.outTemperature;
        typedDst.outHumidity = typedSrc.outHumidity;
        typedDst.windSpeed = typedSrc.windSpeed;

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
        strBuffer.append("outTemperature: ").append(outTemperature).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("outHumidity: ").append(outHumidity).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("windSpeed: ").append(windSpeed).append("\n");  

        return strBuffer.toString();
    }

}
