package com.zhiyicx.zycx.sociax.adapter;

import com.zhiyicx.zycx.sociax.android.ThinksnsAbscractActivity;
import com.zhiyicx.zycx.sociax.exception.ApiException;
import com.zhiyicx.zycx.sociax.exception.DataInvalidException;
import com.zhiyicx.zycx.sociax.exception.ListAreEmptyException;
import com.zhiyicx.zycx.sociax.exception.VerifyErrorException;
import com.zhiyicx.zycx.sociax.modle.Comment;
import com.zhiyicx.zycx.sociax.modle.ListData;
import com.zhiyicx.zycx.sociax.modle.SociaxItem;
import com.zhiyicx.zycx.sociax.modle.Weibo;

/**
 * 类说明：
 *
 * @author Povol
 * @version 1.0
 * @date 2013-2-4
 */
public class GroupWeiboComListAdapter extends CommentListAdapter {

    private Weibo weibo;

    public GroupWeiboComListAdapter(ThinksnsAbscractActivity context,
                                    ListData<SociaxItem> data) {
        super(context, data);
    }

    public GroupWeiboComListAdapter(ThinksnsAbscractActivity context,
                                    ListData<SociaxItem> data, Weibo weibo) {
        super(context, data);
        this.weibo = weibo;
        super.isShowToast = false;
    }

    @Override
    public ListData<SociaxItem> refreshNew(int count)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return thread.getApp().getGroupApi()
                .weiboComments(weibo, null, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshHeader(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return thread.getApp().getGroupApi()
                .weiboCommentsHeader(weibo, (Comment) obj, PAGE_COUNT);
    }

    @Override
    public ListData<SociaxItem> refreshFooter(SociaxItem obj)
            throws VerifyErrorException, ApiException, ListAreEmptyException,
            DataInvalidException {
        return thread.getApp().getGroupApi()
                .weiboCommentsFooter(weibo, (Comment) obj, PAGE_COUNT);
    }

}
