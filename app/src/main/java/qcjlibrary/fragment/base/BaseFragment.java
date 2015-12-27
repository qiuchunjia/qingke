package qcjlibrary.fragment.base;

/***********************************************************************
 * Module:  BaseFragment.java
 * Author:  qcj qq:260964739
 * Purpose: Defines the Class BaseFragment
 ***********************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;

import qcjlibrary.activity.base.BaseActivity;
import qcjlibrary.adapter.base.BAdapter;
import qcjlibrary.listview.base.BaseListView;
import qcjlibrary.model.ModelMsg;
import qcjlibrary.model.ModelUser;
import qcjlibrary.model.base.Model;
import qcjlibrary.request.base.Request;
import qcjlibrary.response.DataAnalyze;
import qcjlibrary.response.HttpResponceListener;
import qcjlibrary.util.ToastUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhiyicx.zycx.sociax.android.Thinksns;

/**
 * fragment的基类，其它fragment必須實現他，不要隨意修改這個基類
 **/
public abstract class BaseFragment extends Fragment implements OnClickListener,
        HttpResponceListener {
    /**
     * adapter 基类
     */
    private BAdapter mAdapter;
    /**
     * listview基类
     */
    private BaseListView mListView;
    /**
     * list的基类
     */
    private List mList;
    /**
     * fragment的布局view
     */
    private View mView;
    /**
     * @pdOid 0d3b4e48-b478-406c-acc4-17fe74117a81
     */
    private ModelUser mUser;
    /**
     * 加載動畫
     */
    private View mLoadingView;
    /**
     * application基類
     */
    public Thinksns mApp;
    public BaseActivity mActivity;
    /**
     * mInflater
     */
    public LayoutInflater mInflater;

    // public FragmentManager mFManager = getChildFragmentManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mApp = (Thinksns) getActivity().getApplication();
            mActivity = (BaseActivity) getActivity();
            mView = inflater.inflate(getLayoutId(), null);
            mInflater = inflater;
            initIntentData();
            initView();
            initData();
            initListener();
            doRefreshNew();
            // 新鲜事
        } else {
            // 当存在mview的时候就调用清零
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        return mView;
    }

    /**
     * 判断这个用户是否登录过
     * <p/>
     * 如果个别fragement对用户开发的话，就重新这个方法，然后return true就可以了
     *
     * @return
     */
    public boolean checkTheUser() {
        ModelUser user = mApp.getUser();
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * @param viewid
     * @pdOid 获取id并返回相应的控件
     */
    protected View findViewById(int viewid) {
        return mView.findViewById(viewid);
    }

    /**
     * 初始化intent的數據 ，没有的话 就直接返回空
     */
    public abstract void initIntentData();

    /**
     * 获取布局的id
     */
    public abstract int getLayoutId();

    /**
     * 初始化各個控件
     */
    public abstract void initView();

    /**
     * 設置監聽器
     */
    public abstract void initListener();

    /**
     * 初始化數據
     */
    public abstract void initData();

    /**
     * 獲取基類baseListview
     */
    public BaseListView getListView() {
        if (mListView != null) {
            return mListView;
        }
        return null;
    }

    /**
     * @param 传入的listview
     */
    public void setListView(BaseListView lv) {
        this.mListView = lv;
    }

    /**
     * 获取baseAdapter
     */
    public BAdapter getAdapter() {
        if (mAdapter != null) {
            return mAdapter;
        }
        return null;
    }

    /**
     * @param 傳入的adapter
     */
    public void setAdapter(BAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void doRefreshNew() {
        if (mAdapter != null) {
            mAdapter.doRefreshNew();
        }
    }

    public static final int IMAGE_CODE = 1; // 取照片的时做的标记
    public static final int CAPTURE_CODE = 2; // 取照片的时做的标记

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            ContentResolver resolver = mActivity.getContentResolver();
            if (resultCode != Activity.RESULT_OK) {
                return;
            } else if (requestCode == IMAGE_CODE) {
                Uri originalUri = data.getData();
                Log.i("TestFile", originalUri.toString());
                if (originalUri != null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver,
                            originalUri);
                    compressPhotoAndDisplay(bitmap);
                    String filename = getFileByUri(originalUri);
                    getFile(filename);
                }
            } else if (requestCode == CAPTURE_CODE) {
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.i("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }
                String name = new DateFormat().format("yyyyMMdd_hhmmss",
                        Calendar.getInstance(Locale.CHINA)) + ".jpg";
                Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

                FileOutputStream b = null;
                // ???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？
                File file = new File("/sdcard/myImage/");
                file.mkdirs();// 创建文件夹
                String fileName = "/sdcard/myImage/" + name;
                Log.i("TestFile", fileName.toString());
                try {
                    b = new FileOutputStream(fileName);
                    compressOutStream2Bitmap(bitmap, b);// 把数据写入文件
                    getFile(fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            Log.i("tag", e.toString() + "");
            ToastUtils.showToast("获取图片抛出了异常！！");
        }
    }

    /**
     * 通过uri到获取文件路径
     *
     * @param originalUri
     * @return
     */
    @SuppressWarnings("deprecation")
    private String getFileByUri(Uri originalUri) {
        // 获取照片文件路径
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(originalUri, proj, null,
                null, null);
        int actual_image_column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String img_path = cursor.getString(actual_image_column_index);
        Log.i("imagePath", img_path + "");
        return img_path;
    }

    /**
     * 同比例压缩图片，并且显示图片如果需要显示这个图片的话就 显示就交给子类重新这个方法，并实现
     *
     * @param photo
     */
    public Bitmap compressPhotoAndDisplay(Bitmap originBitmap) {
        // TODO 统统同比例压缩一倍， 这压缩太粗糙， 留在迭代开发做，现在如果做了，迭代开发干什么？
        originBitmap = ThumbnailUtils.extractThumbnail(originBitmap, 51, 108);
        return originBitmap;
    }

    public Bitmap compressOutStream2Bitmap(Bitmap bitmap, OutputStream stream) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return bitmap;
    }

    /**
     * 获取文件
     *
     * @param path
     * @return
     */
    public File getFile(String path) {
        if (path != null) {
            return new File(path);
        }
        return null;
    }

    /**
     * 打开相册
     */
    public void openTheGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_CODE);
    }

    /**
     * 打开照相机
     */
    public void openTheCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_CODE);
    }

    // ----------------------------------我是本区域邪恶的分界线------------------------------------------------------

    /************************************
     * 网络请求传递，以及返回数据解析
     ***************************************/
    private Request mRequst;
    public static final int REQUEST_GET = 0;
    public static final int REQUEST_POST = 1;

    public void sendRequest(RequestParams params,
                            Class<? extends Model> modeltype, int requsetType) {
        if (params != null && modeltype != null) {
            if (mRequst == null) {
                mRequst = Request.getSingleRequest();
            }
            if (requsetType == 0) {
                mRequst.get(mApp.getHostUrl(), params,
                        new MyAsyncHttpResponseHandler(modeltype));
            } else {
                mRequst.post(mApp.getHostUrl(), params,
                        new MyAsyncHttpResponseHandler(modeltype));
            }
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.zhiyisoft.associations.request.base.HttpResponceListener#
     * onResponseProgress(long, long)
     */
    @Override
    public void onResponseProgress(long bytesWritten, long totalSize) {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.zhiyisoft.associations.request.base.HttpResponceListener#
     * onResponceSuccess(java.lang.Object)
     */
    @Override
    public Object onResponceSuccess(String str, Class class1) {
        return DataAnalyze.parseDataByGson(str, class1);
    }

    /**
     * 判断返回的网络数据是否成功
     *
     * @param object
     * @return
     */
    public boolean judgeTheMsg(Object object) {
        if (object instanceof ModelMsg) {
            ModelMsg msg = (ModelMsg) object;
            if (msg.getCode() == 0) {
                return true;
            }
            ToastUtils.showToast(msg.getMessage());
            return false;
        }
        return false;
    }

    private class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        private Class type;

        public MyAsyncHttpResponseHandler(Class ResultType) {
            this.type = ResultType;
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            ToastUtils.showToast("请求异常");
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            onResponseProgress(bytesWritten, totalSize);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            if (arg2 != null) {
                String result = new String(arg2);
                onResponceSuccess(result, type);
            }
        }

    }

    /************************************ 网络请求传递，以及返回数据解析end ***************************************/
}