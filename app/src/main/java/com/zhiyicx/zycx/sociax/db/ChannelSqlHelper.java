package com.zhiyicx.zycx.sociax.db;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.modle.Channel;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

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
public class ChannelSqlHelper extends SqlHelper {

    private static ChannelSqlHelper instance;
    private ThinksnsTableSqlHelper tableSqlHelper;

    private ChannelSqlHelper(Context context) {
        this.tableSqlHelper = new ThinksnsTableSqlHelper(context, DB_NAME, null, VERSION);
    }

    public static ChannelSqlHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ChannelSqlHelper(context);
        }
        return instance;
    }

    public long addChannel(ListData<SociaxItem> channelList) {
        int size = channelList.size();
        long l = 0;
        for (int i = 0; i < size; i++) {
            Channel channel = (Channel) channelList.get(i);
            if (!hasWeiba(channel)) {
                ContentValues values = new ContentValues();
                values.put("channel_id", channel.getId());
                values.put("title", channel.getcName());
                values.put("sort_id", channel.getSortId());
                values.put("icon_url", channel.getcUrl());
                values.put("site_id", Thinksns.getMySite().getSite_id());
                values.put("my_uid", Thinksns.getMy().getUid());

                l = tableSqlHelper.getWritableDatabase().insert(ThinksnsTableSqlHelper.tbChannel, null, values);
            }
        }
        return l;
    }

    public long addChannel(Channel channel) {

        ContentValues values = new ContentValues();
        values.put("channel_id", channel.getId());
        values.put("title", channel.getcName());
        values.put("sort_id", channel.getSortId());
        values.put("icon_url", channel.getcUrl());
        values.put("site_id", Thinksns.getMySite().getSite_id());
        values.put("my_uid", Thinksns.getMy().getUid());

        return tableSqlHelper.getWritableDatabase().insert(ThinksnsTableSqlHelper.tbChannel, null, values);

    }

    public void deleteChannel(int count) {
        if (count >= 20) {
            tableSqlHelper.getWritableDatabase().execSQL(
                    "delete from " + ThinksnsTableSqlHelper.tbWeiba + " where site_id = "
                            + Thinksns.getMySite().getSite_id() + " and my_uid = " + Thinksns.getMy().getUid());
        } else if (count > 0 && count < 20) {
            String sql = "delete from " + ThinksnsTableSqlHelper.tbWeiba + " where channel_id in (select weiboId from "
                    + ThinksnsTableSqlHelper.tbWeiba + " where site_id = " + Thinksns.getMySite().getSite_id()
                    + " and my_uid = " + Thinksns.getMy().getUid() + " order by weiba_id limit " + count + ")";
            tableSqlHelper.getWritableDatabase().execSQL(sql);
        }
    }

    public boolean hasWeiba(Channel channel) {
        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "select * from " + ThinksnsTableSqlHelper.tbChannel + " where channel_id = ? and site_id="
                + Thinksns.getMySite().getSite_id() + " and my_uid=" + Thinksns.getMy().getUid();
        Cursor cursor = database.rawQuery(sql, new String[]
                {String.valueOf(channel.getId())});

        boolean result = cursor.moveToFirst();
        cursor.close();

        return result;
    }

    public ListData<SociaxItem> getChannelList() {
        SQLiteDatabase database = tableSqlHelper.getWritableDatabase();
        String sql = "select * from " + ThinksnsTableSqlHelper.tbChannel + " where site_id="
                + Thinksns.getMySite().getSite_id() + " and my_uid=" + Thinksns.getMy().getUid(); // order
        // by
        // _id
        // asc";
        Cursor cursor = database.rawQuery(sql, null);
        ListData<SociaxItem> channelList = null;
        Channel channel = null;
        if (cursor.moveToFirst()) {
            channelList = new ListData<SociaxItem>();
            do {
                channel = new Channel();
                channel.setId(cursor.getInt(cursor.getColumnIndex("channel_id")));
                channel.setcName(cursor.getString(cursor.getColumnIndex("title")));
                channel.setSortId(cursor.getInt(cursor.getColumnIndex("sort_id")));
                channel.setcUrl(cursor.getString(cursor.getColumnIndex("icon_url")));
                channelList.add(channel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return channelList;
    }

    /**
     * 删除数据库缓存
     */
    public void clearCacheDB() {
        tableSqlHelper.getWritableDatabase().execSQL(
                "delete from tb_channel where site_id = " + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid());
    }

    @Override
    public void close() {
        tableSqlHelper.close();
    }

}
