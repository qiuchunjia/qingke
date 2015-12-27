package com.zhiyicx.zycx.sociax.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhiyicx.zycx.sociax.android.Thinksns;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.Message;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;

public class ChatMsgSqlhelper extends SqlHelper {
    private static ChatMsgSqlhelper instance;
    private ThinksnsTableSqlHelper msgSqlHelper;
    private ListData<SociaxItem> messageDatas;

    private ChatMsgSqlhelper(Context context) {
        this.msgSqlHelper = new ThinksnsTableSqlHelper(context, DB_NAME, null,
                VERSION);
    }

    public static ChatMsgSqlhelper getInstance(Context context) {
        if (instance == null) {
            instance = new ChatMsgSqlhelper(context);
        }

        return instance;
    }

    private int isread(boolean isread) {
        if (isread) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean forRead(int isread) {
        if (isread == 1) {
            return true;
        } else {
            return false;
        }
    }

    private int isLast(boolean islast) {
        if (islast) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean forLast(int islast) {
        if (islast == 1) {
            return true;
        } else {
            return false;
        }
    }

    private int isOnlyOne(boolean isOnly) {
        if (isOnly) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean forOnlyOne(int isOnly) {
        if (isOnly == 1) {
            return true;
        } else {
            return false;
        }
    }

    public long addMessage(Message message) {
        ContentValues map = new ContentValues();
        map.put("list_id", message.getListId());
        map.put("member_uid", message.getMemberUid());
        map.put("message_id", message.getMeesageId());
        map.put("new", message.getForNew());
        map.put("message_num", message.getMessageNum());
        map.put("member_num", message.getMemeberNum());
        map.put("list_ctime", message.getListCtime());
        // map.put("last_message", message.getLastMessage().toString());
        map.put("from_uid", message.getFromUid());
        map.put("to_uid", message.getToUid());
        map.put("content", message.getContent());
        map.put("title", message.getTitle());
        map.put("from_uname", message.getFromUname());
        map.put("from_face", message.getFromFace());
        map.put("mtime", message.getMtime());
        map.put("ctime", message.getCtime());
        map.put("site_id", Thinksns.getMySite().getSite_id());
        map.put("my_uid", Thinksns.getMy().getUid());

        return msgSqlHelper.getWritableDatabase().insert(
                ThinksnsTableSqlHelper.tbChatMsg, null, map);
    }

    public ListData<SociaxItem> getMessageListById(int listId) {
        Cursor cursor = msgSqlHelper.getReadableDatabase().query(
                ThinksnsTableSqlHelper.tbChatMsg,
                null,
                "site_id = " + Thinksns.getMySite().getSite_id()
                        + " and my_uid = " + Thinksns.getMy().getUid()
                        + " and list_id =" + listId, null, null, null,
                "message_id ASC");
        messageDatas = new ListData<SociaxItem>();

        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setListId(cursor.getInt(cursor
                        .getColumnIndex("list_id")));
                message.setMeesageId(cursor.getInt(cursor
                        .getColumnIndex("message_id")));
                message.setFromUid(cursor.getInt(cursor
                        .getColumnIndex("from_uid")));
                message.setContent(cursor.getString(cursor
                        .getColumnIndex("content")));
                message.setTitle(cursor.getString(cursor
                        .getColumnIndex("title")));
                message.setToUid(cursor.getInt(cursor.getColumnIndex("to_uid")));
                message.setFromFace(cursor.getString(cursor
                        .getColumnIndex("from_face")));
                message.setFromUname(cursor.getString(cursor
                        .getColumnIndex("from_uname")));
                message.setMtime(cursor.getInt(cursor.getColumnIndex("mtime")));
                message.setCtime(cursor.getString(cursor
                        .getColumnIndex("ctime")));
                messageDatas.add(message);
            } while (cursor.moveToNext());
        }
        return messageDatas;
    }

    public int getMsgListSize(int listId) {
        Cursor cursor = msgSqlHelper.getWritableDatabase()
                .rawQuery(
                        "select count(*) from "
                                + ThinksnsTableSqlHelper.tbChatMsg
                                + " where site_id = "
                                + Thinksns.getMySite().getSite_id()
                                + " and my_uid = " + Thinksns.getMy().getUid()
                                + " and list_id =" + listId, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public boolean hasMessage(int message_id) {
        SQLiteDatabase database = msgSqlHelper.getWritableDatabase();
        String sql = "select * from " + ThinksnsTableSqlHelper.tbChatMsg
                + " where message_id = ?";
        Cursor cursor = database.rawQuery(sql,
                new String[]{String.valueOf(message_id)});

        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    public boolean deleteMsg(int count, int listId) {
        if (count > 19) {
            msgSqlHelper.getWritableDatabase().execSQL(
                    "delete from " + ThinksnsTableSqlHelper.tbChatMsg
                            + " where site_id = "
                            + Thinksns.getMySite().getSite_id()
                            + " and my_uid = " + Thinksns.getMy().getUid()
                            + " and list_id =" + listId);
        } else if (count > 0 && count < 20) {
            String sql = "delete from " + ThinksnsTableSqlHelper.tbChatMsg
                    + " where message_id in (select weiboId from "
                    + ThinksnsTableSqlHelper.tbChatMsg + " where site_id = "
                    + Thinksns.getMySite().getSite_id() + " and my_uid = "
                    + Thinksns.getMy().getUid() + " and list_id =" + listId
                    + " order by list_id limit " + count + ")";
            msgSqlHelper.getWritableDatabase().execSQL(sql);
        }
        return false;
    }

    /**
     * 删除数据库缓存
     */
    public void clearCacheDB() {
        msgSqlHelper.getWritableDatabase().execSQL(
                "delete from tb_chat_detal where site_id = "
                        + Thinksns.getMySite().getSite_id() + " and my_uid = "
                        + Thinksns.getMy().getUid());
    }

    @Override
    public void close() {
        msgSqlHelper.close();
    }
}
