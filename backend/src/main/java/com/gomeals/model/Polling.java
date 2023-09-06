package com.gomeals.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "polling")
public class Polling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id")
    private int pollId;
    @Column(name = "poll_date")
    private Date pollDate;
    private String vote;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    @Column(name = "sup_id")
    private int supId;
    @Column(columnDefinition = "TINYINT", length = 4)
    private boolean status;

    public Polling() {
    }

    public Polling(int pollId, Date pollDate, String vote, String item1, String item2, String item3, String item4,
            String item5, boolean status, int supId) {
        this.pollId = pollId;
        this.pollDate = pollDate;
        this.vote = vote;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.status = status;
        this.supId = supId;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public Date getPollDate() {
        return pollDate;
    }

    public void setPollDate(Date pollDate) {
        this.pollDate = pollDate;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

}
