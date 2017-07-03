package movie.example.ls.vvmoviemanager.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/6/13
 * Email : lsw
 */
public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 对象转成json字符串
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * json字符串转化成对应的类
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * json字符串转化成对应的list集合
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }
    /**
     * json字符串转化成对应的list集合
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectList(String jsonString,Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json字符串转化成对应的List集合，内部包含map
     * @param gsonString
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * json字符串转化成对应的 map 类
     * @param gsonString
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}