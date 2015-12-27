package com.zhiyicx.zycx.sociax.modle;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;

public class Weibo extends SociaxItem {
    public static final String TAG = "Weibo";

    public static enum From {
        WEB, PHONE, ANDROID, IPHONE, IPAD, WINDOWSPHONE
    }

    private static final String POSTIMAGE = "postimage";
    private static final String POSTIFILE = "postfile";

    private int weiboId;
    private int uid;
    private String content;
    private String cTime;
    private From from;
    private int timestamp;
    private int comment;
    private String type = "";
    private String[] picUrl;
    private String[] imageUrl;
    private String thumbMiddleUrl;
    private String thumbUrl;
    private Weibo transpond;
    private int transpondCount;
    private String userface;
    private int transpondId;
    private boolean favorited;
    private String username;
    private String jsonString;
    private String tempJsonString;
    private int zhanCount;
    private boolean isZhan = false;

    private List<ImageAttach> attachs;

    private int hasAttach;

    public String getTempJsonString() {
        return tempJsonString;
    }

    public void setTempJsonString(String tempJsonString) {
        this.tempJsonString = tempJsonString;
    }

    public static final int MAX_CONTENT_LENGTH = 140;

    public Weibo() {
    }

    public boolean isInvalidWeibo() {
        return !"".equals(this.content);
    }

    public Weibo(JSONObject weiboData, String str)
            throws WeiboDataInvalidException {
        try {
            // this.setComment(weiboData.getInt("comment"));
            this.setContent(weiboData.getString("feed_content"));
            this.setCtime(weiboData.getString("ctime"));
            this.setWeiboId(weiboData.getInt("feed_id"));
            this.setUid(weiboData.getInt("uid"));
            this.setFrom(weiboData.getInt("from"));
            if (weiboData.has("type") && weiboData.getString("type") != null) {
                this.setType(weiboData.getString("type"));
            }
            this.setTranspondCount(weiboData.getInt("report_count"));
            this.setTranspondId(weiboData.getInt("transpond_id"));
            if (this.hasImage()) {
                // this.setPicUrl(weiboData.getJSONObject("type_data").getString("picurl"));
                this.setThumbMiddleUrl(weiboData.getJSONObject("type_data")
                        .getString("thumbmiddleurl"));
                this.setThumbUrl(weiboData.getJSONObject("type_data")
                        .getString("thumburl"));
            }

            if (!this.isNullForTranspondId()) {
                Weibo transpond = new Weibo(
                        weiboData.getJSONObject("transpond_data"));
                this.setTranspond(transpond);
            }
            this.jsonString = weiboData.toString();
        } catch (JSONException e) {
            throw new WeiboDataInvalidException(e.getMessage());
        }
    }

    public Weibo(JSONObject weiboData) throws WeiboDataInvalidException {

        try {
            if (weiboData.has("comment_count"))
                this.setComment(weiboData.getInt("comment_count"));

            if (weiboData.has("content"))
                this.setContent(weiboData.getString("content"));

            this.setCtime(weiboData.getString("ctime"));
            this.setWeiboId(weiboData.getInt("feed_id"));
            this.setUid(weiboData.getInt("uid"));
            this.setTimestamp(weiboData.getInt("publish_time"));
            this.setFrom(weiboData.getInt("from"));
            if (weiboData.has("type") && weiboData.getString("type") != null) {
                this.setType(weiboData.getString("type"));
            }
            if (weiboData.has("comment_count"))
                this.setTranspondCount(weiboData.getInt("repost_count"));

            if (weiboData.has("transpond_id"))
                this.setTranspondId(weiboData.getInt("transpond_id"));

            if (weiboData.has("iscoll")) {
                JSONObject temp = weiboData.getJSONObject("iscoll");
                if (temp.has("colled") && temp.get("colled") != null) {
                    int isColl = temp.getInt("colled");
                    this.setFavorited(isColl == 1 ? true : false);
                }
            }
            if (weiboData.has("digg_count"))
                zhanCount = weiboData.getInt("digg_count");
            if (weiboData.has("isDigg")) {
                int i = weiboData.getInt("isDigg");
                if (i == 1)
                    isZhan = true;
                else
                    isZhan = false;
            }

            this.setHasAttach(weiboData.getInt("has_attach"));
            this.setUserface(weiboData.getString("avatar_middle"));
            this.setUsername(weiboData.getString("uname"));
            List<ImageAttach> attachs;
            if (this.hasImage() && hasAttach()) {
                attachs = new ArrayList<ImageAttach>();
                ImageAttach iAttach = null;
                JSONArray iArray = weiboData.getJSONArray("attach");
                for (int i = 0; i < iArray.length(); i++) {
                    iAttach = new ImageAttach();
                    JSONObject temp = (JSONObject) iArray.get(i);
                    iAttach.setWeiboId(this.getWeiboId());

                    iAttach.setName(temp.getString("attach_name"));

                    if (temp.has("attach_middle"))
                        iAttach.setSmall(temp.getString("attach_small"));

                    if (temp.has("attach_middle"))
                        iAttach.setMiddle(temp.getString("attach_middle"));

                    iAttach.setNormal(temp.getString("attach_url"));

                    attachs.add(iAttach);
                }
                this.setAttachs(attachs);
            }

            if (this.hasFile() && hasAttach()) {
                attachs = new ArrayList<ImageAttach>();
                ImageAttach iAttach = null;
                JSONArray iArray = weiboData.getJSONArray("attach");
                for (int i = 0; i < iArray.length(); i++) {
                    iAttach = new ImageAttach();
                    JSONObject temp = (JSONObject) iArray.get(i);
                    iAttach.setWeiboId(this.getWeiboId());

                    iAttach.setName(temp.getString("attach_name"));

                    if (temp.has("attach_middle"))
                        iAttach.setSmall(temp.getString("attach_small"));

                    if (temp.has("attach_middle"))
                        iAttach.setMiddle(temp.getString("attach_middle"));

                    iAttach.setNormal(temp.getString("attach_url"));

                    attachs.add(iAttach);
                }
                this.setAttachs(attachs);
            }

			/*
             * this.setThumbMiddleUrl(NULL); this.setThumbUrl(NULL);
			 */

            if (!this.isNullForTranspondId()) {
                Weibo transpond = new Weibo(
                        weiboData.getJSONObject("transpond_data"));
                this.setTranspond(transpond);
            }
            this.jsonString = weiboData.toString();
        } catch (JSONException e) {
            Log.d(TAG, "weibo construc method error " + e.toString());
            throw new WeiboDataInvalidException(e.getMessage());
        }
    }

    public Weibo(JSONObject weiboData, int type)
            throws WeiboDataInvalidException {
        try {
            if (weiboData.has("comment_count"))
                this.setComment(weiboData.getInt("comment_count"));

            this.setContent(weiboData.getString("source_content"));

            this.setCtime(weiboData.getString("ctime"));
            this.setWeiboId(weiboData.getInt("source_id"));
            this.setUid(weiboData.getInt("uid"));
            this.setFrom(weiboData.getInt("from"));

            if (weiboData.has("feedtype"))
                this.setType(weiboData.getString("feedtype"));

            if (weiboData.has("comment_count"))
                this.setTranspondCount(weiboData.getInt("repost_count"));

            if (weiboData.has("transpond_id"))
                this.setTranspondId(weiboData.getInt("transpond_id"));
			/*
			 * if(weiboData.getInt("favorited") != 0){ this.setFavorited(true);
			 * }else{ this.setFavorited(false); }
			 */
            if (weiboData.has("source_user"))
                this.setUsername(weiboData.getString("source_user"/*"source_title"*/));
            if (weiboData.has("avatar_middle"))
                setUserface(weiboData.getString("avatar_middle"));

            if (!this.isNullForTranspondId()) {
                Weibo transpond = new Weibo(
                        weiboData.getJSONObject("transpond_data"));
                this.setTranspond(transpond);
            }
            this.jsonString = weiboData.toString();

        } catch (JSONException e) {
            throw new WeiboDataInvalidException(e.getMessage());
        }
    }

    public String toJSON() {
        return this.jsonString;
    }

    public boolean checkContent() {
        return this.content.length() <= MAX_CONTENT_LENGTH;
    }

    public boolean checkContent(String content) {
        return content.length() <= MAX_CONTENT_LENGTH;
    }

    public boolean isNullForComment() {
        return this.comment == 0;
    }

    public boolean isNullForContent() {
        return this.content.equals(NULL) || this.content == null;
    }

    public boolean isNullForCtime() {
        return this.cTime.equals(NULL) || this.cTime == null;
    }

    public boolean isNullForWeiboId() {
        return this.weiboId == 0;
    }

    public boolean isNullForUid() {
        return this.uid == 0;
    }

    public boolean isNullForTimestamp() {
        return this.timestamp == 0;
    }

    public boolean isNullForTranspond() {
        if (!this.isNullForTranspondId())
            return this.transpond == null;
        return true;
    }

    public boolean isNullForTranspondId() {
        return this.transpondId == 0;
    }

    public boolean isNullForTranspondCount() {
        return this.transpondCount == 0;
    }

    public boolean isNullForUserFace() {
        return this.userface == null || this.userface.equals(NULL);
    }

    public boolean isNullForUserName() {
        return this.username == null || this.username.equals(NULL);
    }

	/*
	 * public boolean isNullForThumbCache(){ return this.getThumb() == null; }
	 * 
	 * public boolean isNullForThumbMiddleCache(){ return this.getThumbMiddle()
	 * == null; } public boolean isNullForThumbLargeCache(){ return
	 * this.getPicUrl() == null; }
	 * 
	 * 
	 * public boolean isNullForPic(){ if(this.hasImage()) return
	 * this.picUrl.equals(NULL); return true; }
	 */

    public boolean isNullForThumbMiddleUrl() {
        if (this.hasImage())
            return this.thumbMiddleUrl.equals(NULL);
        return true;
    }

    public boolean isNullForThumbUrl() {
        if (this.hasImage())
            return this.thumbUrl.equals(NULL);
        return true;
    }

    public boolean hasImage() {
        return this.type.equals(Weibo.POSTIMAGE);
    }

    public boolean hasFile() {
        return this.type.equals(Weibo.POSTIFILE);
    }

    @Override
    public boolean checkValid() {
        boolean result = true;
		/*
		 * if(this.hasImage()){ result = result &&
		 * !(this.isNullForThumbMiddleUrl() || this.isNullForThumbUrl() ||
		 * this.isNullForPic()); }
		 * 
		 * if(!this.isNullForTranspondId()){ result = result &&
		 * !this.isNullForTranspond(); }
		 * 
		 * result = result && !(this.isNullForContent() || this.isNullForCtime()
		 * || this.isNullForUid() || this.isNullForTimestamp() ||
		 * this.isNullForUserFace() || this.isNullForWeiboId() ||
		 * this.isNullForUserName());
		 */
        return result;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCtime() {
        return cTime;
    }

    public void setCtime(String cTime) {
        this.cTime = cTime;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(int from) {
        switch (from) {
            case 0:
                this.from = From.WEB;
                break;
            case 1:
                this.from = From.PHONE;
                break;
            case 2:
                this.from = From.ANDROID;
                break;
            case 3:
                this.from = From.IPHONE;
                break;
            case 4:
                this.from = From.IPAD;
                break;
            case 5:
                this.from = From.WINDOWSPHONE;
                break;
            default:
                this.from = From.WEB;
        }
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getType() {
        if (type == null)
            return "";
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String[] picUrl) {
        this.picUrl = picUrl;
    }

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbMiddleUrl() {
        return thumbMiddleUrl;
    }

    public void setThumbMiddleUrl(String thumbMiddleUrl) {
        this.thumbMiddleUrl = thumbMiddleUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Override
    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public Weibo getTranspond() {
        return transpond;
    }

    public void setTranspond(Weibo transpond) {
        this.transpond = transpond;
    }

    public int getTranspondCount() {
        return transpondCount;
    }

    public void setTranspondCount(int transpondCount) {
        this.transpondCount = transpondCount;
    }

    public int getTranspondId() {
        return transpondId;
    }

    public void setTranspondId(int transpondId) {
        this.transpondId = transpondId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	/*
	 * public Bitmap getThumb() { return
	 * Thinksns.getImageCache().get(this.getThumbUrl()); } public void
	 * setThumb(Bitmap thumb) { Thinksns.getImageCache().put(this.getThumbUrl(),
	 * thumb); }
	 * 
	 * public Bitmap getThumbMiddle() { return
	 * Thinksns.getImageCache().get(this.getThumbMiddleUrl()); } public void
	 * setThumbMiddle(Bitmap thumbMiddle) {
	 * Thinksns.getImageCache().put(this.getThumbMiddleUrl(), thumbMiddle); }
	 * 
	 * public Bitmap getThumbLarge(){ return
	 * Thinksns.getImageCache().get(this.getPicUrl()); }
	 * 
	 * public void setThumbLarge(Bitmap thumbLarge){
	 * Thinksns.getImageCache().put(this.getPicUrl(), thumbLarge); }
	 */

    public int getHasAttach() {
        return hasAttach;
    }

    public void setHasAttach(int hasAttach) {
        this.hasAttach = hasAttach;
    }

    public List<ImageAttach> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<ImageAttach> attachs) {
        this.attachs = attachs;
    }

    public boolean hasAttach() {
        return this.getHasAttach() == 1 ? true : false;
    }

    public int getZhanCount() {
        return zhanCount;
    }

    public void setZhanCount(int count) {
        zhanCount = count;
    }

    public boolean isZhan() {
        return isZhan;
    }

    public void setIsZhan(boolean en) {
        isZhan = en;
    }
}
