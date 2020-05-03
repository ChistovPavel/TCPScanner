package Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    public static String objToJsonStr(Object obj)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        String result = gson.toJson(obj);
        return result;
    }
}
