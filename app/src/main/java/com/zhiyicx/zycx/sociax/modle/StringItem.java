package com.zhiyicx.zycx.sociax.modle;

/**
 * 类说明：
 *
 * @author Povol
 * @version 1.0
 * @date 2013-2-3
 */
public class StringItem extends SociaxItem {

    private int id;
    private String name;

    private ListData<SociaxItem> listData;

    public StringItem() {
    }

    public StringItem(int id, String name) {
        setId(id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListData<SociaxItem> getListData() {
        return listData;
    }

    public void setListData(ListData<SociaxItem> listData) {
        this.listData = listData;
    }

    @Override
    public boolean checkValid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getUserface() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int compareTo(SociaxItem another) {
        return ((Integer) id).compareTo(((StringItem) another).getId());
    }

    @Override
    public String toString() {
        return "StringItem [id=" + id + ", name=" + name + ", listData="
                + listData + "]";
    }

}
