package com.gomeals.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="meal_chart")
public class MealChart {

    @EmbeddedId
    private MealChartID id;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    @Column(name = "special_date")
    private Date specialDate;

//    @ManyToOne
//    @JoinColumn(name = "supId")
//    private supplier supplier;

    public MealChart(String item1, String item2, String item3, String item4, String item5, Date special_date, MealChartID id) {

        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.specialDate = special_date;
        this.id = id;
    }

    public MealChart() {

    }


    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public Date getSpecialDate() {
        return specialDate;
    }

    public void setSpecialDate(Date special_date) {
        this.specialDate = special_date;
    }

    public MealChartID getId() {
        return id;
    }

    public void setId(MealChartID id) {
        this.id = id;
    }
}


