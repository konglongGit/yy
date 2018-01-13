package com.example.mfr.multifunctionrecord;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kongpeng on 16/7/9.
 */
public class JsonUtil {
    private final static String regex = "\"([^\\\" ]+?)\":";

    /**
     * 一个方法解析多层json数据  json + 正则 + 递归
     * @see {@link java.util.regex.Matcher}, {@link java.util.regex.Pattern}
     * @param jsonStr
     * @return {@link java.util.Map} or {@link java.util.List} or {@link java.lang.String}
     */
    public static Object jsonParse(final String jsonStr) {
        if (jsonStr == null) throw new NullPointerException("JsonString shouldn't be null");
        String aJsonStr = jsonStr.trim();
        try {
            if (isJsonObject(aJsonStr)) {
                final Pattern pattern = Pattern.compile(regex);
                final Matcher matcher = pattern.matcher(aJsonStr);
                final Map<String, Object> map = new HashMap<String, Object>();
                final JSONObject jsonObject = new JSONObject(aJsonStr);
                try {
                    for (; matcher.find(); ) {
                        String groupName = matcher.group(1);
                        Object obj = jsonObject.opt(groupName);
                        //Log.e(groupName, obj+"");
                        if (isJsonObject(obj+"") || isJsonArray(obj+"")) {
                            matcher.region(matcher.end() + (obj+"").replace("\\", "").length(), matcher.regionEnd());
                            map.put(groupName, jsonParse(obj+""));
                        } else {
                            map.put(groupName, obj+"");
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("object---error", e.getMessage() + "--" + e.getLocalizedMessage());
                }

                return map;
            } else if (isJsonArray(aJsonStr)) {
                List<Object> list = new ArrayList<Object>();
                try {
                    JSONArray jsonArray = new JSONArray(aJsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object object = jsonArray.opt(i);
                        list.add(jsonParse(object+""));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("array---error", e.getMessage()+"--"+e.getLocalizedMessage());
                }
                return list;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("RegexUtil--regexJson", e.getMessage()+"");
        }
        return aJsonStr;
    }

    /**
     * To determine whether a string is JsonObject {@link org.json.JSONObject}
     * @param jsonStr {@link java.lang.String}
     * @return boolean
     */
    private static boolean isJsonObject(final String jsonStr) {
        if (jsonStr == null) return false;
        try{
            JSONObject json=new JSONObject (jsonStr);
            return json.length() > 0;
        }catch (JSONException ex){

        }
        return false;
    }

    /**
     * 以确定是否获取一个字符串 {@link org.json.JSONArray};
     * @param jsonStr {@link java.lang.String}
     * @return boolean
     */
    private static boolean isJsonArray(final String jsonStr) {
        if (jsonStr == null) return false;
        try{
            JSONArray jsonarray=new JSONArray (jsonStr);
            return jsonarray.length() > 0;
        }catch (JSONException ex){

        }

        return false;
    }

    /**
     * 将对象分装为json字符串 (json + 递归)
     * @param obj 参数应为{@link java.util.Map} 或者 {@link java.util.List}
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object jsonEnclose(Object obj) {
        try {
            if (obj instanceof Map) {   //如果是Map则转换为JsonObject
                Map<String, Object> map = (Map<String, Object>)obj;

                Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
                JSONStringer jsonStringer = new JSONStringer().object();
                while (iterator.hasNext()) {
                    Entry<String, Object> entry = iterator.next();
                    jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));
                }
                JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));
                return jsonObject;
            } else if (obj instanceof List) {  //如果是List则转换为JsonArray
                List<Object> list = (List<Object>)obj;
                JSONStringer jsonStringer = new JSONStringer().array();
                for (int i = 0; i < list.size(); i++) {
                    jsonStringer.value(jsonEnclose(list.get(i)));
                }
                JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));
                return jsonArray;
            } else {
                return obj;
            }
        } catch (Exception e) {
            // TODO: handle exception
//            LogUtil.e("jsonUtil--Enclose", e.getMessage());
            return e.getMessage();
        }
    }

}
