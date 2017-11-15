CREATE TABLE banner (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `image` varchar(100) NOT NULL DEFAULT '' COMMENT '图片地址',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '访问URL',
  `sequence` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_display` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否展示；0：否，1：是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner';

CREATE TABLE account (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱地址',
  `pwd` varchar(16) default '' not null comment '账号密码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号表';

CREATE TABLE product_base(
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	`category_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '类目id',
  `image1` varchar(100) NOT NULL DEFAULT '' COMMENT '商品主图',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
  `brand` varchar(64) NOT NULL DEFAULT '' COMMENT '品牌',
  `country` varchar(64) NOT NULL DEFAULT '' COMMENT '产地',
  `is_available` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用；0：否，1：是',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础商品表';

CREATE TABLE product_information(
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	`product_base_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '基础商品id',
  `image1` varchar(100) NOT NULL DEFAULT '' COMMENT '商品主图',
  `image2` varchar(100) NOT NULL DEFAULT '' COMMENT '品牌图片2',
  `image3` varchar(100) NOT NULL DEFAULT '' COMMENT '品牌图片3',
  `image4` varchar(100) NOT NULL DEFAULT '' COMMENT '品牌图片4',
  `image5` varchar(100) NOT NULL DEFAULT '' COMMENT '品牌图片5',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品展示名称',
  `desc` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `market_price` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '供货价，分为单位',
  `sales_price` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '销售价，分为单位',
  `is_available` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用；0：否，1：是',
  `limit` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '加入购物车限制',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';

CREATE TABLE product_detail(
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	`product_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '详情图片',
  `width`   int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '图片宽度',
  `height`  int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '图片高度',
  `sequence` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '排序值，从小到大排序',
  `link` varchar(200) NOT NULL DEFAULT '' COMMENT '跳转URL',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情表';

CREATE TABLE category(
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '类目名称',
  `is_available` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用；0：否，1：是',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类目表';

CREATE TABLE `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账号id',
  `total_price` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总价，分为单位',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '订单状态；1：已提交，2：已处理，3：已完成，4：已取消',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '地址',
  `phone` varchar(50) NOT NULL DEFAULT '' COMMENT '手机号',
  `pay_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '付款时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE `order_product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` int(11) unsigned NOT NULL COMMENT '订单id',
  `product_id` int(11) unsigned NOT NULL COMMENT '货架商品id',
  `sales_price` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '单价，分为单位',
  `count` int(5) unsigned NOT NULL DEFAULT '1' COMMENT '商品数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品表';

CREATE TABLE `group` (
  `id`           int(11) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'id',
  `remark`       varchar(100)        NOT NULL DEFAULT '' COMMENT '备注',
  `is_available` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用；0：否，1：是',
  `create_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '组合表';

CREATE TABLE `relation_group_product` (
  `id`           INT(11) UNSIGNED     NOT NULL AUTO_INCREMENT COMMENT 'id',
  `is_available` TINYINT(3) UNSIGNED  NOT NULL DEFAULT '1' COMMENT '是否可用；0：否，1：是',
  `group_id`     INT(11) UNSIGNED     NOT NULL DEFAULT '0' COMMENT '组合id',
  `produc_id`    INT(11) UNSIGNED     NOT NULL DEFAULT '0' COMMENT '商品id',
  `sequence`     SMALLINT(5) UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序值，从小到大排序',
  `create_time`  TIMESTAMP            NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time`  TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '组合表';

CREATE TABLE `cms` (
  `id`           INT(11) UNSIGNED    NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `name`         VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT 'cms名称',
  `remark`       VARCHAR(100)        NOT NULL DEFAULT ''
  COMMENT 'cms备注',
  `is_available` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1'
  COMMENT '是否可用；0：否，1：是',
  `create_time`  TIMESTAMP           NOT NULL DEFAULT '0000-00-00 00:00:00'
  COMMENT '创建时间',
  `update_time`  TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'cms模版';

/*促销模版组合搭配详情*/
CREATE TABLE `cms_group_detail` (
  `id`                 INT(11) UNSIGNED     NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `cms_detail_id`      INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '促销模版详情id',
  `sequence`           SMALLINT(5) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '排序值，从大到小排序',
  `is_available`       TINYINT(3) UNSIGNED  NOT NULL DEFAULT '1'
  COMMENT '是否可用；0：否，1：是',
  `layout_type`        TINYINT(3) UNSIGNED  NOT NULL DEFAULT '1'
  COMMENT '布局方式，1：一行1张，2：一行2张，3：一行3张，4：一行4张，5：一行5张，6：一行6张',
  `one_remark`         VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第一张备注',
  `one_image_url`      VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第一张图片url',
  `one_image_width`    INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第一张图片宽度',
  `one_image_height`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第一张图片高度',
  `one_type`           TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT '第一张关联类型，1：商品，2：组合，3：cms，4：点击不跳转',
  `one_display_id`     INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第一张关联对象Id',
  `two_remark`         VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第二张备注',
  `two_image_url`      VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第二张图片url',
  `two_image_width`    INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第二张图片宽度',
  `two_image_height`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第二张图片高度',
  `two_type`           TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT '第二张关联类型，1：商品，2：组合，3：cms，4：点击不跳转',
  `two_display_id`     INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第二张关联对象Id',
  `three_remark`       VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第三张备注',
  `three_image_url`    VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第三张图片url',
  `three_image_width`  INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第三张图片宽度',
  `three_image_height` INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第三张图片高度',
  `three_type`         TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT '第三张关联类型，1：商品，2：组合，3：cms，4：点击不跳转',
  `three_display_id`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第三张关联对象Id',
  `four_remark`        VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第四张备注',
  `four_image_url`     VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '第四张图片url',
  `four_image_width`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第四张图片宽度',
  `four_image_height`  INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第四张图片高度',
  `four_type`          TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT '第四张关联类型，1：商品，2：组合，3：cms，4：点击不跳转',
  `four_display_id`    INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '第四张关联对象Id',
  `create_time`        TIMESTAMP            NOT NULL DEFAULT '0000-00-00 00:00:00'
  COMMENT '创建时间',
  `update_time`        TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'cms组合搭配详情';

/*cms详情*/
CREATE TABLE `cms_detail` (
  `id`               INT(11) UNSIGNED     NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `cms_id`           INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'cmsId',
  `remark`           VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT '备注',
  `is_available`     TINYINT(3) UNSIGNED  NOT NULL DEFAULT '1'
  COMMENT '是否可用；0：否，1：是',
  `sequence`         SMALLINT(5) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '排序值，从大到小排序',
  `type`             TINYINT(3) UNSIGNED  NOT NULL DEFAULT '1'
  COMMENT '模版类型，1：组合搭配模块；2：商品展示区域(1行3张)；3：导航；4：微信号；5：滑动banner；6：滑动商品 7：商品展示区域(1行2张)',
  `navigation_name`  VARCHAR(20)          NOT NULL DEFAULT ''
  COMMENT '导航栏名称',
  `in_navigation`    TINYINT(3) UNSIGNED  NOT NULL DEFAULT '0'
  COMMENT '是否出现在导航栏中；0：否，1：是',
  `one_image`        VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT 'type=2，7标题1图片,type=4微信号图片',
  `two_image`        VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT 'type=2，7标题2图片',
  `one_image_height` INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'type=2，7标题1图片高度,type=4微信号图片高度',
  `two_image_height` INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'type=2，7标题2图片高度',
  `one_color`        VARCHAR(20)          NOT NULL DEFAULT ''
  COMMENT 'type=2，7背景颜色 type=4模块底色',
  `two_color`        VARCHAR(20)          NOT NULL DEFAULT ''
  COMMENT 'type=4文字颜色',
  `three_color`      VARCHAR(20)          NOT NULL DEFAULT ''
  COMMENT 'type=4被选中色块',
  `one_type`         TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT 'type=3标题1关联类型，1：商品，2：组合，3：cms，4：点击不跳转',
  `one_display_id`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'type=3标题1关联对象Id',
  `two_type`         TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT 'type=3标题2关联类型，1：商品，2：组合，3：cms，4：点击不跳转',
  `two_display_id`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'type=3标题2关联对象Id',
  `relation_id`      INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '关联id',
  `create_time`      TIMESTAMP            NOT NULL DEFAULT '0000-00-00 00:00:00'
  COMMENT '创建时间',
  `update_time`      TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'cms详情';

/*cms轮播详情*/
CREATE TABLE `cms_carousel_detail` (
  `id`            INT(11) UNSIGNED     NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `cms_detail_id` INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'cms详情id',
  `sequence`      SMALLINT(5) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '排序值，从大到小排序',
  `is_available`  TINYINT(3) UNSIGNED  NOT NULL DEFAULT '1'
  COMMENT '是否可用；0：否，1：是',
  `remark`        VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT 'cms信息备注',
  `type`          TINYINT(3) UNSIGNED  NOT NULL DEFAULT '4'
  COMMENT '关联类型，1：商品，2：组合；3：cms；4：点击不跳转',
  `image`         VARCHAR(100)         NOT NULL DEFAULT ''
  COMMENT 'banner图片url',
  `image_width`   INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'banner图片宽度',
  `image_height`  INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT 'banner图片高度',
  `display_id`    INT(11) UNSIGNED     NOT NULL DEFAULT '0'
  COMMENT '关联对象Id',
  `create_time`   TIMESTAMP            NOT NULL DEFAULT '0000-00-00 00:00:00'
  COMMENT '创建时间',
  `update_time`   TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'cms模版轮播详情';
