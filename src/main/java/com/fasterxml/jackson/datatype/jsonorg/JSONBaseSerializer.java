package com.fasterxml.jackson.datatype.jsonorg;

import org.codehaus.jackson.map.ser.SerializerBase;

abstract class JSONBaseSerializer<T> extends SerializerBase<T>
{
    protected JSONBaseSerializer(Class<T> cls) { super(cls); }
}
