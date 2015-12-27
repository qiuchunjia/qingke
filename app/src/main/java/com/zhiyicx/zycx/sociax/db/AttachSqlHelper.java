package com.zhiyicx.zycx.sociax.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.modle.ImageAttach;

public class AttachSqlHelper extends SqlHelper {

    private static AttachSqlHelper instance;
    private ThinksnsTableSqlHelper tableSqlHelper;

    AttachSqlHelper(Context context) {
        this.tableSqlHelper = new ThinksnsTableSqlHelper(context, DB_NAME, null, VERSION);
    }

    public static AttachSqlHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AttachSqlHelper(context);
        }
        return instance;
    }

    public long addAttach(ImageAttach iAttach) {
        ContentValues values = new ContentValues();
        values.put("weiboId", iAttach.getWeiboId());
        values.put("name", iAttach.getName());
        values.put("small", iAttach.getSmall());
        values.put("middle", iAttach.getMiddle());
        values.put("normal", iAttach.getNormal());

        values.put("site_id", Thinksns.getMySite().getSite_id());
        values.put("my_uid", Thinksns.getMy().getUid());

        long result = tableSqlHelper.getWritableDatabase().insert(ThinksnsTableSqlHelper.attachTable, null, values);
        return result;
    }

    public boolean getAttachByWeiboId(int weiboId) {

        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "select * from attach where weiboId = ?";
        Cursor cursor = database.rawQuery(sql, new String[]
                {String.valueOf(weiboId)});

        boolean result = cursor.moveToFirst();
        cursor.close();

        return result;
    }

    /**
     * 根据微博id来获得微博的附件
     *
     * @param weiboId
     * @return
     */
    public List<ImageAttach> getAttachsByWeiboId(int weiboId) {
        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "select * from attach where weiboId = ?";
        Cursor cursor = database.rawQuery(sql, new String[]
                {String.valueOf(weiboId)});
        List<ImageAttach> lAttachs = null;
        ImageAttach iAttach = null;
        if (cursor.moveToFirst()) {
            lAttachs = new ArrayList<ImageAttach>();
            do {
                iAttach = new ImageAttach();
                iAttach.setWeiboId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("weiboId"))));
                iAttach.setName(cursor.getString(cursor.getColumnIndex("name")));
                iAttach.setSmall(cursor.getString(cursor.getColumnIndex("small")));
                iAttach.setMiddle(cursor.getString(cursor.getColumnIndex("middle")));
                iAttach.setNormal(cursor.getString(cursor.getColumnIndex("normal")));

                lAttachs.add(iAttach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lAttachs;
    }

    /**
     * 删除数据库缓存
     */
    public void clearCacheDB() {
        tableSqlHelper.getWritableDatabase().execSQL(
                "delete from attach where site_id = " + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid());
    }

    @Override
    public void close() {
        tableSqlHelper.close();
    }

}
