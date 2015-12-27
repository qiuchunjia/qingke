package qcjlibrary.model;

import qcjlibrary.model.base.Model;

/**
 * author：qiuchunjia time：下午4:53:14
 * <p/>
 * 类描述：这个类是实现 添加病例的bean
 */

public class ModelAddCase extends Model {
    private static final long serialVersionUID = 1L;
    /**
     * realname 姓名
     * <p/>
     * sex 性别 0-男 1-女
     * <p/>
     * age 年龄
     * <p/>
     * marriage 婚姻状况 0-未婚 1-已婚
     * <p/>
     * nation 民族
     * <p/>
     * profession 职业
     * <p/>
     * education 文化程度
     * <p/>
     * insform 保险形式
     * <p/>
     * native 籍贯
     * <p/>
     * domicile 居住地
     * <p/>
     * height 身高
     * <p/>
     * weight 体重
     */
    private String realname;
    private String sex;
    private String age;
    private String marriage;
    private String nation;
    private String profession;
    private String education;
    private String insform;
    private String natives;
    private String domicile;
    private String height;
    private String weight;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getInsform() {
        return insform;
    }

    public void setInsform(String insform) {
        this.insform = insform;
    }

    public String getNatives() {
        return natives;
    }

    public void setNatives(String natives) {
        this.natives = natives;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
