package com.fasterxml.jackson.module.jsonorg;

import org.codehaus.jackson.map.ser.SerializerBase;

abstract class JSONBaseSerializer<T> extends SerializerBase<T>
{
    protected JSONBaseSerializer(Class<T> cls) { super(cls); }
}
