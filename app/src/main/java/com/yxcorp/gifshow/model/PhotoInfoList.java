package com.yxcorp.gifshow.model;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.List;


public class PhotoInfoList implements Serializable{
    private static final long serialVersionUID = -1693148470225445116L;
    public List<PhotoInfoQuery> mList;

    public PhotoInfoList(List<PhotoInfoQuery> list) {
        this.mList = list;
    }

    public PhotoInfoList(PhotoInfoQuery... photoInfoQueryArr) {
        this.mList = ImmutableList.copyOf((PhotoInfoQuery[]) photoInfoQueryArr);
    }


}
