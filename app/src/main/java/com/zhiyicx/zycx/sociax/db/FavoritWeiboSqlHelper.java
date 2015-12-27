package com.zhiyicx.zycx.sociax.db;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.modle.ImageAttach;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;
import com.zhiyicx.zycx.sociax.unit.CommonLog;
import com.zhiyicx.zycx.sociax.unit.LogFactory;

public class FavoritWeiboSqlHelper extends SqlHelper {
    private static FavoritWeiboSqlHelper instance;
    private ThinksnsTableSqlHelper favoritWeiboSql;
    static AttachSqlHelper attachSqlHelper;

    private ListData<SociaxItem> weiboDatas;

    private CommonLog mCommonLog = LogFactory.createLog();

    private FavoritWeiboSqlHelper(Context context) {
        this.favoritWeiboSql = new ThinksnsTableSqlHelper(context, DB_NAME,
                null, VERSION);
    }

    public static FavoritWeiboSqlHelper getInstance(Context context) {
        attachSqlHelper = new AttachSqlHelper(context);

        if (instance == null) {
            instance = new FavoritWeiboSqlHelper(context);
        }

        return instance;
    }

    private int transFrom(String str) {
        if (str.equals("WEB")) {
            return 0;
        } else if (str.endsWith("ANDROID")) {
            return 2;
        } else if (str.equals("IPHONE")) {
            return 3;
        } else if (str.equals("PHONE")) {
            return 1;
        }
        return 0;
    }

    private int transFavourt(boolean favorited) {
        if (favorited) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isFavourt(int is) {
        if (is == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加微博
     *
     * @param weibo
     * @return
     */
    public long addWeibo(Weibo weibo) {
        ContentValues map = new ContentValues();
        map.put(ThinksnsTableSqlHelper.weiboId, weibo.getWeiboId());
        map.put(ThinksnsTableSqlHelper.uid, weibo.getUid());
        map.put(ThinksnsTableSqlHelper.userName, weibo.getUsername());
        map.put(ThinksnsTableSqlHelper.content, weibo.getContent());
        map.put(ThinksnsTableSqlHelper.cTime, weibo.getCtime());
        map.put(ThinksnsTableSqlHelper.from, transFrom(weibo.getFrom()
                .toString()));
        map.put(ThinksnsTableSqlHelper.timeStamp, weibo.getTimestamp());
        map.put(ThinksnsTableSqlHelper.comment, weibo.getComment());
        map.put(ThinksnsTableSqlHelper.type, weibo.getType());
        map.put(ThinksnsTableSqlHelper.attach, weibo.hasImage() == true ? 0 : 1);

        if (weibo.getAttachs() != null)
            for (ImageAttach iAttach : weibo.getAttachs()) {
                attachSqlHelper.addAttach(iAttach);
            }
        attachSqlHelper.close();
        /*
		 * map.put(weiboTable.picUrl, weibo.getPicUrl()!=
		 * null?weibo.getPicUrl():"");
		 * 
		 * map.put(weiboTable.thumbMiddleUrl,weibo.getThumbMiddleUrl()!=null?weibo
		 * .getPicUrl():""); map.put(weiboTable.thumbUrl,
		 * weibo.getThumbUrl()!=null?weibo.getThumbUrl():"");
		 */
        if (!weibo.isNullForTranspondId()) {
            map.put(ThinksnsTableSqlHelper.transpond, weibo.getTranspond()
                    .toJSON());
        }
        // map.put(weiboTable.transpond,weibo.getTranspond().toJSON()!=
        // null?weibo.getTranspond().toJSON():" ");
        map.put(ThinksnsTableSqlHelper.transpondCount,
                weibo.getTranspondCount());
        map.put(ThinksnsTableSqlHelper.userface, weibo.getUserface());
        map.put(ThinksnsTableSqlHelper.transpondId, weibo.getTranspondId());
        map.put(ThinksnsTableSqlHelper.favorited,
                transFavourt(weibo.isFavorited()));
        map.put(ThinksnsTableSqlHelper.weiboJson, weibo.toJSON());
        map.put("site_id", Thinksns.getMySite().getSite_id());
        map.put("my_uid", Thinksns.getMy().getUid());
        return favoritWeiboSql.getWritableDatabase().insert("fav_weibo", null,
                map);
    }

    /**
     * 获取微博列表
     *
     * @return
     */
    public ListData<SociaxItem> getWeiboList() {
        Cursor cursor = favoritWeiboSql.getReadableDatabase().query(
                "fav_weibo",
                null,
                "site_id=" + Thinksns.getMySite().getSite_id() + " and my_uid="
                        + Thinksns.getMy().getUid(), null, null, null,
                ThinksnsTableSqlHelper.weiboId + " DESC");
        weiboDatas = new ListData<SociaxItem>();

        if (cursor.moveToFirst())
            do {
                Weibo weibo = new Weibo();
                int weiboId = cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.weiboId));
                weibo.setWeiboId(weiboId);
                weibo.setUid(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.uid)));
                weibo.setUsername(cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.userName)));
                weibo.setContent(cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.content)));
                weibo.setCtime(cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.cTime)));
                weibo.setFrom(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.from)));
                weibo.setTimestamp(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.timeStamp)));
                weibo.setComment(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.comment)));
                weibo.setType(cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.type)));

                weibo.setAttachs(attachSqlHelper.getAttachsByWeiboId(weiboId));
                attachSqlHelper.close();

                // weibo.setPicUrl(cursor.getString(cursor.getColumnIndex(weiboTable.picUrl)));
                // weibo.setThumbMiddleUrl(cursor.getString(cursor.getColumnIndex(weiboTable.thumbMiddleUrl)));
                weibo.setTranspondId(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.transpondId)));
                // weibo.setThumbUrl(cursor.getString(cursor.getColumnIndex(weiboTable.thumbUrl)));
                if (cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.transpond)) != null) {
                    try {
                        weibo.setTranspond(new Weibo(
                                new JSONObject(
                                        cursor.getString(cursor
                                                .getColumnIndex(ThinksnsTableSqlHelper.transpond)))));
                    } catch (WeiboDataInvalidException e) {
                        mCommonLog.e(e.toString());
                    } catch (JSONException e) {
                        mCommonLog.e(e.toString());
                    }
                }
                weibo.setTranspondCount(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.transpondCount)));
                weibo.setFavorited(isFavourt(cursor.getInt(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.favorited))));
                weibo.setUserface(cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.userface)));
                weibo.setTempJsonString(cursor.getString(cursor
                        .getColumnIndex(ThinksnsTableSqlHelper.weiboJson)));
                weiboDatas.add(weibo);
            } while (cursor.moveToNext());
        cursor.close();
        return weiboDatas;
    }

    /**
     * 获取DB的微博数量
     *
     * @return
     */
    public int getDBWeiboSize() {
        Cursor cursor = favoritWeiboSql.getWritableDatabase().rawQuery(
                "select count(*) from fav_weibo where site_id = "
                        + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid(), null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    /**
     * 删除指定条数的微博
     *
     * @param count
     * @return
     */
    public void deleteWeibo(int count) {
        if (count >= 20) {
            favoritWeiboSql.getWritableDatabase().execSQL(
                    "delete from fav_weibo where site_id = "
                            + Thinksns.getMySite().getSite_id()
                            + " and my_uid = " + Thinksns.getMy().getUid());
        } else if (count > 0 && count < 20) {
            String sql = "delete from home_weibo where weiboId in (select weiboId from fav_weibo where site_id = "
                    + Thinksns.getMySite().getSite_id()
                    + " and my_uid = "
                    + Thinksns.getMy().getUid()
                    + " order by weiboId limit "
                    + count + ")";
            favoritWeiboSql.getWritableDatabase().execSQL(sql);
        }
    }

    public boolean deleteWeiboById(int weiboId) {
        try {
            favoritWeiboSql.getWritableDatabase().execSQL(
                    "delete from fav_weibo where weiboId=" + weiboId
                            + " and site_id = "
                            + Thinksns.getMySite().getSite_id()
                            + " and my_uid = " + Thinksns.getMy().getUid());
            return true;
        } catch (Exception e) {
            Log.e("FavoritWeiboSqlHelper",
                    "delete weibo error ---------->" + e.toString());
            return false;
        }
    }

    /**
     * 删除数据库缓存
     */
    public void clearCacheDB() {
        favoritWeiboSql.getWritableDatabase().execSQL(
                "delete from fav_weibo where site_id = "
                        + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid());
    }

    @Override
    public void close() {
        favoritWeiboSql.close();
    }

}
