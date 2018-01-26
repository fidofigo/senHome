package com.senhome.web.interceptor.request;

import com.senhome.shell.common.enums.OS;

/**
 * 用户终端，如APP、浏览器
 * Created by luliru on 2017/2/23.
 */
public class Terminal {

    private OS os;

    private String imei;

    public Terminal(OS os,String imei){
        this.os = os;
        this.imei = imei;
    }

    public OS getOs() {
        return os;
    }

    public String getImei() {
        return imei;
    }

    /**
     * 是否为App设备
     * @return
     */
    public boolean isApp(){
        return true;
    }
}
