package com.zhiyicx.zycx.sociax.api;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

/**
 * 类说明： 频道接口
 *
 * @author povol
 * @version 1.0
 * @date Dec 5, 2012
 */
public interface ApiChannel {

    public static String MOD_NAME = "Channel"; // MOD 名称
    // act 名称
    public static String GET_ALL_CHANNEL = "get_all_channel"; // 获取频道列表
    public static String GET_CHANNEL_FEED = "get_channel_feed"; // 获取频道微博

    public ListData<SociaxItem> getAllChannel() throws ApiException;

    public ListData<SociaxItem> getChannelFeed(int channelId)
            throws ApiException;

    public ListData<SociaxItem> getChannelHeaderFeed(Weibo weibo, int channelId)
            throws ApiException;

    public ListData<SociaxItem> getChannelFooterFeed(Weibo weibo, int channelId)
            throws ApiException;

}
