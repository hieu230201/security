package com.example.securitybase.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelMapperUtil {

    private static ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        return mapper;
    }

//    @UseSafeRunning
//    @UseLogging
    public static <S, T> T toObject(S s, Class<S> fromClass, Class<T> targetClass, ExpressionMap<S, T> expressionMap) {
        ModelMapper modelMapper = getMapper();
        if (expressionMap != null) {
            modelMapper.typeMap(fromClass, targetClass).addMappings(expressionMap);
        }

        return modelMapper.map(s, targetClass);
    }

//    @UseSafeRunning
//    @UseLogging
    public static <S, T> T toObject(S s, Class<T> targetClass) {
        if (s == null)
            return null;
        return getMapper().map(s, targetClass);
    }

//    @UseSafeRunning
//    @UseLogging
    public static <S, T> List<T> listObjectToListModel(Iterable<S> source, Class<T> targetClass) {
        int size;

        if (source instanceof Collection<?>) {
            size = ((Collection<?>) source).size();
            if (size > 0) {
                List<T> lstTargetClass = new ArrayList<T>();
                for (S s : source) {
                    if (s != null) {
                        lstTargetClass.add(toObject(s, targetClass));
                    }
                }
                return lstTargetClass;
            }
        }
        return new ArrayList<T>();
    }

    @SneakyThrows
//    @UseSafeRunning
//    @UseLogging
    public static <S, T> T cloneObject(S source, Class<T> targetClass){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(objectMapper.writeValueAsBytes(source), targetClass);
    }


}