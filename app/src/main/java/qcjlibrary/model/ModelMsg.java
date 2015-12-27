package qcjlibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:49:44 类描述：这个类是实现
 */

public class ModelMsg extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

    public ModelMsg(JSONObject data) {
        try {
            if (data.has("message")) {

                setMessage(data.getString("domain"));
            }
            if (data.has("code")) {
                setCode(data.getInt("code"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
