package com.senhome.shell.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.util.Collection;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    }

    @SneakyThrows
    public static String objectToJson(Object object) {

        if (object == null) {
            return StringUtils.EMPTY;
        }

        StringWriter out = new StringWriter();

        objectMapper.writeValue(out, object);

        return out.toString();
    }

    @SneakyThrows
    public static <T> T jsonToObject(String json, Class<T> clazz) {

        if (StringUtils.isBlank(json)) {
            return null;
        }

        return objectMapper.readValue(json.trim(), clazz);

    }

    @SneakyThrows
    public static <T extends Collection, E> T jsonToCollection(String json, Class<T> collectionClass,
                                                               Class<E> elementClass) {

        if (StringUtils.isBlank(json)) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);

        return objectMapper.readValue(json.trim(), collectionType);

    }

}
