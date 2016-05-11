package com.m13.dam.dam_m13_finalproject_android.model.services;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Adri on 06/05/2016.
 */
public abstract class MarshallingUnmarshalling {
    /**
     * unmarshalling (fichero a objeto) de ficheros json utilizando Jackson. Las
     * classes con las que quieres trabajar tienen que tener las etiquetas de
     * Jackson.
     *
     * Se pasara por parametro la classe del objeto padre (si quieres hacer
     * unmarsahlling de mas de un objeto tendras que hacer una classe que tenga
     * un ArrayList con todos los objetos).
     *
     * @param clas classe del objeto padre. ej: bookStore.class()
     * @param inputStream InputStream de donde se sacan los objetos
     * @return Objeto Object del objeto del fichero. Se tendra que CASTEAR.
     */
    public static Object jsonJacksonUnmarshalling(Class oClass, InputStream inputStream) throws IOException {
        return new ObjectMapper().readValue(inputStream, new TypeReference<Collection<Employee>>() { });
    }

    /**
     * marshalling (objectes a fitxer) de ficheros json utilizando Jackson. Las
     * classes con las que quieres trabajar tienen que tener las etiquetas de
     * Jackson.
     *
     * Se pasara por parametro el objeto padre que se grabara en el fichero (si
     * quieres hacer marsahlling de un fichero con mas de un objeto tendras que
     * hacer una classe que tenga un ArrayList con todos los objetos).
     *
     * @param object objeto padre
     * @param out OutputStream donde se imprimira el resultado
     */
    public static void jsonJacksonMarshalling(Object object, OutputStream out) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            //mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
            mapper.writeValue(out, object);

    }
}
