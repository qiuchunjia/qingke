package com.zhiyicx.zycx.sociax.modle;

import java.io.Serializable;
import java.util.ArrayList;

public class ListData<T extends SociaxItem> extends ArrayList<SociaxItem>
        implements Serializable {

    private static final long serialVersionUID = 12L;

    public static enum Position {
        BEGINING, END
    }

    public static enum DataType {
        COMMENT, WEIBO, USER, RECEIVE, FOLLOW, SEARCH_USER
    }

}
