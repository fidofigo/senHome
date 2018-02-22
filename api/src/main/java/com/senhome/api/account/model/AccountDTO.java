package com.senhome.api.account.model;

import lombok.Data;

@Data
public class AccountDTO
{
    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 密钥
     */
    private String secretKey;

    private long inMeters;
}
