package qcjlibrary.api;

import qcjlibrary.model.ModelFoodSearch;
import qcjlibrary.model.ModelFoodSearch0;
import qcjlibrary.model.ModelFoodSearch1;

import com.loopj.android.http.RequestParams;

/**
 * author：qiuchunjia time：上午10:34:25 类描述：这个类是实现 食疗模块的接口
 */

public interface FoodIm {
    // 接口需要的操作参数
    public static final String SHILIAO = "Shiliao";
    public static final String FOOD_SEARCH = "food_search";
    public static final String INDEX = "index";
    public static final String FOOD_DETAIL = "food_detail";
    public static final String FOOD_SIDE_DETAIL = "food_side_detail";
    // 接口需要传的值的键
    /******
     * string key 搜索内容 选填
     * <p/>
     * int state 食疗类型（0食材，1食疗方） 必填
     * <p/>
     * int type_id 食材类型 选填
     * <p/>
     * int p 页数 选填
     * <p/>
     * string table 食疗方类型（普通食疗方sidefood,癌种食疗方cancer,对症食疗方symptom） 选填
     */
    public static final String KEY = "key"; // 搜索内容 选填
    public static final String STATE = "state";
    public static final String TYPE_ID = "type_id";
    public static final String P = "p";
    public static final String TABLE = "table";
    public static final String ID = "id"; // 食疗方id 必填 已经食疗方的id

    /**
     * demo-qingko.zhiyicx.com/index.php?app=api&mod=Shiliao&act=food_search
     * <p/>
     * string key 搜索内容 选填
     * <p/>
     * int state 食疗类型（0食材，1食疗方） 必填
     * <p/>
     * int type_id 食材类型 选填
     * <p/>
     * int p 页数 选填
     * <p/>
     * string table 食疗方类型（普通食疗方sidefood,癌种食疗方cancer,对症食疗方symptom） 选填
     *
     * @param model
     * @return
     */
    public RequestParams food_search(ModelFoodSearch foodSearch);

    /**
     * demo-qingko.zhiyicx.com/index.php?app=api&mod=Shiliao&act=index
     *
     * @return
     */
    public RequestParams index();

    /**
     * 食疗-食材详情 int id 食材id 必填
     * <p/>
     * <p/>
     * demo-qingko.zhiyicx.com/index.php?app=api&mod=Shiliao&act=food_detail&id=
     * 1
     *
     * @param model
     * @return
     */
    public RequestParams food_detail(ModelFoodSearch0 search0);

    /**
     * 食疗-食疗方详情 int id 食疗方id 必填
     * <p/>
     * demo-qingko.zhiyicx.com/index.php?app=api&mod=Shiliao&act=
     * food_side_detail&id=1
     *
     * @param model
     * @return
     */
    public RequestParams food_side_detail(ModelFoodSearch1 search1);
}
