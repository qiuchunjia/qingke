package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 *
 *
 */

public class ModelUser extends Model {

    /**
     * token 必须
     * <p/>
     * sex 1-男 2-女
     * <p/>
     * intro 简介
     * <p/>
     * cancer 癌种id
     * <p/>
     * birthday 生日
     * <p/>
     * location 地区
     * <p/>
     * city_ids 省市区id 依次以逗号隔开
     * <p/>
     * uname 昵称
     */
    private static final long serialVersionUID = 1L;
    private String oauth_token;
    private String oauth_token_secret;
    private String sex;
    private String intro;
    private String cancer;
    private String birthday;
    private String location;
    private String city_ids;
    private String uname;
    private String avatar;

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getOauth_token_secret() {
        return oauth_token_secret;
    }

    public void setOauth_token_secret(String oauth_token_secret) {
        this.oauth_token_secret = oauth_token_secret;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity_ids() {
        return city_ids;
    }

    public void setCity_ids(String city_ids) {
        this.city_ids = city_ids;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
