package com.m13.dam.dam_m13_finalproject_android.model.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;

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
     * @param pathFile path del fichero, en String.
     * @return Objeto Object del objeto del fichero. Se tendra que CASTEAR.
     */
    public static Object jsonJacksonUnmarshalling(Class clas, String pathFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File f = new File(pathFile);
            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            Object e = mapper.readValue(f, clas);
            return e;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
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
     * @param pathFile path del fichero, en String.
     */
    public static void jsonJacksonMarshalling(Object object, String pathFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File f = new File(pathFile);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
            mapper.writeValue(f, object);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
