

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

public class Humidity   implements Copyable, Serializable{

    public int value = (int)0;
    public long timeStamp = (long)0;

    public Humidity() {

    }
    public Humidity (Humidity other) {

        this();
        copy_from(other);
    }

    public static Object create() {

        Humidity self;
        self = new  Humidity();
        self.clear();
        return self;

    }

    public void clear() {

        value = (int)0;
        timeStamp = (long)0;
    }

    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }        

        if(getClass() != o.getClass()) {
            return false;
        }

        Humidity otherObj = (Humidity)o;

        if(value != otherObj.value) {
            return false;
        }
        if(timeStamp != otherObj.timeStamp) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int __result = 0;
        __result += (int)value;
        __result += (int)timeStamp;
        return __result;
    }

    /**
    * This is the implementation of the <code>Copyable</code> interface.
    * This method will perform a deep copy of <code>src</code>
    * This method could be placed into <code>HumidityTypeSupport</code>
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

        Humidity typedSrc = (Humidity) src;
        Humidity typedDst = this;

        typedDst.value = typedSrc.value;
        typedDst.timeStamp = typedSrc.timeStamp;

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
        strBuffer.append("value: ").append(value).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("timeStamp: ").append(timeStamp).append("\n");  

        return strBuffer.toString();
    }

}
