package com.zhiyicx.zycx.modle;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.zhiyicx.zycx.adapter.LoadListAdapter;
import com.zhiyicx.zycx.config.MyConfig;
import com.zhiyicx.zycx.net.JsonDataListener;
import com.zhiyicx.zycx.net.NetComTools;
import com.zhiyicx.zycx.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/14.
 */
public class NetData {

    private static final String TAG = "NetData";

    static public final int mListSize = 20;

    static private ArrayList<JSONObject> getArrayList(JSONArray array)
            throws JSONException {
        if (array == null || array.length() <= 0)
            return null;
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++)
            list.add(array.getJSONObject(i));
        return list;
    }

    public static void ZiXunSearchList(final Context context,
                                       final Handler handler, int count, String key, int footer) {
        String url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count + "&key=" + key;
        if (footer != -1)
            url += "&lastid=" + footer;
        NetComTools.getInstance(context).getNetJson(url,
                new JsonDataListener() {
                    @Override
                    public void OnReceive(JSONObject jsonObject) {
                        Log.d(TAG, "ZiXun list:" + jsonObject.toString());
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        mainMsg.arg1 = LoadListAdapter.SEARCH_NEW;
                        try {
                            int ret = jsonObject.getInt("code");
                            if (ret == 0) {
                                JSONObject data = (JSONObject) jsonObject
                                        .get("data");
                                JSONArray listJson = data.getJSONArray("list");
                                mainMsg.what = 0;
                                mainMsg.obj = getArrayList(listJson);
                            } else {
                                String msg = jsonObject.optString("message");
                                if (TextUtils.isEmpty(msg))
                                    mainMsg.obj = "暂无相关内容";
                                else
                                    mainMsg.obj = msg;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendMessage(mainMsg);
                        }
                    }

                    @Override
                    public void OnError(String error) {
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        mainMsg.arg1 = LoadListAdapter.SEARCH_NEW;
                        mainMsg.obj = "数据格式错误";
                        handler.sendMessage(mainMsg);
                        Log.d("ZiXunView", "Get ZiXun list error, " + error);
                    }
                });
    }

    public static void getZiXunNewList(final Context context,
                                       final Handler handler, int count, int type) {
        String url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count;
        if (type != 0)
            url += "&cid=" + type;
        if (type == -1)
            url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                    + "&limit=" + count + "&isColl=" + 1;

        NetComTools.getInstance(context).getNetJson(url,
                new JsonDataListener() {
                    @Override
                    public void OnReceive(JSONObject jsonObject) {
                        Log.d(TAG, "ZiXun list:" + jsonObject.toString());
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        try {
                            int ret = jsonObject.getInt("code");
                            if (ret == 0) {
                                JSONObject data = (JSONObject) jsonObject
                                        .get("data");
                                JSONArray listJson = data.getJSONArray("list");
                                mainMsg.what = 0;
                                mainMsg.obj = getArrayList(listJson);
                                mainMsg.arg1 = LoadListAdapter.REFRESH_NEW;
                            } else {
                                mainMsg.obj = "暂无内容";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendMessage(mainMsg);
                        }
                    }

                    @Override
                    public void OnError(String error) {
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        mainMsg.obj = "数据格式错误";
                        handler.sendMessage(mainMsg);
                        Log.d("ZiXunView", "Get ZiXun list error, " + error);
                    }
                });
    }

    public static void ZiXunRefHeaderList(final Context context,
                                          final Handler handler, int count, int type, int head) {
        String url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count + "&maxid=" + head;
        if (type != 0)
            url += "&cid=" + type;
        if (type == -1)
            url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                    + "&limit=" + count + "&isColl=" + 1 + "&maxid=" + head;
        NetComTools.getInstance(context).getNetJson(url,
                new JsonDataListener() {
                    @Override
                    public void OnReceive(JSONObject jsonObject) {
                        Log.d(TAG, "ZiXun list:" + jsonObject.toString());
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        try {
                            int ret = jsonObject.getInt("code");
                            if (ret == 0) {
                                JSONObject data = (JSONObject) jsonObject
                                        .get("data");
                                JSONArray listJson = data.getJSONArray("list");
                                mainMsg.what = 0;
                                mainMsg.obj = getArrayList(listJson);
                                mainMsg.arg1 = LoadListAdapter.REFRESH_HEADER;
                            } else
                                mainMsg.obj = "已无更多内容";
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendMessage(mainMsg);
                        }
                    }

                    @Override
                    public void OnError(String error) {
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        mainMsg.obj = "数据格式错误";
                        handler.sendMessage(mainMsg);
                        Log.d("ZiXunView", "Get ZiXun list error, " + error);
                    }
                });
    }

    public static void ZiXunRefFooterList(final Context context,
                                          final Handler handler, int count, int type, int footer) {
        String url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count + "&lastid=" + footer;
        if (type != 0)
            url += "&cid=" + type;
        if (type == -1)
            url = MyConfig.ZIXUN_LIST_URL + Utils.getTokenString(context)
                    + "&limit=" + count + "&isColl=" + 1 + "&lastid=" + footer;
        NetComTools.getInstance(context).getNetJson(url,
                new JsonDataListener() {
                    @Override
                    public void OnReceive(JSONObject jsonObject) {
                        Log.d(TAG, "ZiXun list:" + jsonObject.toString());
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        try {
                            int ret = jsonObject.getInt("code");
                            if (ret == 0) {
                                JSONObject data = (JSONObject) jsonObject
                                        .get("data");
                                JSONArray listJson = data.getJSONArray("list");
                                mainMsg.what = 0;
                                mainMsg.obj = getArrayList(listJson);
                                mainMsg.arg1 = LoadListAdapter.REFRESH_FOOTER;
                            } else
                                mainMsg.obj = "已无更多内容";
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendMessage(mainMsg);
                        }
                    }

                    @Override
                    public void OnError(String error) {
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        handler.sendMessage(mainMsg);
                        Log.d("ZiXunView", "Get ZiXun list error, " + error);
                    }
                });
    }

    public static void QiKanNewList(final Context context,
                                    final Handler handler, int count) {
        String url = MyConfig.QIKAN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Qikan list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONArray listJson = jsonObject.getJSONArray("data");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_NEW;
                    } else
                        mainMsg.obj = "暂无内容";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
                Log.d(TAG, "Get Qikan details error, " + error);
            }
        });
    }

    public static void QiKanRefHeaderList(final Context context,
                                          final Handler handler, int count, int header) {
        String url = MyConfig.QIKAN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count + "&maxsort=" + header;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Qikan list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONArray listJson = jsonObject.getJSONArray("data");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_HEADER;
                    } else
                        mainMsg.obj = "已无更多内容";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
                Log.d(TAG, "Get Qikan details error, " + error);
            }
        });
    }

    public static void QiKanRefFooterList(final Context context,
                                          final Handler handler, int count, int footer) {
        String url = MyConfig.QIKAN_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + count + "&lastsort=" + footer;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Qikan list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONArray listJson = jsonObject.getJSONArray("data");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_FOOTER;
                    } else
                        mainMsg.obj = "已无更多内容";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
                Log.d(TAG, "Get Qikan details error, " + error);
            }
        });
    }

    public static void QClassSearchList(final Context context,
                                        final Handler handler, int status, String key, int footer) {
        String url = MyConfig.QCLASS_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize + "&key=" + key + "&status=" + status;
        if (footer != -1)
            url += "&lastid=" + footer;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Qikan list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.arg1 = LoadListAdapter.SEARCH_NEW;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        JSONArray listJson = data.getJSONArray("list");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                    } else {
                        String msg = jsonObject.optString("message");
                        if (TextUtils.isEmpty(msg))
                            mainMsg.obj = "暂无相关内容";
                        else
                            mainMsg.obj = msg;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.arg1 = LoadListAdapter.SEARCH_NEW;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
                Log.d(TAG, "Get Qikan details error, " + error);
            }
        });
    }

    public static void QClassNewList(final Context context,
                                     final Handler handler, int status, int type) {
        String url = MyConfig.QCLASS_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize + "&status=" + status;
        if (type != 0)
            url += "&cid=" + type;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Qikan list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        JSONArray listJson = data.getJSONArray("list");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_NEW;
                    } else
                        mainMsg.obj = "暂无内容";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
                Log.d(TAG, "Get Qikan details error, " + error);
            }
        });
    }

    public static void QClassRefHeaderList(final Context context,
                                           final Handler handler, int status, int type, int head) {
        String url = MyConfig.QCLASS_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize + "&maxid=" + head + "&status="
                + status;
        if (type != 0)
            url += "&cid=" + type;
        NetComTools.getInstance(context).getNetJson(url,
                new JsonDataListener() {
                    @Override
                    public void OnReceive(JSONObject jsonObject) {
                        Log.d(TAG, "ZiXun list:" + jsonObject.toString());
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        try {
                            int ret = jsonObject.getInt("code");
                            if (ret == 0) {
                                JSONObject data = (JSONObject) jsonObject
                                        .get("data");
                                JSONArray listJson = data.getJSONArray("list");
                                mainMsg.what = 0;
                                mainMsg.obj = getArrayList(listJson);
                                mainMsg.arg1 = LoadListAdapter.REFRESH_HEADER;
                            } else
                                mainMsg.obj = "已无更多内容";
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendMessage(mainMsg);
                        }
                    }

                    @Override
                    public void OnError(String error) {
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        mainMsg.obj = "数据格式错误";
                        handler.sendMessage(mainMsg);
                        Log.d("ZiXunView", "Get ZiXun list error, " + error);
                    }
                });
    }

    public static void QClassRefFooterList(final Context context,
                                           final Handler handler, int status, int type, int footer) {
        String url = MyConfig.QCLASS_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize + "&lastid=" + footer + "&status="
                + status;
        if (type != 0)
            url += "&cid=" + type;

        NetComTools.getInstance(context).getNetJson(url,
                new JsonDataListener() {
                    @Override
                    public void OnReceive(JSONObject jsonObject) {
                        Log.d(TAG, "ZiXun list:" + jsonObject.toString());
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        try {
                            int ret = jsonObject.getInt("code");
                            if (ret == 0) {
                                JSONObject data = (JSONObject) jsonObject
                                        .get("data");
                                JSONArray listJson = data.getJSONArray("list");
                                mainMsg.what = 0;
                                mainMsg.obj = getArrayList(listJson);
                                mainMsg.arg1 = LoadListAdapter.REFRESH_FOOTER;
                            } else
                                mainMsg.obj = "已无更多内容";
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendMessage(mainMsg);
                        }
                    }

                    @Override
                    public void OnError(String error) {
                        Message mainMsg = new Message();
                        mainMsg.what = 1;
                        mainMsg.obj = "数据格式错误";
                        handler.sendMessage(mainMsg);
                        Log.d("ZiXunView", "Get ZiXun list error, " + error);
                    }
                });
    }

    public static void QuestionSearchList(final Context context,
                                          final Handler handler, String key) {
        String url = MyConfig.QUESTION_SEARCH_URL
                + Utils.getTokenString(context) + "&key=" + key /*
                                                                 * + "&limit=" +
																 * mListSize
																 */;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Question list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.arg1 = LoadListAdapter.SEARCH_NEW;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONArray listJson = jsonObject.getJSONArray("data");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                    } else {
                        String msg = jsonObject.optString("message");
                        if (TextUtils.isEmpty(msg))
                            mainMsg.obj = "暂无相关内容";
                        else
                            mainMsg.obj = msg;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.arg1 = LoadListAdapter.SEARCH_NEW;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
            }
        });
    }

    public static void QuestionNewList(final Context context,
                                       final Handler handler, int type) {
        String url = MyConfig.QUESTION_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize;
        if (type == -1)
            url += "&is_me=1";
        else
            url += "&id=" + type;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Question list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray listJson = data.getJSONArray("list");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_NEW;

                    } else {
                        // String msg = jsonObject.optString("message");
                        mainMsg.obj = "暂无内容";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
            }
        });
    }

    public static void QuestionHeaderList(final Context context,
                                          final Handler handler, int type, int header) {
        String url = MyConfig.QUESTION_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize + "&maxid=" + header;
        if (type == -1)
            url += "&is_me=1";
        else
            url += "&id=" + type;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Question list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray listJson = data.getJSONArray("list");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_HEADER;

                    } else {
                        // String msg = jsonObject.optString("message");
                        mainMsg.obj = "已无更多内容";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
            }
        });
    }

    public static void QuestionFooterList(final Context context,
                                          final Handler handler, int type, int footer) {
        String url = MyConfig.QUESTION_LIST_URL + Utils.getTokenString(context)
                + "&limit=" + mListSize + "&lastid=" + footer;
        if (type == -1)
            url += "&is_me=1";
        else
            url += "&id=" + type;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Question list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray listJson = data.getJSONArray("list");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_FOOTER;

                    } else {
                        // String msg = jsonObject.optString("message");
                        mainMsg.obj = "已无更多内容";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
            }
        });
    }

    public static void QuestionTopNew(final Context context,
                                      final Handler handler, int tid) {
        String url = MyConfig.QUESTION_TOPLIST_URL
                + Utils.getTokenString(context) + "&limit=" + mListSize;
        url += "&tid=" + tid;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Question list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray listJson = data.getJSONArray("questionlist");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_NEW;

                    } else {
                        // String msg = jsonObject.optString("message");
                        mainMsg.obj = "暂无内容";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
            }
        });
    }

    public static void QuestionTopFooter(final Context context,
                                         final Handler handler, int tid, int footer) {
        String url = MyConfig.QUESTION_TOPLIST_URL
                + Utils.getTokenString(context) + "&limit=" + mListSize;
        url += "&tid=" + tid + "&lastid=" + footer;
        NetComTools netComTools = NetComTools.getInstance(context);
        netComTools.getNetJson(url, new JsonDataListener() {
            @Override
            public void OnReceive(JSONObject jsonObject) {
                // Log.d(TAG, "Question list data:" + jsonObject.toString());
                Message mainMsg = new Message();
                mainMsg.what = 1;
                try {
                    int ret = jsonObject.getInt("code");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray listJson = data.getJSONArray("questionlist");
                        mainMsg.what = 0;
                        mainMsg.obj = getArrayList(listJson);
                        mainMsg.arg1 = LoadListAdapter.REFRESH_FOOTER;

                    } else {
                        // String msg = jsonObject.optString("message");
                        mainMsg.obj = "暂无内容";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(mainMsg);
                }
            }

            @Override
            public void OnError(String error) {
                Log.d(TAG, "Get Question list error, " + error);
                Message mainMsg = new Message();
                mainMsg.what = 1;
                mainMsg.obj = "数据格式错误";
                handler.sendMessage(mainMsg);
            }
        });
    }

}
