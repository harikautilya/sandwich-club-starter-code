package com.udacity.sandwichclub.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsonMapper {


    public <T> T mapJson(String json, Class<T> classs) {
        T data = null;
        try {
            classs.getDeclaredConstructor().setAccessible(true);
            data = classs.getConstructor().newInstance();
            JSONObject jsonObject = new JSONObject(json);
            for (Field field : classs.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.getType() == Integer.class) {
                    field.set(data, jsonObject.getInt(field.getName()));
                } else if (field.getType() == String.class) {
                    field.set(data, jsonObject.getString(field.getName()));
                } else if (field.getType() == Float.class) {
                    field.set(data, BigDecimal.valueOf(jsonObject.getDouble(field.getName())).floatValue());
                } else if (field.getType() == Long.class) {
                    field.set(data, jsonObject.getLong(field.getName()));
                } else if (field.getType() == Boolean.class) {
                    field.set(data, jsonObject.getBoolean(field.getName()));
                } else if (field.getType() == List.class) {

                    Type value = (((ParameterizedType) field.getGenericType())
                            .getActualTypeArguments()[0]);

                    if (value == String.class) {
                        List<String> list = getGenericList(jsonObject.getJSONArray(field.getName()));
                        field.set(data, list);
                    }

                } else {
                    field.set(data, mapJson(jsonObject.getString(field.getName()), field.getType()));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    private <P> List<P> getGenericList(JSONArray params) throws JSONException {
        List<P> l = new ArrayList<P>();
        for (int i = 0; i < params.length(); i++) {
            l.add((P) params.get(i));
        }
        return l;
    }


}
