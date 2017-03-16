package com.example.inf.smarthome3.model.modelImpl;


import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by INF on 2017/3/14.
 */

public abstract class MyFindListener<T> {

    private Type mType ;

    private Class<T> mClass ;

    public abstract void onSuccess();
    public abstract void onFailed(Exception e);
    public abstract void onDataChange(T obj);

    public MyFindListener(){
        mType = getSuperclassTypeParameter(getClass());
        mClass = (Class<T>) mType;
    }


    public Type getType() {
        return mType;
    }

    public Class<T> getmClass() {
        return mClass;
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
