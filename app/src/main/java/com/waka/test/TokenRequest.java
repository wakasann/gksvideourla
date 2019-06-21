package com.waka.test;

public class TokenRequest {
    private TokenRequestBean tokenRequestBean;

    public TokenRequestBean getTokenRequestBean() {
        return tokenRequestBean;
    }

    public void setTokenRequestBean(TokenRequestBean tokenRequestBean){
        this.tokenRequestBean = tokenRequestBean;
    }

    public class TokenRequestBean {
        private String sig;

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }
    }
}


