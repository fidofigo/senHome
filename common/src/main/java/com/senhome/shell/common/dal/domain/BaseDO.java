package com.senhome.shell.common.dal.domain;

import lombok.*;

import java.util.Date;

/**
 * 基础数据对象
 */
@Data
public abstract class BaseDO {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 是否可用
	 */
	private Byte isAvailable;
}
