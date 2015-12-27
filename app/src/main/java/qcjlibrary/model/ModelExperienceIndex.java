package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午2:24:04 类描述：这个类是实现
 */

public class ModelExperienceIndex extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ModelExperience first; // 经历的 第一个数据
    private ModelExperience second; // 经历的第二个的数据

    public ModelExperience getFirst() {
        return first;
    }

    public void setFirst(ModelExperience first) {
        this.first = first;
    }

    public ModelExperience getSecond() {
        return second;
    }

    public void setSecond(ModelExperience second) {
        this.second = second;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
