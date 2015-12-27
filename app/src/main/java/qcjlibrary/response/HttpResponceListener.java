package qcjlibrary.response;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：
 * <p/>
 * <p/>
 * 下午2:17:18 类描述：这个类是实现网络数据
 */

public interface HttpResponceListener {
    /**
     * 处理进度条
     *
     * @param bytesWritten
     * @param totalSize
     */
    public void onResponseProgress(long bytesWritten, long totalSize);

    /**
     * 处理返回的结果
     *
     * @param object
     * @return
     */
    public Object onResponceSuccess(String str, Class class1);
}
