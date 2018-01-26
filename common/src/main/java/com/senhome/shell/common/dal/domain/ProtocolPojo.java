package com.senhome.shell.common.dal.domain;

import lombok.Data;

@Data
public class ProtocolPojo
{
    /**
     * 操作系统, 1:ios 2:android
     */
    private String os;

    /**
     * 版本
     */
    private Float version;

    /**
     * 签名
     */
    private String sign;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * ip
     */
    private String ip;

    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 设备标示
     */
    private String imei;
}
