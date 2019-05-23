package com.yxcorp.plugin.kwaitoken.model;

import java.io.Serializable;

public class ShareTokenInfo implements Serializable {
    private static final long serialVersionUID = 8924043246489548949L;
    public Serializable mExtras;
    public int mPlatform;
    public int mResult;
    public ShareTokenDialog mTokenDialog;
    public String mUri;

    public Serializable getExtras() {
        return this.mExtras;
    }
}
