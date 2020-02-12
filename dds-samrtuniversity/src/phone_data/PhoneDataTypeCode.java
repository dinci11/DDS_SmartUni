
/*
WARNING: THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.

This file was generated from .idl using "rtiddsgen".
The rtiddsgen tool is part of the RTI Connext distribution.
For more information, type 'rtiddsgen -help' at a command shell
or consult the RTI Connext manual.
*/

package phone_data;

import com.rti.dds.typecode.*;

public class  PhoneDataTypeCode {
    public static final TypeCode VALUE = getTypeCode();

    private static TypeCode getTypeCode() {
        TypeCode tc = null;
        int __i=0;
        StructMember sm[]=new StructMember[4];
        Annotations memberAnnotation;

        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_DOUBLE);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_DOUBLE);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_DOUBLE);
        sm[__i] = new  StructMember("temperature", false, (short)-1,  false, TypeCode.TC_DOUBLE, 0, false, memberAnnotation);__i++;
        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_DOUBLE);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_DOUBLE);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_DOUBLE);
        sm[__i] = new  StructMember("humidity", false, (short)-1,  false, TypeCode.TC_DOUBLE, 1, false, memberAnnotation);__i++;
        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_LONGLONG);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_LONGLONG);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_LONGLONG);
        sm[__i] = new  StructMember("timeStamp", false, (short)-1,  false, TypeCode.TC_LONGLONG, 2, false, memberAnnotation);__i++;
        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_LONG);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_LONG);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_LONG);
        sm[__i] = new  StructMember("deviceID", false, (short)-1,  false, TypeCode.TC_LONG, 3, false, memberAnnotation);__i++;

        Annotations annotation = new Annotations();
        annotation.allowed_data_representation_mask(5);

        tc = TypeCodeFactory.TheTypeCodeFactory.create_struct_tc("PhoneData",ExtensibilityKind.EXTENSIBLE_EXTENSIBILITY,  sm , annotation);        
        return tc;
    }
}

