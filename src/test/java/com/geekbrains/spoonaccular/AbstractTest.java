package com.geekbrains.spoonaccular;

import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AbstractTest  {

    public String getRecource (String name) throws IOException, NullPointerException {
        String resource= getClass().getSimpleName()+"/"+name;
        System.out.println("resourse "+resource);
        InputStream stream = getClass().getResourceAsStream(resource);
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        int i;
        while (( i = stream.read()) != -1) {
            buffer.write((byte) i);
        }
        byte[] bytes=buffer.toByteArray();
        return new String(bytes, StandardCharsets.UTF_8);
    }
    public void assertJson (Object expected, Object actually) {
        JsonAssert.assertJsonEquals(expected, actually, JsonAssert.when(Option.IGNORING_ARRAY_ORDER));
    }
    public File getFile (String name){
        String resource= getClass().getSimpleName()+"/"+name;
        String file = getClass().getResource(resource).getFile();
        return new File(file);
    }
}


