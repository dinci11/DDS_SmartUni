

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

public class WindowOpen   implements Copyable, Serializable{

    public double intensity = (double)0;

    public WindowOpen() {

    }
    public WindowOpen (WindowOpen other) {

        this();
        copy_from(other);
    }

    public static Object create() {

        WindowOpen self;
        self = new  WindowOpen();
        self.clear();
        return self;

    }

    public void clear() {

        intensity = (double)0;
    }

    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }        

        if(getClass() != o.getClass()) {
            return false;
        }

        WindowOpen otherObj = (WindowOpen)o;

        if(intensity != otherObj.intensity) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int __result = 0;
        __result += (int)intensity;
        return __result;
    }

    /**
    * This is the implementation of the <code>Copyable</code> interface.
    * This method will perform a deep copy of <code>src</code>
    * This method could be placed into <code>WindowOpenTypeSupport</code>
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

        WindowOpen typedSrc = (WindowOpen) src;
        WindowOpen typedDst = this;

        typedDst.intensity = typedSrc.intensity;

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
        strBuffer.append("intensity: ").append(intensity).append("\n");  

        return strBuffer.toString();
    }

}
