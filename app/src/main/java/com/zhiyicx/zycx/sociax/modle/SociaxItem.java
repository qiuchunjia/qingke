package com.zhiyicx.zycx.sociax.modle;

import java.io.Serializable;

import org.json.JSONObject;

import android.graphics.Bitmap;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;

public abstract class SociaxItem implements Serializable,
        Comparable<SociaxItem> {

    /**
     * 所有modle的基类
     */
    private static final long serialVersionUID = 1L;
    protected static final String NULL = "";

    public SociaxItem(JSONObject jsonData) throws DataInvalidException {
        if (jsonData == null)
            throw new DataInvalidException();
    }

    public SociaxItem() {
    }

    public abstract boolean checkValid();

    public abstract String getUserface();

    protected boolean checkNull(String data) {
        return data == null || data.equals(NULL);
    }

    protected boolean checkNull(int data) {
        return data == 0;
    }

    public boolean isNullForHeaderCache() {
        return this.getHeader() == null;
    }

    public Bitmap getHeader() {
        return Thinksns.getImageCache().get(this.getUserface());
    }

    public void setHeader(Bitmap header) {
        Thinksns.getImageCache().put(this.getUserface(), header);
    }

    public boolean hasHeader() {
        return !this.getUserface().contains("user_pic_");
    }

    @Override
    public int compareTo(SociaxItem another) {
        // TODO Auto-generated method stub
        return 0;
    }

}
