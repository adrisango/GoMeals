package com.gomeals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MealChartID implements Serializable {
    @Column(name="day")
 private String day;
    @Column(name="sup_id")
 private int supId;

    public MealChartID(String day, int supId) {
        this.day = day;
        this.supId = supId;
    }

    public MealChartID() {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }
}
