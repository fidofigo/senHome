package com.senhome.web.interceptor.request;

/**
 * Created by luliru on 2017/2/23.
 */
public class AccountLogin
{

    private boolean registered;

    private int id;

    public AccountLogin(int id,boolean registered){
        this.id = id;
        this.registered = registered;
    }

    /**
     * 是否注册用户
     * @return
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * 是否临时用户
     * @return
     */
    public boolean isTemp(){
        return !registered;
    }

    /**
     * 获取用户id
     * @return
     */
    public int getId() {
        return id;
    }
}
