package qcjlibrary.model.base;

import java.io.Serializable;

/**
 * author：qiuchunjia time：下午2:58:40 类描述：这个类是实现
 */

public class Model implements Serializable {
    public String lastid;
    public String maxid;

    public String getLastid() {
        return lastid;
    }

    public void setLastid(String lastid) {
        this.lastid = lastid;
    }

    public String getMaxid() {
        return maxid;
    }

    public void setMaxid(String maxid) {
        this.maxid = maxid;
    }

}
