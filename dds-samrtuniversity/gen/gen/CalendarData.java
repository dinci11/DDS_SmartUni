

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

public class CalendarData   implements Copyable, Serializable{

    public long date = (long)0;
    public boolean isWeekday = (boolean)false;
    public boolean isNationalHoliday = (boolean)false;
    public boolean isUniversityHoliday = (boolean)false;
    public boolean isLecture = (boolean)false;

    public CalendarData() {

    }
    public CalendarData (CalendarData other) {

        this();
        copy_from(other);
    }

    public static Object create() {

        CalendarData self;
        self = new  CalendarData();
        self.clear();
        return self;

    }

    public void clear() {

        date = (long)0;
        isWeekday = (boolean)false;
        isNationalHoliday = (boolean)false;
        isUniversityHoliday = (boolean)false;
        isLecture = (boolean)false;
    }

    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }        

        if(getClass() != o.getClass()) {
            return false;
        }

        CalendarData otherObj = (CalendarData)o;

        if(date != otherObj.date) {
            return false;
        }
        if(isWeekday != otherObj.isWeekday) {
            return false;
        }
        if(isNationalHoliday != otherObj.isNationalHoliday) {
            return false;
        }
        if(isUniversityHoliday != otherObj.isUniversityHoliday) {
            return false;
        }
        if(isLecture != otherObj.isLecture) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int __result = 0;
        __result += (int)date;
        __result += (isWeekday == true)?1:0;
        __result += (isNationalHoliday == true)?1:0;
        __result += (isUniversityHoliday == true)?1:0;
        __result += (isLecture == true)?1:0;
        return __result;
    }

    /**
    * This is the implementation of the <code>Copyable</code> interface.
    * This method will perform a deep copy of <code>src</code>
    * This method could be placed into <code>CalendarDataTypeSupport</code>
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

        CalendarData typedSrc = (CalendarData) src;
        CalendarData typedDst = this;

        typedDst.date = typedSrc.date;
        typedDst.isWeekday = typedSrc.isWeekday;
        typedDst.isNationalHoliday = typedSrc.isNationalHoliday;
        typedDst.isUniversityHoliday = typedSrc.isUniversityHoliday;
        typedDst.isLecture = typedSrc.isLecture;

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
        strBuffer.append("date: ").append(date).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("isWeekday: ").append(isWeekday).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("isNationalHoliday: ").append(isNationalHoliday).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("isUniversityHoliday: ").append(isUniversityHoliday).append("\n");  
        CdrHelper.printIndent(strBuffer, indent+1);        
        strBuffer.append("isLecture: ").append(isLecture).append("\n");  

        return strBuffer.toString();
    }

}
