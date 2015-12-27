package com.zhiyicx.zycx.sociax.db;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.exception.WeiboDataInvalidException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public class AtMeSqlHelper extends SqlHelper {
    private static AtMeSqlHelper instance;
    private ThinksnsTableSqlHelper weiboTable;
    private ListData<SociaxItem> atMeDatas;

    private AtMeSqlHelper(Context context) {
        this.weiboTable = new ThinksnsTableSqlHelper(context, DB_NAME, null, VERSION);
    }

    public static AtMeSqlHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AtMeSqlHelper(context);
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

    public long addAtMe(Weibo weibo) {
        ContentValues map = new ContentValues();
        map.put(ThinksnsTableSqlHelper.weiboId, weibo.getWeiboId());
        map.put(ThinksnsTableSqlHelper.uid, weibo.getUid());
        map.put(ThinksnsTableSqlHelper.userName, weibo.getUsername());
        map.put(ThinksnsTableSqlHelper.content, weibo.getContent());
        map.put(ThinksnsTableSqlHelper.cTime, weibo.getCtime());
        map.put(ThinksnsTableSqlHelper.from, transFrom(weibo.getFrom().toString()));
        map.put(ThinksnsTableSqlHelper.timeStamp, weibo.getTimestamp());
        map.put(ThinksnsTableSqlHelper.comment, weibo.getComment());
        map.put(ThinksnsTableSqlHelper.type, weibo.getType());
        if (!weibo.isNullForTranspondId()) {
            map.put(ThinksnsTableSqlHelper.transpond, weibo.getTranspond().toJSON());
        }
        // map.put(weiboTable.transpond,weibo.getTranspond().toJSON()!=
        // null?weibo.getTranspond().toJSON():" ");
        map.put(ThinksnsTableSqlHelper.transpondCount, weibo.getTranspondCount());
        map.put(ThinksnsTableSqlHelper.userface, weibo.getUserface());
        map.put(ThinksnsTableSqlHelper.transpondId, weibo.getTranspondId());
        map.put(ThinksnsTableSqlHelper.favorited, transFavourt(weibo.isFavorited()));
        map.put(ThinksnsTableSqlHelper.weiboJson, weibo.toJSON());
        map.put("site_id", Thinksns.getMySite().getSite_id());
        map.put("my_uid", Thinksns.getMy().getUid());
        long l = weiboTable.getWritableDatabase().insert(ThinksnsTableSqlHelper.atMeTable, null, map);
        return l;
    }

    public ListData<SociaxItem> getAtMeList() {
        Cursor cursor = weiboTable.getReadableDatabase().query(ThinksnsTableSqlHelper.atMeTable, null,
                "site_id=" + Thinksns.getMySite().getSite_id() + " and my_uid=" + Thinksns.getMy().getUid(), null,
                null, null, ThinksnsTableSqlHelper.weiboId + " DESC");
        atMeDatas = new ListData<SociaxItem>();
        if (cursor.moveToFirst()) {
            do {
                Weibo weibo = new Weibo();
                weibo.setWeiboId(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.weiboId)));
                weibo.setUid(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.uid)));
                weibo.setUsername(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.userName)));
                weibo.setContent(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.content)));
                weibo.setCtime(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.cTime)));
                weibo.setFrom(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.from)));
                weibo.setTimestamp(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.timeStamp)));
                weibo.setComment(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.comment)));
                weibo.setType(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.type)));

                // weibo.setPicUrl(cursor.getString(cursor.getColumnIndex(weiboTable.picUrl)));
                weibo.setThumbMiddleUrl(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.thumbMiddleUrl)));
                weibo.setTranspondId(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.transpondId)));
                weibo.setThumbUrl(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.thumbUrl)));
                if (cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.transpond)) != null) {
                    try {
                        weibo.setTranspond(new Weibo(new JSONObject(cursor.getString(cursor
                                .getColumnIndex(ThinksnsTableSqlHelper.transpond)))));
                    } catch (WeiboDataInvalidException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                weibo.setTranspondCount(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.transpondCount)));
                weibo.setFavorited(isFavourt(cursor.getInt(cursor.getColumnIndex(ThinksnsTableSqlHelper.favorited))));
                weibo.setUserface(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.userface)));
                weibo.setTempJsonString(cursor.getString(cursor.getColumnIndex(ThinksnsTableSqlHelper.weiboJson)));
                atMeDatas.add(weibo);
            } while (cursor.moveToNext());
        }
        return atMeDatas;
    }

    public int getDBAtMeSize() {
        Cursor cursor = weiboTable.getWritableDatabase().rawQuery(
                "select count(*) from at_me where site_id = " + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid(), null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public void deleteAtMe(int count) {
        if (count >= 20) {
            weiboTable.getWritableDatabase().execSQL(
                    "delete from at_me where site_id = " + Thinksns.getMySite().getSite_id() + " and my_uid = "
                            + Thinksns.getMy().getUid());
        } else if (count > 0 && count < 20) {
            String sql = "delete from at_me where weiboId in (select weiboId from at_me where site_id = "
                    + Thinksns.getMySite().getSite_id() + " and my_uid = " + Thinksns.getMy().getUid()
                    + " order by weiboId limit " + count + ")";
            weiboTable.getWritableDatabase().execSQL(sql);
        }
    }

    /**
     * 删除数据库缓存
     */
    public void clearCacheDB() {
        weiboTable.getWritableDatabase().execSQL(
                "delete from at_me where site_id = " + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid());
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        weiboTable.close();
    }

}
