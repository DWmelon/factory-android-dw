package com.yongheng.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/4/9.
 */

public class DataModel implements Serializable{

    private static final long serialVersionUID = 3989249758833216226L;

    private List<BusinessModel> businessModelList = new ArrayList<>();

    public List<BusinessModel> getBusinessModelList() {
        return businessModelList;
    }

    public void setBusinessModelList(List<BusinessModel> businessModelList) {
        this.businessModelList = businessModelList;
    }
}
