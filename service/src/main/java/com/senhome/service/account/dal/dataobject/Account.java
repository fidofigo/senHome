package com.senhome.service.account.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.*;

@Data
public class Account extends BaseDO
{
    private String email;

    private String pwd;
}
