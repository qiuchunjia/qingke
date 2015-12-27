package com.zhiyicx.zycx.sociax.api;

import java.io.File;

import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.ReceiveComment;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.User;
import com.zhiyicx.zycx.sociax.modle.Weibo;

public interface ApiGroup {
    static final String MOD_NAME = "Group";
    static final String SHOW_STATUSES_TYPE = "showStatusType"; //
    static final String SHOW_STATUSES = "showStatuses";
    static final String SHOW_ATME_STATUSES = "showAtmeStatuses";
    static final String SHOW_STATUS_COMMENTS = "showStatusComments"; // 群组内评论我的
    static final String GROUP_MEMBERS = "groupMembers"; //

    static final String WEIBO_DETAI = "weiboDetai";
    static final String WEIBO_COMMENTS = "WeiboComments";

    static final String UPDATE_STATUS = "updateStatus";
    static final String UPLOAD_STATUS = "uploadStatus";
    static final String REPOST_STATUSES = "repostStatuses";

    static final String COMMENT_STATUSES = "commentStatuses";

    public ListData<SociaxItem> showStatuesType() throws ApiException;

    public ListData<SociaxItem> showStatuses(int count, int type)
            throws ApiException;

    public ListData<SociaxItem> showStatusesHeader(Weibo item, int count,
                                                   int type) throws ApiException;

    public ListData<SociaxItem> showStatusesFooter(Weibo item, int count,
                                                   int type) throws ApiException;

    public ListData<SociaxItem> showAtmeStatuses(int count) throws ApiException;

    public ListData<SociaxItem> showAtmeStatusesHeader(Weibo item, int count)
            throws ApiException;

    public ListData<SociaxItem> showAtmeStatusesFooter(Weibo item, int count)
            throws ApiException;

    public ListData<SociaxItem> showStatusComments(int count)
            throws ApiException;

    public ListData<SociaxItem> showStatusCommentsHeader(ReceiveComment item,
                                                         int count) throws ApiException;

    public ListData<SociaxItem> showStatusCommentsFooter(ReceiveComment item,
                                                         int count) throws ApiException;

    public ListData<SociaxItem> groupMembers(int count) throws ApiException;

    public ListData<SociaxItem> groupMembersHeader(User user, int count)
            throws ApiException;

    public ListData<SociaxItem> groupMembersFooter(User user, int count)
            throws ApiException;

    public ListData<SociaxItem> weiboComments(Weibo item, Comment comment,
                                              int count) throws ApiException;

    public ListData<SociaxItem> weiboCommentsHeader(Weibo item,
                                                    Comment comment, int count) throws ApiException;

    public ListData<SociaxItem> weiboCommentsFooter(Weibo item,
                                                    Comment comment, int count) throws ApiException;

    public boolean updateStatus(Weibo weibo) throws ApiException;

    public boolean uploadStatus(Weibo weibo, File file) throws ApiException;

    public boolean repostStatuses(Weibo weibo, boolean isComment)
            throws ApiException;

    public boolean commentStatuses(Comment comment) throws ApiException;
}
