package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class TestUtility {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T jsonStringToObject(String jsonString, Class<T> resultObjectType) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, resultObjectType);
        } catch (Exception ex) {
            String errorMessage = String.format("Unable to convert json String to object [%s]. Caught a %s. %s.", resultObjectType.getName(), ex.getClass().getCanonicalName(), ex.getMessage());
            throw new RuntimeException(errorMessage,ex);
        }
    }

    public static String readFile(String path) throws Exception {
        InputStream inputStream = null;

        String errorMessage;

        try {
            inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            if (inputStream == null) {
                errorMessage = String.format("Resource could not be found in path [%s]. InputStream is null. Please validate the path. Validate that the file is in the resource folder.", path);
                throw new IOException(errorMessage);
            }
            errorMessage = IOUtils.toString(inputStream);
        } catch (IOException ex) {
            errorMessage = String.format("Unable to readFile from path [%s]. Caught a %s. $s", path, ex.getClass().getCanonicalName(), ex);
            throw new IOException(errorMessage, ex);
        } finally {
            closeInputStream(inputStream);
        }
        return errorMessage;
    }

    private static void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
