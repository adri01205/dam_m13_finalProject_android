package com.m13.dam.dam_m13_finalproject_android.model.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
    public static Object jsonJacksonUnmarshalling(Class clas, InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(Collection.class, clas);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return mapper.readValue(inputStream, type);
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
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.setDateFormat(SynupConversor.dataFormat);
        mapper.writeValue(out,object);
        out.flush();


    }
}
