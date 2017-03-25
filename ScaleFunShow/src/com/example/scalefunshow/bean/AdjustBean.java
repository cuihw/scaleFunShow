package com.example.scalefunshow.bean;

/**
 * Created by cuihuawei on 3/24/2017.
 */

public class AdjustBean {
    /* + KEY_OPERATOR + " TEXT,"
        + KEY_TIME + " TEXT,"
        + KEY_FAMA_WEIGHT + " TEXT,"
        + KEY_WEIGHT + " TEXT,"
        + KEY_POINT + " TEXT,"
        + KEY_IS_RIGHT + " TEXT,"*/
    int id;
    String operator;
    String time;
    String famaWeight;
    String Weight;
    String point;
    String isRight;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFamaWeight() {
        return famaWeight;
    }

    public void setFamaWeight(String famaWeight) {
        this.famaWeight = famaWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
