package com.gomeals.constants;

public enum DeliveryStatus {
    IN_PROGRESS("inprogress"),
    CANCELLED("cancelled"),
    COMPLETED("completed");

    private final String statusName;

    DeliveryStatus(String statusName){
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

}
