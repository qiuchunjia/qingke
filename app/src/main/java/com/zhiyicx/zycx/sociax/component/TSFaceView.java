package com.zhiyicx.zycx.sociax.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.zhiyicx.zycx.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TSFaceView extends LinearLayout implements
        AdapterView.OnItemClickListener {
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static final ArrayList<Integer> faceDisplayList = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final HashMap<Integer, String> facesKeySrc = new LinkedHashMap();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final HashMap<String, Integer> facesKeyString = new LinkedHashMap();
    private Context m_Context;
    private GridView m_GridView;
    private FaceAdapter m_faceAdapter;

    static {

        faceDisplayList.add(Integer.valueOf(R.drawable.aoman));
        faceDisplayList.add(Integer.valueOf(R.drawable.baiyan));
        faceDisplayList.add(Integer.valueOf(R.drawable.bishi));
        faceDisplayList.add(Integer.valueOf(R.drawable.bizui));
        faceDisplayList.add(Integer.valueOf(R.drawable.cahan));
        faceDisplayList.add(Integer.valueOf(R.drawable.caidao));
        faceDisplayList.add(Integer.valueOf(R.drawable.chajin));
        faceDisplayList.add(Integer.valueOf(R.drawable.cheer));
        faceDisplayList.add(Integer.valueOf(R.drawable.chong));
        faceDisplayList.add(Integer.valueOf(R.drawable.ciya));
        faceDisplayList.add(Integer.valueOf(R.drawable.da));
        faceDisplayList.add(Integer.valueOf(R.drawable.dabian));
        faceDisplayList.add(Integer.valueOf(R.drawable.dabing));
        faceDisplayList.add(Integer.valueOf(R.drawable.dajiao));
        faceDisplayList.add(Integer.valueOf(R.drawable.daku));
        faceDisplayList.add(Integer.valueOf(R.drawable.dangao));
        faceDisplayList.add(Integer.valueOf(R.drawable.danu));
        faceDisplayList.add(Integer.valueOf(R.drawable.dao));
        faceDisplayList.add(Integer.valueOf(R.drawable.deyi));
        faceDisplayList.add(Integer.valueOf(R.drawable.diaoxie));
        faceDisplayList.add(Integer.valueOf(R.drawable.e));
        faceDisplayList.add(Integer.valueOf(R.drawable.fadai));
        faceDisplayList.add(Integer.valueOf(R.drawable.fadou));
        faceDisplayList.add(Integer.valueOf(R.drawable.fan));
        faceDisplayList.add(Integer.valueOf(R.drawable.fanu));
        faceDisplayList.add(Integer.valueOf(R.drawable.feiwen));
        faceDisplayList.add(Integer.valueOf(R.drawable.fendou));
        faceDisplayList.add(Integer.valueOf(R.drawable.gangga));
        faceDisplayList.add(Integer.valueOf(R.drawable.geili));
        faceDisplayList.add(Integer.valueOf(R.drawable.gouyin));
        faceDisplayList.add(Integer.valueOf(R.drawable.guzhang));
        faceDisplayList.add(Integer.valueOf(R.drawable.haha));
        faceDisplayList.add(Integer.valueOf(R.drawable.haixiu));
        faceDisplayList.add(Integer.valueOf(R.drawable.haqian));
        faceDisplayList.add(Integer.valueOf(R.drawable.hua));
        faceDisplayList.add(Integer.valueOf(R.drawable.huaixiao));
        faceDisplayList.add(Integer.valueOf(R.drawable.hufen));
        faceDisplayList.add(Integer.valueOf(R.drawable.huishou));
        faceDisplayList.add(Integer.valueOf(R.drawable.huitou));
        faceDisplayList.add(Integer.valueOf(R.drawable.jidong));
        faceDisplayList.add(Integer.valueOf(R.drawable.jingkong));
        faceDisplayList.add(Integer.valueOf(R.drawable.jingya));
        faceDisplayList.add(Integer.valueOf(R.drawable.kafei));
        faceDisplayList.add(Integer.valueOf(R.drawable.keai));
        faceDisplayList.add(Integer.valueOf(R.drawable.kelian));
        faceDisplayList.add(Integer.valueOf(R.drawable.ketou));
        faceDisplayList.add(Integer.valueOf(R.drawable.kiss));
        faceDisplayList.add(Integer.valueOf(R.drawable.ku));
        faceDisplayList.add(Integer.valueOf(R.drawable.kuaikule));
        faceDisplayList.add(Integer.valueOf(R.drawable.kulou));
        faceDisplayList.add(Integer.valueOf(R.drawable.kun));
        faceDisplayList.add(Integer.valueOf(R.drawable.lanqiu));
        faceDisplayList.add(Integer.valueOf(R.drawable.lenghan));
        faceDisplayList.add(Integer.valueOf(R.drawable.liuhan));
        faceDisplayList.add(Integer.valueOf(R.drawable.liulei));
        faceDisplayList.add(Integer.valueOf(R.drawable.liwu));
        faceDisplayList.add(Integer.valueOf(R.drawable.love));
        faceDisplayList.add(Integer.valueOf(R.drawable.ma));
        faceDisplayList.add(Integer.valueOf(R.drawable.meng));
        faceDisplayList.add(Integer.valueOf(R.drawable.nanguo));
        faceDisplayList.add(Integer.valueOf(R.drawable.no));
        faceDisplayList.add(Integer.valueOf(R.drawable.ok));
        faceDisplayList.add(Integer.valueOf(R.drawable.peifu));
        faceDisplayList.add(Integer.valueOf(R.drawable.pijiu));
        faceDisplayList.add(Integer.valueOf(R.drawable.pingpang));
        faceDisplayList.add(Integer.valueOf(R.drawable.pizui));
        faceDisplayList.add(Integer.valueOf(R.drawable.qiang));
        faceDisplayList.add(Integer.valueOf(R.drawable.qinqin));
        faceDisplayList.add(Integer.valueOf(R.drawable.qioudale));
        faceDisplayList.add(Integer.valueOf(R.drawable.qiu));
        faceDisplayList.add(Integer.valueOf(R.drawable.quantou));
        faceDisplayList.add(Integer.valueOf(R.drawable.ruo));
        faceDisplayList.add(Integer.valueOf(R.drawable.se));
        faceDisplayList.add(Integer.valueOf(R.drawable.shandian));
        faceDisplayList.add(Integer.valueOf(R.drawable.shengli));
        faceDisplayList.add(Integer.valueOf(R.drawable.shenma));
        faceDisplayList.add(Integer.valueOf(R.drawable.shuai));
        faceDisplayList.add(Integer.valueOf(R.drawable.shuijiao));
        faceDisplayList.add(Integer.valueOf(R.drawable.taiyang));
        faceDisplayList.add(Integer.valueOf(R.drawable.tiao));
        faceDisplayList.add(Integer.valueOf(R.drawable.tiaopi));
        faceDisplayList.add(Integer.valueOf(R.drawable.tiaosheng));
        faceDisplayList.add(Integer.valueOf(R.drawable.tiaowu));
        faceDisplayList.add(Integer.valueOf(R.drawable.touxiao));
        faceDisplayList.add(Integer.valueOf(R.drawable.tu));
        faceDisplayList.add(Integer.valueOf(R.drawable.tuzi));
        faceDisplayList.add(Integer.valueOf(R.drawable.wabi));
        faceDisplayList.add(Integer.valueOf(R.drawable.weiqu));
        faceDisplayList.add(Integer.valueOf(R.drawable.weixiao));
        faceDisplayList.add(Integer.valueOf(R.drawable.wen));
        faceDisplayList.add(Integer.valueOf(R.drawable.woshou));
        faceDisplayList.add(Integer.valueOf(R.drawable.xia));
        faceDisplayList.add(Integer.valueOf(R.drawable.xianwen));
        faceDisplayList.add(Integer.valueOf(R.drawable.xigua));
        faceDisplayList.add(Integer.valueOf(R.drawable.xinsui));
        faceDisplayList.add(Integer.valueOf(R.drawable.xu));
        faceDisplayList.add(Integer.valueOf(R.drawable.yinxian));
        faceDisplayList.add(Integer.valueOf(R.drawable.yongbao));
        faceDisplayList.add(Integer.valueOf(R.drawable.youhengheng));
        faceDisplayList.add(Integer.valueOf(R.drawable.youtaiji));
        faceDisplayList.add(Integer.valueOf(R.drawable.yueliang));
        faceDisplayList.add(Integer.valueOf(R.drawable.yun));
        faceDisplayList.add(Integer.valueOf(R.drawable.zaijian));
        faceDisplayList.add(Integer.valueOf(R.drawable.zhadan));
        faceDisplayList.add(Integer.valueOf(R.drawable.zhemo));
        faceDisplayList.add(Integer.valueOf(R.drawable.zhuakuang));
        faceDisplayList.add(Integer.valueOf(R.drawable.zhuanquan));
        faceDisplayList.add(Integer.valueOf(R.drawable.zhutou));
        faceDisplayList.add(Integer.valueOf(R.drawable.zuohengheng));
        faceDisplayList.add(Integer.valueOf(R.drawable.zuotaiji));
        faceDisplayList.add(Integer.valueOf(R.drawable.zuqiu));

        facesKeySrc.put(Integer.valueOf(R.drawable.aoman), "aoman");
        facesKeySrc.put(Integer.valueOf(R.drawable.baiyan), "baiyan");
        facesKeySrc.put(Integer.valueOf(R.drawable.bishi), "bishi");
        facesKeySrc.put(Integer.valueOf(R.drawable.bizui), "bizui");
        facesKeySrc.put(Integer.valueOf(R.drawable.cahan), "cahan");
        facesKeySrc.put(Integer.valueOf(R.drawable.caidao), "caidao");
        facesKeySrc.put(Integer.valueOf(R.drawable.chajin), "chajin");
        facesKeySrc.put(Integer.valueOf(R.drawable.cheer), "cheer");
        facesKeySrc.put(Integer.valueOf(R.drawable.chong), "chong");
        facesKeySrc.put(Integer.valueOf(R.drawable.ciya), "ciya");
        facesKeySrc.put(Integer.valueOf(R.drawable.da), "da");
        facesKeySrc.put(Integer.valueOf(R.drawable.dabian), "dabian");
        facesKeySrc.put(Integer.valueOf(R.drawable.dabing), "dabing");
        facesKeySrc.put(Integer.valueOf(R.drawable.dajiao), "dajiao");
        facesKeySrc.put(Integer.valueOf(R.drawable.daku), "daku");
        facesKeySrc.put(Integer.valueOf(R.drawable.dangao), "dangao");
        facesKeySrc.put(Integer.valueOf(R.drawable.danu), "danu");
        facesKeySrc.put(Integer.valueOf(R.drawable.dao), "dao");
        facesKeySrc.put(Integer.valueOf(R.drawable.deyi), "deyi");
        facesKeySrc.put(Integer.valueOf(R.drawable.diaoxie), "diaoxie");
        facesKeySrc.put(Integer.valueOf(R.drawable.e), "e");
        facesKeySrc.put(Integer.valueOf(R.drawable.fadai), "fadai");
        facesKeySrc.put(Integer.valueOf(R.drawable.fadou), "fadou");
        facesKeySrc.put(Integer.valueOf(R.drawable.fan), "fan");
        facesKeySrc.put(Integer.valueOf(R.drawable.fanu), "fanu");
        facesKeySrc.put(Integer.valueOf(R.drawable.feiwen), "feiwen");
        facesKeySrc.put(Integer.valueOf(R.drawable.fendou), "fendou");
        facesKeySrc.put(Integer.valueOf(R.drawable.gangga), "gangga");
        facesKeySrc.put(Integer.valueOf(R.drawable.geili), "geili");
        facesKeySrc.put(Integer.valueOf(R.drawable.gouyin), "gouyin");
        facesKeySrc.put(Integer.valueOf(R.drawable.guzhang), "guzhang");
        facesKeySrc.put(Integer.valueOf(R.drawable.haha), "haha");
        facesKeySrc.put(Integer.valueOf(R.drawable.haixiu), "haixiu");
        facesKeySrc.put(Integer.valueOf(R.drawable.haqian), "haqian");
        facesKeySrc.put(Integer.valueOf(R.drawable.hua), "hua");
        facesKeySrc.put(Integer.valueOf(R.drawable.huaixiao), "huaixiao");
        facesKeySrc.put(Integer.valueOf(R.drawable.hufen), "hufen");
        facesKeySrc.put(Integer.valueOf(R.drawable.huishou), "huishou");
        facesKeySrc.put(Integer.valueOf(R.drawable.huitou), "huitou");
        facesKeySrc.put(Integer.valueOf(R.drawable.jidong), "jidong");
        facesKeySrc.put(Integer.valueOf(R.drawable.jingkong), "jingkong");
        facesKeySrc.put(Integer.valueOf(R.drawable.jingya), "jingya");
        facesKeySrc.put(Integer.valueOf(R.drawable.kafei), "kafei");
        facesKeySrc.put(Integer.valueOf(R.drawable.keai), "keai");
        facesKeySrc.put(Integer.valueOf(R.drawable.kelian), "kelian");
        facesKeySrc.put(Integer.valueOf(R.drawable.ketou), "ketou");
        facesKeySrc.put(Integer.valueOf(R.drawable.kiss), "kiss");
        facesKeySrc.put(Integer.valueOf(R.drawable.ku), "ku");
        facesKeySrc.put(Integer.valueOf(R.drawable.kuaikule), "kuaikule");
        facesKeySrc.put(Integer.valueOf(R.drawable.kulou), "kulou");
        facesKeySrc.put(Integer.valueOf(R.drawable.kun), "kun");
        facesKeySrc.put(Integer.valueOf(R.drawable.lanqiu), "lanqiu");
        facesKeySrc.put(Integer.valueOf(R.drawable.lenghan), "lenghan");
        facesKeySrc.put(Integer.valueOf(R.drawable.liuhan), "liuhan");
        facesKeySrc.put(Integer.valueOf(R.drawable.liulei), "liulei");
        facesKeySrc.put(Integer.valueOf(R.drawable.liwu), "liwu");
        facesKeySrc.put(Integer.valueOf(R.drawable.love), "love");
        facesKeySrc.put(Integer.valueOf(R.drawable.ma), "ma");
        facesKeySrc.put(Integer.valueOf(R.drawable.meng), "meng");
        facesKeySrc.put(Integer.valueOf(R.drawable.nanguo), "nanguo");
        facesKeySrc.put(Integer.valueOf(R.drawable.no), "no");
        facesKeySrc.put(Integer.valueOf(R.drawable.ok), "ok");
        facesKeySrc.put(Integer.valueOf(R.drawable.peifu), "peifu");
        facesKeySrc.put(Integer.valueOf(R.drawable.pijiu), "pijiu");
        facesKeySrc.put(Integer.valueOf(R.drawable.pingpang), "pingpang");
        facesKeySrc.put(Integer.valueOf(R.drawable.pizui), "pizui");
        facesKeySrc.put(Integer.valueOf(R.drawable.qiang), "qiang");
        facesKeySrc.put(Integer.valueOf(R.drawable.qinqin), "qinqin");
        facesKeySrc.put(Integer.valueOf(R.drawable.qioudale), "qioudale");
        facesKeySrc.put(Integer.valueOf(R.drawable.qiu), "qiu");
        facesKeySrc.put(Integer.valueOf(R.drawable.quantou), "quantou");
        facesKeySrc.put(Integer.valueOf(R.drawable.ruo), "ruo");
        facesKeySrc.put(Integer.valueOf(R.drawable.se), "se");
        facesKeySrc.put(Integer.valueOf(R.drawable.shandian), "shandian");
        facesKeySrc.put(Integer.valueOf(R.drawable.shengli), "shengli");
        facesKeySrc.put(Integer.valueOf(R.drawable.shenma), "shenma");
        facesKeySrc.put(Integer.valueOf(R.drawable.shuai), "shuai");
        facesKeySrc.put(Integer.valueOf(R.drawable.shuijiao), "shuijiao");
        facesKeySrc.put(Integer.valueOf(R.drawable.taiyang), "taiyang");
        facesKeySrc.put(Integer.valueOf(R.drawable.tiao), "tiao");
        facesKeySrc.put(Integer.valueOf(R.drawable.tiaopi), "tiaopi");
        facesKeySrc.put(Integer.valueOf(R.drawable.tiaosheng), "tiaosheng");
        facesKeySrc.put(Integer.valueOf(R.drawable.tiaowu), "tiaowu");
        facesKeySrc.put(Integer.valueOf(R.drawable.touxiao), "touxiao");
        facesKeySrc.put(Integer.valueOf(R.drawable.tu), "tu");
        facesKeySrc.put(Integer.valueOf(R.drawable.tuzi), "tuzi");
        facesKeySrc.put(Integer.valueOf(R.drawable.wabi), "wabi");
        facesKeySrc.put(Integer.valueOf(R.drawable.weiqu), "weiqu");
        facesKeySrc.put(Integer.valueOf(R.drawable.weixiao), "weixiao");
        facesKeySrc.put(Integer.valueOf(R.drawable.wen), "wen");
        facesKeySrc.put(Integer.valueOf(R.drawable.woshou), "woshou");
        facesKeySrc.put(Integer.valueOf(R.drawable.xia), "xia");
        facesKeySrc.put(Integer.valueOf(R.drawable.xianwen), "xianwen");
        facesKeySrc.put(Integer.valueOf(R.drawable.xigua), "xigua");
        facesKeySrc.put(Integer.valueOf(R.drawable.xinsui), "xinsui");
        facesKeySrc.put(Integer.valueOf(R.drawable.xu), "xu");
        facesKeySrc.put(Integer.valueOf(R.drawable.yinxian), "yinxian");
        facesKeySrc.put(Integer.valueOf(R.drawable.yongbao), "yongbao");
        facesKeySrc.put(Integer.valueOf(R.drawable.youhengheng), "youhengheng");
        facesKeySrc.put(Integer.valueOf(R.drawable.youtaiji), "youtaiji");
        facesKeySrc.put(Integer.valueOf(R.drawable.yueliang), "yueliang");
        facesKeySrc.put(Integer.valueOf(R.drawable.yun), "yun");
        facesKeySrc.put(Integer.valueOf(R.drawable.zaijian), "zaijian");
        facesKeySrc.put(Integer.valueOf(R.drawable.zhadan), "zhadan");
        facesKeySrc.put(Integer.valueOf(R.drawable.zhemo), "zhemo");
        facesKeySrc.put(Integer.valueOf(R.drawable.zhuakuang), "zhuakuang");
        facesKeySrc.put(Integer.valueOf(R.drawable.zhuanquan), "zhuanquan");
        facesKeySrc.put(Integer.valueOf(R.drawable.zhutou), "zhutou");
        facesKeySrc.put(Integer.valueOf(R.drawable.zuohengheng), "zuohengheng");
        facesKeySrc.put(Integer.valueOf(R.drawable.zuotaiji), "zuotaiji");
        facesKeySrc.put(Integer.valueOf(R.drawable.zuqiu), "zuqiu");

        facesKeyString.put("aoman", Integer.valueOf(R.drawable.aoman));
        facesKeyString.put("baiyan", Integer.valueOf(R.drawable.baiyan));
        facesKeyString.put("bishi", Integer.valueOf(R.drawable.bishi));
        facesKeyString.put("bizui", Integer.valueOf(R.drawable.bizui));
        facesKeyString.put("cahan", Integer.valueOf(R.drawable.cahan));
        facesKeyString.put("caidao", Integer.valueOf(R.drawable.caidao));
        facesKeyString.put("chajin", Integer.valueOf(R.drawable.chajin));
        facesKeyString.put("cheer", Integer.valueOf(R.drawable.cheer));
        facesKeyString.put("chong", Integer.valueOf(R.drawable.chong));
        facesKeyString.put("ciya", Integer.valueOf(R.drawable.ciya));
        facesKeyString.put("da", Integer.valueOf(R.drawable.da));
        facesKeyString.put("dabian", Integer.valueOf(R.drawable.dabian));
        facesKeyString.put("dabing", Integer.valueOf(R.drawable.dabing));
        facesKeyString.put("dajiao", Integer.valueOf(R.drawable.dajiao));
        facesKeyString.put("daku", Integer.valueOf(R.drawable.daku));
        facesKeyString.put("dangao", Integer.valueOf(R.drawable.dangao));
        facesKeyString.put("danu", Integer.valueOf(R.drawable.danu));
        facesKeyString.put("dao", Integer.valueOf(R.drawable.dao));
        facesKeyString.put("deyi", Integer.valueOf(R.drawable.deyi));
        facesKeyString.put("diaoxie", Integer.valueOf(R.drawable.diaoxie));
        facesKeyString.put("e", Integer.valueOf(R.drawable.e));
        facesKeyString.put("fadai", Integer.valueOf(R.drawable.fadai));
        facesKeyString.put("fadou", Integer.valueOf(R.drawable.fadou));
        facesKeyString.put("fan", Integer.valueOf(R.drawable.fan));
        facesKeyString.put("fanu", Integer.valueOf(R.drawable.fanu));
        facesKeyString.put("feiwen", Integer.valueOf(R.drawable.feiwen));
        facesKeyString.put("fendou", Integer.valueOf(R.drawable.fendou));
        facesKeyString.put("gangga", Integer.valueOf(R.drawable.gangga));
        facesKeyString.put("geili", Integer.valueOf(R.drawable.geili));
        facesKeyString.put("gouyin", Integer.valueOf(R.drawable.gouyin));
        facesKeyString.put("guzhang", Integer.valueOf(R.drawable.guzhang));
        facesKeyString.put("haha", Integer.valueOf(R.drawable.haha));
        facesKeyString.put("haixiu", Integer.valueOf(R.drawable.haixiu));
        facesKeyString.put("haqian", Integer.valueOf(R.drawable.haqian));
        facesKeyString.put("hua", Integer.valueOf(R.drawable.hua));
        facesKeyString.put("huaixiao", Integer.valueOf(R.drawable.huaixiao));
        facesKeyString.put("hufen", Integer.valueOf(R.drawable.hufen));
        facesKeyString.put("huishou", Integer.valueOf(R.drawable.huishou));
        facesKeyString.put("huitou", Integer.valueOf(R.drawable.huitou));
        facesKeyString.put("jidong", Integer.valueOf(R.drawable.jidong));
        facesKeyString.put("jingkong", Integer.valueOf(R.drawable.jingkong));
        facesKeyString.put("jingya", Integer.valueOf(R.drawable.jingya));
        facesKeyString.put("kafei", Integer.valueOf(R.drawable.kafei));
        facesKeyString.put("keai", Integer.valueOf(R.drawable.keai));
        facesKeyString.put("kelian", Integer.valueOf(R.drawable.kelian));
        facesKeyString.put("ketou", Integer.valueOf(R.drawable.ketou));
        facesKeyString.put("kiss", Integer.valueOf(R.drawable.kiss));
        facesKeyString.put("ku", Integer.valueOf(R.drawable.ku));
        facesKeyString.put("kuaikule", Integer.valueOf(R.drawable.kuaikule));
        facesKeyString.put("kulou", Integer.valueOf(R.drawable.kulou));
        facesKeyString.put("kun", Integer.valueOf(R.drawable.kun));
        facesKeyString.put("lanqiu", Integer.valueOf(R.drawable.lanqiu));
        facesKeyString.put("lenghan", Integer.valueOf(R.drawable.lenghan));
        facesKeyString.put("liuhan", Integer.valueOf(R.drawable.liuhan));
        facesKeyString.put("liulei", Integer.valueOf(R.drawable.liulei));
        facesKeyString.put("liwu", Integer.valueOf(R.drawable.liwu));
        facesKeyString.put("love", Integer.valueOf(R.drawable.love));
        facesKeyString.put("ma", Integer.valueOf(R.drawable.ma));
        facesKeyString.put("meng", Integer.valueOf(R.drawable.meng));
        facesKeyString.put("nanguo", Integer.valueOf(R.drawable.nanguo));
        facesKeyString.put("no", Integer.valueOf(R.drawable.no));
        facesKeyString.put("ok", Integer.valueOf(R.drawable.ok));
        facesKeyString.put("peifu", Integer.valueOf(R.drawable.peifu));
        facesKeyString.put("pijiu", Integer.valueOf(R.drawable.pijiu));
        facesKeyString.put("pingpang", Integer.valueOf(R.drawable.pingpang));
        facesKeyString.put("pizui", Integer.valueOf(R.drawable.pizui));
        facesKeyString.put("qiang", Integer.valueOf(R.drawable.qiang));
        facesKeyString.put("qinqin", Integer.valueOf(R.drawable.qinqin));
        facesKeyString.put("qioudale", Integer.valueOf(R.drawable.qioudale));
        facesKeyString.put("qiu", Integer.valueOf(R.drawable.qiu));
        facesKeyString.put("quantou", Integer.valueOf(R.drawable.quantou));
        facesKeyString.put("ruo", Integer.valueOf(R.drawable.ruo));
        facesKeyString.put("se", Integer.valueOf(R.drawable.se));
        facesKeyString.put("shandian", Integer.valueOf(R.drawable.shandian));
        facesKeyString.put("shengli", Integer.valueOf(R.drawable.shengli));
        facesKeyString.put("shenma", Integer.valueOf(R.drawable.shenma));
        facesKeyString.put("shuai", Integer.valueOf(R.drawable.shuai));
        facesKeyString.put("shuijiao", Integer.valueOf(R.drawable.shuijiao));
        facesKeyString.put("taiyang", Integer.valueOf(R.drawable.taiyang));
        facesKeyString.put("tiao", Integer.valueOf(R.drawable.tiao));
        facesKeyString.put("tiaopi", Integer.valueOf(R.drawable.tiaopi));
        facesKeyString.put("tiaosheng", Integer.valueOf(R.drawable.tiaosheng));
        facesKeyString.put("tiaowu", Integer.valueOf(R.drawable.tiaowu));
        facesKeyString.put("touxiao", Integer.valueOf(R.drawable.touxiao));
        facesKeyString.put("tu", Integer.valueOf(R.drawable.tu));
        facesKeyString.put("tuzi", Integer.valueOf(R.drawable.tuzi));
        facesKeyString.put("wabi", Integer.valueOf(R.drawable.wabi));
        facesKeyString.put("weiqu", Integer.valueOf(R.drawable.weiqu));
        facesKeyString.put("weixiao", Integer.valueOf(R.drawable.weixiao));
        facesKeyString.put("wen", Integer.valueOf(R.drawable.wen));
        facesKeyString.put("woshou", Integer.valueOf(R.drawable.woshou));
        facesKeyString.put("xia", Integer.valueOf(R.drawable.xia));
        facesKeyString.put("xianwen", Integer.valueOf(R.drawable.xianwen));
        facesKeyString.put("xigua", Integer.valueOf(R.drawable.xigua));
        facesKeyString.put("xinsui", Integer.valueOf(R.drawable.xinsui));
        facesKeyString.put("xu", Integer.valueOf(R.drawable.xu));
        facesKeyString.put("yinxian", Integer.valueOf(R.drawable.yinxian));
        facesKeyString.put("yongbao", Integer.valueOf(R.drawable.yongbao));
        facesKeyString.put("youhengheng",
                Integer.valueOf(R.drawable.youhengheng));
        facesKeyString.put("youtaiji", Integer.valueOf(R.drawable.youtaiji));
        facesKeyString.put("yueliang", Integer.valueOf(R.drawable.yueliang));
        facesKeyString.put("yun", Integer.valueOf(R.drawable.yun));
        facesKeyString.put("zaijian", Integer.valueOf(R.drawable.zaijian));
        facesKeyString.put("zhadan", Integer.valueOf(R.drawable.zhadan));
        facesKeyString.put("zhemo", Integer.valueOf(R.drawable.zhemo));
        facesKeyString.put("zhuakuang", Integer.valueOf(R.drawable.zhuakuang));
        facesKeyString.put("zhuanquan", Integer.valueOf(R.drawable.zhuanquan));
        facesKeyString.put("zhutou", Integer.valueOf(R.drawable.zhutou));
        facesKeyString.put("zuohengheng",
                Integer.valueOf(R.drawable.zuohengheng));
        facesKeyString.put("zuotaiji", Integer.valueOf(R.drawable.zuotaiji));
        facesKeyString.put("zuqiu", Integer.valueOf(R.drawable.zuqiu));

    }

    public TSFaceView(Context paramContext) {
        super(paramContext);
        this.m_Context = paramContext;
        initViews();
    }

    public TSFaceView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.m_Context = paramContext;
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.face_main, this);
        this.m_GridView = ((GridView) findViewById(R.id.gridView));
        GridViewAdapter localGridViewAdapter = new GridViewAdapter(
                this.m_Context, faceDisplayList);
        this.m_GridView.setAdapter(localGridViewAdapter);
        this.m_GridView.setOnItemClickListener(this);
    }

    public FaceAdapter getFaceAdapter() {
        return this.m_faceAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
                            int paramInt, long paramLong) {
        if (this.m_faceAdapter != null) {
            int i = faceDisplayList.get(paramInt).intValue();
            String str = facesKeySrc.get(Integer.valueOf(i));
            this.m_faceAdapter.doAction(i, str);
        }
    }

    public void reBuildViews() {
        removeAllViews();
        initViews();
        requestLayout();
        invalidate();
    }

    public void setFaceAdapter(FaceAdapter paramFaceAdapter) {
        this.m_faceAdapter = paramFaceAdapter;
    }

    public static abstract interface FaceAdapter {
        public abstract void doAction(int paramInt, String paramString);
    }

    class GridViewAdapter extends BaseAdapter {
        Context ct;
        List<Integer> list;

        public GridViewAdapter(Context mContext, ArrayList<Integer> arg2) {
            this.ct = mContext;
            this.list = arg2;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return this.list.get(position);
        }

        @Override
        public long getItemId(int paramInt) {
            return paramInt;
        }

        @Override
        public View getView(int paramInt, View paramView,
                            ViewGroup paramViewGroup) {
            ImageView localImageView = null;
            if (paramView == null) {
                localImageView = new ImageView(this.ct);
                localImageView.setBackgroundResource(R.drawable.bg_face);
                int j = TSFaceView.this.getResources().getDimensionPixelSize(
                        R.dimen.face_item_view_height);
                localImageView.setPadding(0, j, 0, j);
                paramView = localImageView;
                paramView.setTag(localImageView);
            } else {
                localImageView = (ImageView) paramView.getTag();
            }

            int i = ((Integer) getItem(paramInt)).intValue();

            localImageView.setImageResource(i);
            return paramView;
        }
    }
}