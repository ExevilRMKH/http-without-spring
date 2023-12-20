package org.example.app.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.app.exceptions.PostmanException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHelpers {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String[] queryToArray(String query){
        if(query == null)
            return new String[]{};
        return query.split("&");
    }
    public static AbstractMap.SimpleImmutableEntry<String,String> splitQueryParameters(String it){
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0,idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : it;
        return new AbstractMap.SimpleImmutableEntry<>(
                URLDecoder.decode(key, StandardCharsets.UTF_8),
                URLDecoder.decode(value, StandardCharsets.UTF_8)
        );
    }

    public static Map<String,List<String>> entryToMap(List<AbstractMap.SimpleImmutableEntry<String, String>> params ){
        return params.stream()
                .filter(stringStringSimpleImmutableEntry -> stringStringSimpleImmutableEntry.getKey().equals("id"))
                .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, LinkedHashMap::new, mapping(Map.Entry::getValue, toList())));
    }

    public static byte[] objectToArray(Object o) {
        if(o == null)
            return new byte[0];

        try {
            return mapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new PostmanException("object couldn't serialization", e);
        }
    }
}
