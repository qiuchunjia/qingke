package com.zhiyicx.zycx.sociax.db;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weiba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 类说明：
 *
 * @author povol
 * @version 1.0
 * @date Dec 19, 2012
 */
public class WeibaSqlHelper extends SqlHelper {

    private static WeibaSqlHelper instance;
    private ThinksnsTableSqlHelper tableSqlHelper;

    private WeibaSqlHelper(Context context) {
        this.tableSqlHelper = new ThinksnsTableSqlHelper(context, DB_NAME,
                null, VERSION);
    }

    public static WeibaSqlHelper getInstance(Context context) {
        if (instance == null) {
            instance = new WeibaSqlHelper(context);
        }
        return instance;
    }

    public long addWeiba(Weiba weiba) {

        ContentValues values = new ContentValues();
        values.put("weiba_id", weiba.getWeibaId());
        values.put("name", weiba.getWeibaName());
        values.put("intro", weiba.getIntro());
        values.put("icon_url", weiba.getWeibaIcon());
        values.put("icon_data", weiba.getIconData());
        values.put("f_count", weiba.getFollowCount());
        values.put("t_count", weiba.getThreadCount());
        values.put("f_state", weiba.getFollowstate());
        values.put("post_permission", weiba.getPostPermission());
        values.put("site_id", Thinksns.getMySite().getSite_id());
        values.put("my_uid", Thinksns.getMy().getUid());

        return tableSqlHelper.getWritableDatabase().insert(
                ThinksnsTableSqlHelper.tbWeiba, null, values);

    }

    public void delWeiba(Weiba weiba) {
        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "delete * from tb_weiba"; // order by _id
        // ascdatabase.execSQL(sql);
        database.execSQL(sql);
        database.close();
    }

    public void deleteWeiba(int count) {
        if (count >= 20) {
            tableSqlHelper.getWritableDatabase().execSQL(
                    "delete from " + ThinksnsTableSqlHelper.tbWeiba
                            + " where site_id = "
                            + Thinksns.getMySite().getSite_id()
                            + " and my_uid = " + Thinksns.getMy().getUid());
        } else if (count > 0 && count < 20) {
            String sql = "delete from " + ThinksnsTableSqlHelper.tbWeiba
                    + " where weiba_id in (select weiboId from "
                    + ThinksnsTableSqlHelper.tbWeiba + " where site_id = "
                    + Thinksns.getMySite().getSite_id() + " and my_uid = "
                    + Thinksns.getMy().getUid() + " order by weiba_id limit "
                    + count + ")";
            tableSqlHelper.getWritableDatabase().execSQL(sql);
        }
    }

    public int getDBWeibaSize() {
        Cursor cursor = tableSqlHelper.getWritableDatabase().rawQuery(
                "select count(*) from " + ThinksnsTableSqlHelper.tbWeiba
                        + " where site_id = "
                        + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid(), null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public boolean hasWeiba(Weiba weiba) {
        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "select * from tb_weiba where weiba_id = ? and site_id="
                + Thinksns.getMySite().getSite_id() + " and my_uid="
                + Thinksns.getMy().getUid();
        Cursor cursor = database.rawQuery(sql,
                new String[]{String.valueOf(weiba.getWeibaId())});

        boolean result = cursor.moveToFirst();
        cursor.close();

        return result;
    }

    public ListData<SociaxItem> getWeibaList() {
        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "select * from tb_weiba where site_id="
                + Thinksns.getMySite().getSite_id() + " and my_uid="
                + Thinksns.getMy().getUid(); // order by _id asc";
        Cursor cursor = database.rawQuery(sql, null);
        ListData<SociaxItem> weibaList = null;
        Weiba weiba = null;
        if (cursor.moveToFirst()) {
            weibaList = new ListData<SociaxItem>();
            do {
                weiba = new Weiba();
                weiba.setWeibaId(Integer.parseInt(cursor.getString(cursor
                        .getColumnIndex("weiba_id"))));
                weiba.setWeibaName(cursor.getString(cursor
                        .getColumnIndex("name")));
                weiba.setWeibaIcon(cursor.getString(cursor
                        .getColumnIndex("icon_url")));
                weiba.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
                weiba.setIconData(cursor.getBlob(cursor
                        .getColumnIndex("icon_data")));
                weiba.setFollowCount(cursor.getInt(cursor
                        .getColumnIndex("f_count")));
                weiba.setThreadCount(cursor.getInt(cursor
                        .getColumnIndex("t_count")));
                weiba.setFollowstate(cursor.getInt(cursor
                        .getColumnIndex("f_state")));
                weiba.setPostPermission(cursor.getInt(cursor
                        .getColumnIndex("post_permission")));

                weibaList.add(weiba);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return weibaList;
    }

    public void clearCacheDB() {
        tableSqlHelper.getWritableDatabase().execSQL(
                "delete from " + ThinksnsTableSqlHelper.tbWeiba
                        + " where site_id = "
                        + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid());
    }

    @Override
    public void close() {
        tableSqlHelper.close();
    }

}
