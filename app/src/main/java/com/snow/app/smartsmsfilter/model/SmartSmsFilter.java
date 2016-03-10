package com.snow.app.smartsmsfilter.model;

/**
 * Created by Administrator on 2016.03.08.
 */
public class SmartSmsFilter {
    private int id;
    private String smsFrom;
    private boolean filterState;
    private int deleteTimes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmsFrom() {
        return smsFrom;
    }

    public void setSmsFrom(String smsFrom) {
        this.smsFrom = smsFrom;
    }

    public boolean isFilterState() {
        return filterState;
    }

    public void setFilterState(boolean filterState) {
        this.filterState = filterState;
    }

    public int getDeleteTimes() {
        return deleteTimes;
    }

    public void setDeleteTimes(int deleteTimes) {
        this.deleteTimes = deleteTimes;
    }

}
