package qcjlibrary.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

import android.util.Log;

/**
 * 用于CommonUtils的工具集合类
 *
 * @author qcj
 */
public class JsonUtils {

    private static final String TAG = "JsonUtils";

    /**
     * json 并返回 一個model對象 利用反射机制解析
     *
     * @param jsonObject 需要解析的json对象 必須是純數據
     * @param typeItem   需要解析成model的對象，只要是繼承model或者是其子類的都行
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model parseJsonObject(JSONObject jsonObject,
                                        Class<? extends Model> typeItem) {
        if (jsonObject != null) {
            try {
                @SuppressWarnings("unchecked")
                Constructor<Model> constructor = (Constructor<Model>) typeItem
                        .getConstructor(JSONObject.class);
                Model model = constructor.newInstance(jsonObject);
                Log.i("reflect", "------=" + model.toString() + "");
                return model;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 把jsonarray解析成list<ModelItem> 利用反射机制解析
     *
     * @param array    需要解析的jsonarray 必须是纯数据
     * @param typeItem 需要解析为model的類型
     * @return
     */
    public static List<Model> parseJsonArray(JSONArray array,
                                             Class<? extends Model> typeItem) {
        List<Model> list = new ArrayList<Model>();
        if (array != null && array.length() > 0) {
            int num = array.length();
            for (int i = 0; i < num; i++) {
                try {
                    Model model = new Model();
                    model = parseJsonObject(array.getJSONObject(i), typeItem);
                    list.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }
}
