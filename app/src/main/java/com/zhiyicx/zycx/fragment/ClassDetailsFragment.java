package com.zhiyicx.zycx.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.zycx.R;
import com.zhiyicx.zycx.net.NetComTools;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ClassDetailsFragment extends Fragment {

    private Context mContext;

    static public ClassDetailsFragment newInstance(
            String title, String photo_url, String name, String tinfo, String cinfo) {
        ClassDetailsFragment f = new ClassDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("photo", photo_url);
        bundle.putString("name", name);
        bundle.putString("tinfo", tinfo);
        bundle.putString("cinfo", cinfo);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.class_details_fragment, container, false);
        Bundle bundle = getArguments();
        String tmp = bundle.getString("title", null);
        TextView textView = (TextView) view.findViewById(R.id.txt_class_title);
        if (!TextUtils.isEmpty(tmp))
            textView.setText(tmp);
        textView = (TextView) view.findViewById(R.id.txt_name);
        tmp = bundle.getString("name", null);
        if (!TextUtils.isEmpty(tmp))
            textView.setText(tmp);
        textView = (TextView) view.findViewById(R.id.txt_tech_info);
        tmp = bundle.getString("tinfo", null);
        if (!TextUtils.isEmpty(tmp))
            textView.setText(tmp);
        textView = (TextView) view.findViewById(R.id.txt_class_info);
        tmp = bundle.getString("cinfo", null);
        if (!TextUtils.isEmpty(tmp))
            textView.setText(tmp);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_photo);
        tmp = bundle.getString("photo", null);
        if (!TextUtils.isEmpty(tmp))
            NetComTools.getInstance(mContext).loadNetImage(imageView, tmp, R.drawable.header, 0, 0);
        return view;
    }


}
