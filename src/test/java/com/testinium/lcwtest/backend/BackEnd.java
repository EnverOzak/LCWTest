package com.testinium.lcwtest.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class BackEnd {

    public static String findElementByKey(String key, String pageObject) throws IOException {

        ClassLoader classLoader = BackEnd.class.getClassLoader();
        File file = new File(classLoader.getResource(pageObject).getFile());

        String content = new String(Files.readAllBytes(file.toPath()));
        ObjectMapper mapper = new ObjectMapper();

        List<WebElements> listElems = mapper.readValue(content, new TypeReference<List<WebElements>>(){});
        for (WebElements listElem : listElems) {
            if (listElem.key.equals(key))
                return listElem.webValue;
        }
        return "";
    }

}
