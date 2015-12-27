package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午5:58:40 类描述：这个类是实现
 */

public class ModelAddHistoryCase extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // med_history 既往病史
    // allergy_history 过敏史
    // per_history 个人史
    // eating_habit 饮食习惯
    // smoke 抽烟 0不抽 1抽
    // smoke_age 抽烟年龄
    // smoke_time 每日抽烟根数
    // stop_smoke 是否戒烟 0-未戒 1-已戒
    // stop_smoke_time 戒烟时间
    // drink 是否饮酒 0饮酒 1不饮酒
    // drink_age 饮酒年龄
    // drink_consumption 饮酒量
    // stop_drink 是否戒酒 0-未戒 1-已戒
    // stop_drink_time 戒酒时间
    // menarche_age 初潮年纪
    // menarche_etime 末次月经时间
    // amenorrhoea_age 闭经年龄
    // childs 子女
    // family_history 家族史
    private String med_history;
    private String allergy_history;
    private String per_history;
    private String eating_habit;
    private String smoke;
    private String smoke_age;
    private String smoke_time;
    private String stop_smoke;
    private String stop_smoke_time;
    private String drink;
    private String drink_age;
    private String drink_consumption;
    private String stop_drink;
    private String stop_drink_time;
    private String menarche_age;
    private String menarche_etime;
    private String amenorrhoea_age;
    private String childs;
    private String family_history;

    public String getMed_history() {
        return med_history;
    }

    public void setMed_history(String med_history) {
        this.med_history = med_history;
    }

    public String getAllergy_history() {
        return allergy_history;
    }

    public void setAllergy_history(String allergy_history) {
        this.allergy_history = allergy_history;
    }

    public String getPer_history() {
        return per_history;
    }

    public void setPer_history(String per_history) {
        this.per_history = per_history;
    }

    public String getEating_habit() {
        return eating_habit;
    }

    public void setEating_habit(String eating_habit) {
        this.eating_habit = eating_habit;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getSmoke_age() {
        return smoke_age;
    }

    public void setSmoke_age(String smoke_age) {
        this.smoke_age = smoke_age;
    }

    public String getSmoke_time() {
        return smoke_time;
    }

    public void setSmoke_time(String smoke_time) {
        this.smoke_time = smoke_time;
    }

    public String getStop_smoke() {
        return stop_smoke;
    }

    public void setStop_smoke(String stop_smoke) {
        this.stop_smoke = stop_smoke;
    }

    public String getStop_smoke_time() {
        return stop_smoke_time;
    }

    public void setStop_smoke_time(String stop_smoke_time) {
        this.stop_smoke_time = stop_smoke_time;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getDrink_age() {
        return drink_age;
    }

    public void setDrink_age(String drink_age) {
        this.drink_age = drink_age;
    }

    public String getDrink_consumption() {
        return drink_consumption;
    }

    public void setDrink_consumption(String drink_consumption) {
        this.drink_consumption = drink_consumption;
    }

    public String getStop_drink() {
        return stop_drink;
    }

    public void setStop_drink(String stop_drink) {
        this.stop_drink = stop_drink;
    }

    public String getStop_drink_time() {
        return stop_drink_time;
    }

    public void setStop_drink_time(String stop_drink_time) {
        this.stop_drink_time = stop_drink_time;
    }

    public String getMenarche_age() {
        return menarche_age;
    }

    public void setMenarche_age(String menarche_age) {
        this.menarche_age = menarche_age;
    }

    public String getMenarche_etime() {
        return menarche_etime;
    }

    public void setMenarche_etime(String menarche_etime) {
        this.menarche_etime = menarche_etime;
    }

    public String getAmenorrhoea_age() {
        return amenorrhoea_age;
    }

    public void setAmenorrhoea_age(String amenorrhoea_age) {
        this.amenorrhoea_age = amenorrhoea_age;
    }

    public String getChilds() {
        return childs;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public String getFamily_history() {
        return family_history;
    }

    public void setFamily_history(String family_history) {
        this.family_history = family_history;
    }

    @Override
    public String toString() {
        return "ModelAddHistoryCase [med_history=" + med_history
                + ", allergy_history=" + allergy_history + ", per_history="
                + per_history + ", eating_habit=" + eating_habit + ", smoke="
                + smoke + ", smoke_age=" + smoke_age + ", smoke_time="
                + smoke_time + ", stop_smoke=" + stop_smoke
                + ", stop_smoke_time=" + stop_smoke_time + ", drink=" + drink
                + ", drink_age=" + drink_age + ", drink_consumption="
                + drink_consumption + ", stop_drink=" + stop_drink
                + ", stop_drink_time=" + stop_drink_time + ", menarche_age="
                + menarche_age + ", menarche_etime=" + menarche_etime
                + ", amenorrhoea_age=" + amenorrhoea_age + ", childs=" + childs
                + ", family_history=" + family_history + "]";
    }

}
