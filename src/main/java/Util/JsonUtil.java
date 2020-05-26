package Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *  Класс, который содержит методы для работы с JSON
 * */
public class JsonUtil {

    /**
     * метод {@link JsonUtil#objToJsonStr(Object)}  предназначен для сериализации объекта в строку в формате JSON
     * @param obj объект, который необходимо сериализовать.
     * @return строка {@link String} в формате JSON
     * */
    public static String objToJsonStr(Object obj)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        String result = gson.toJson(obj);
        return result;
    }
}
