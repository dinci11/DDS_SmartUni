
/*
WARNING: THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.

This file was generated from .idl using "rtiddsgen".
The rtiddsgen tool is part of the RTI Connext distribution.
For more information, type 'rtiddsgen -help' at a command shell
or consult the RTI Connext manual.
*/

package gen;

import com.rti.dds.typecode.*;

public class  WeatherDataTypeCode {
    public static final TypeCode VALUE = getTypeCode();

    private static TypeCode getTypeCode() {
        TypeCode tc = null;
        int __i=0;
        StructMember sm[]=new StructMember[3];
        Annotations memberAnnotation;

        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_LONG);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_LONG);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_LONG);
        sm[__i] = new  StructMember("outTemperature", false, (short)-1,  false, TypeCode.TC_LONG, 0, false, memberAnnotation);__i++;
        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_LONG);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_LONG);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_LONG);
        sm[__i] = new  StructMember("outHumidity", false, (short)-1,  false, TypeCode.TC_LONG, 1, false, memberAnnotation);__i++;
        memberAnnotation = new Annotations();
        memberAnnotation.default_annotation(AnnotationParameterValue.ZERO_LONG);
        memberAnnotation.min_annotation(AnnotationParameterValue.MIN_LONG);
        memberAnnotation.max_annotation(AnnotationParameterValue.MAX_LONG);
        sm[__i] = new  StructMember("windSpeed", false, (short)-1,  false, TypeCode.TC_LONG, 2, false, memberAnnotation);__i++;

        Annotations annotation = new Annotations();
        annotation.allowed_data_representation_mask(5);

        tc = TypeCodeFactory.TheTypeCodeFactory.create_struct_tc("WeatherData",ExtensibilityKind.EXTENSIBLE_EXTENSIBILITY,  sm , annotation);        
        return tc;
    }
}

