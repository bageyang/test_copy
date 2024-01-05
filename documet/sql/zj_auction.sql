SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for zj_auction
-- ----------------------------
DROP TABLE IF EXISTS `zj_auction`;
CREATE TABLE `zj_auction`
(
    `id`              bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '拍品表',
    `auction_name`    varchar(32)    DEFAULT NULL COMMENT '拍品名称',
    `goods_id`        bigint         NOT NULL COMMENT '商品id',
    `cash_price`      decimal(10, 2) NOT NULL COMMENT '现金价格',
    `integral_price`  decimal(10, 2) DEFAULT NULL COMMENT '积分',
    `stock_quantity`  int            DEFAULT NULL COMMENT '库存数量',
    `auction_status`  tinyint        DEFAULT NULL COMMENT '拍品状态(0:未开始,1:进行中，2:已结束)',
    `auction_area_id` int            DEFAULT NULL COMMENT '拍卖区域ID',
    `create_time`     datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time`     datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`      tinyint        DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for zj_auction_stock_relation
-- ----------------------------
DROP TABLE IF EXISTS `zj_auction_stock_relation`;
CREATE TABLE `zj_auction_stock_relation`
(
    `id`           bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
    `auction_id`   bigint(0) NOT NULL,
    `stock_number` bigint(0) NOT NULL,
    `stock_id`     bigint(0) NOT NULL,
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP (0),
    `update_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0),
    `is_deleted`   tinyint(0) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;

-- ----------------------------
-- Table structure for zj_goods
-- ----------------------------
DROP TABLE IF EXISTS `zj_goods`;
CREATE TABLE `zj_goods`
(
    `id`                    bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
    `post_id`               int                                                           DEFAULT NULL COMMENT '邮费模板id',
    `user_id`               bigint                                                        DEFAULT NULL COMMENT '用户id',
    `old_price`             decimal(10, 2)                                                DEFAULT '0.00' COMMENT '原价',
    `price`                 decimal(10, 2) NOT NULL                                       DEFAULT '0.00' COMMENT '默认价格',
    `discount_prices`       decimal(10, 2)                                                DEFAULT NULL COMMENT '折后价格',
    `rebate_transaction_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       DEFAULT NULL COMMENT '模板的id,这个值>0表示购买这个会向上级返利',
    `hot_flag`              int                                                           DEFAULT '0' COMMENT '是否在热销展示 1为展示',
    `discount_flag`         int                                                           DEFAULT '0' COMMENT '是否在折扣区展示 1为展示',
    `new_flag`              int                                                           DEFAULT '0' COMMENT '是否新品展示 1为展示',
    `goods_name`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       DEFAULT NULL COMMENT '商品名称',
    `img_url`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       DEFAULT NULL COMMENT '封面图',
    `video_url`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '视频地址',
    `content`               text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品详情',
    `type_id`               int                                                           DEFAULT NULL COMMENT '类型id  默认0 普通商品  1 飞鱼自营 2 茅台专区',
    `status`                int                                                           DEFAULT '0' COMMENT '状态,0：正常,1：下架',
    `remarks`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       DEFAULT NULL COMMENT '备注',
    `create_time`           datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`           datetime                                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
    `add_user_id`           int                                                           DEFAULT NULL COMMENT '添加人id',
    `weight`                double                                                        DEFAULT NULL COMMENT '每件重量',
    `volume`                double                                                        DEFAULT NULL COMMENT '每件体积',
    `rebate_ratio`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       DEFAULT NULL COMMENT '返利比例',
    `service_tel`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci       DEFAULT NULL COMMENT '客服电话',
    `sort`                  int                                                           DEFAULT '0' COMMENT '排序',
    `update_user_id`        bigint unsigned DEFAULT '0' COMMENT '更新者',
    `sell_total`            int            NOT NULL                                       DEFAULT '0' COMMENT '销量',
    `frozen_explain`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '下架理由',
    `ship_address`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发货地址',
    `buckle_point_ratio`    decimal(19, 3) NOT NULL                                       DEFAULT '0.010' COMMENT '平台返佣',
    `specification`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '产品规格',
    `tag_id`                bigint                                                        DEFAULT '-1' COMMENT '标签 0 全国 其他金刚区',
    `auction_area_id`       bigint                                                        DEFAULT '-1' COMMENT '竞拍区编号  上午 下午 晚上',
    `auction_status`        int                                                           DEFAULT NULL,
    `hand_num`              int                                                           DEFAULT '61' COMMENT '交割手数',
    `delivery_money`        decimal(20, 2)                                                DEFAULT NULL COMMENT '交割金额',
    `sub_type`              tinyint                                                       DEFAULT '0' COMMENT '产品二级分类',
    `cash_price`            decimal(20, 2)                                                DEFAULT '0.00' COMMENT '新价/现金部分',
    `is_deleted`            tinyint                                                       DEFAULT '0' COMMENT '删除状态，0未删除，1已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16803 DEFAULT CHARSET=utf8  COMMENT='商品表';

-- ----------------------------
-- Table structure for zj_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `zj_goods_category`;
CREATE TABLE `zj_goods_category`
(
    `id`          bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `pid`         bigint(0) NOT NULL COMMENT '父级id',
    `name`        varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '名称',
    `sort_index`  int(0) NULL DEFAULT NULL COMMENT '同级序号',
    `level`       int(0) NULL DEFAULT NULL COMMENT '层级',
    `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_deleted`  tinyint(0) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8  COMMENT = '商品分类表';

-- ----------------------------
-- Table structure for zj_order
-- ----------------------------
DROP TABLE IF EXISTS `zj_order`;
CREATE TABLE `zj_order`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `order_sn`       bigint NOT NULL COMMENT '订单编号',
    `goods_id`       bigint NOT NULL COMMENT '商品id',
    `auction_id`     bigint         DEFAULT NULL COMMENT '拍品id',
    `stock_id`       bigint NOT NULL COMMENT '库存id',
    `stock_number`   bigint NOT NULL COMMENT '库存号',
    `user_id`        bigint         DEFAULT NULL COMMENT '用户帐号',
    `total_amount`   decimal(10, 2) DEFAULT NULL COMMENT '订单总金额',
    `pay_amount`     decimal(10, 2) DEFAULT NULL COMMENT '应付金额（实际支付金额）',
    `integral_fee`   decimal(10, 2) DEFAULT '0.00' COMMENT '积分费用(钻石费用)',
    `hand_fee`       decimal(10, 2) unsigned DEFAULT '0.00' COMMENT '手续费',
    `freight_amount` decimal(10, 2) DEFAULT '0.00' COMMENT '运费金额',
    `order_type`     tinyint        DEFAULT NULL COMMENT '订单类型',
    `order_status`   tinyint        DEFAULT NULL COMMENT '订单状态',
    `delivery_time`  datetime       DEFAULT NULL COMMENT '发货时间',
    `receive_time`   datetime       DEFAULT NULL COMMENT '确认收货时间',
    `item_id`        bigint         DEFAULT NULL,
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_deleted`     tinyint        DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for zj_pay_log
-- ----------------------------
DROP TABLE IF EXISTS `zj_pay_log`;
CREATE TABLE `zj_pay_log`
(
    `id`               bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`          bigint(0) NULL DEFAULT NULL,
    `order_id`         bigint(0) NULL DEFAULT NULL,
    `order_sn`         varchar(64) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `pay_type`         tinyint(0) NULL DEFAULT NULL COMMENT '支付方式',
    `req_param`        varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `req_status`       tinyint(0) NULL DEFAULT NULL,
    `res_status`       tinyint(0) NULL DEFAULT NULL,
    `call_back_param`  varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `call_back_status` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `err_info`         varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `create_time`      datetime(0) NULL DEFAULT NULL,
    `update_time`      datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;

-- ----------------------------
-- Table structure for zj_stock
-- ----------------------------
DROP TABLE IF EXISTS `zj_stock`;
CREATE TABLE `zj_stock`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT,
    `goods_id`       bigint         NOT NULL COMMENT '商品id',
    `cash_price`     decimal(10, 2) NOT NULL COMMENT '当前现金价格',
    `integral_price` decimal(10, 2) DEFAULT NULL COMMENT '积分价格',
    `stock_number`   bigint         NOT NULL COMMENT '库存编号',
    `stock_status`   tinyint        NOT NULL COMMENT '货品状态',
    `owner_id`       bigint         NOT NULL COMMENT '拥有者id',
    `transfer_num`   int            DEFAULT '0' COMMENT '转拍数',
    `create_time`    datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`     tinyint        DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for zj_user_bill
-- ----------------------------
DROP TABLE IF EXISTS `zj_user_bill`;
CREATE TABLE `zj_user_bill`
(
    `id`           bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`      bigint(0) NOT NULL,
    `bill_type`    tinyint(0) NULL DEFAULT NULL COMMENT '账单类型',
    `pay_type`     tinyint(0) NULL DEFAULT NULL COMMENT '支付方式',
    `bill_status`  tinyint(0) NULL DEFAULT NULL COMMENT '账单状态',
    `transtion_sn` varchar(64) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '流水号',
    `external_sn`  varchar(64) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '外部流水号',
    `amount`       decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
    `finish_time`  datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
    `remark`       varchar(32) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '备注',
    `create_time`  datetime(0) DEFAULT CURRENT_TIMESTAMP,
    `update_time`  datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`   tinyint(0) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;

-- ----------------------------
-- Table structure for zj_wallet
-- ----------------------------
DROP TABLE IF EXISTS `zj_wallet`;
CREATE TABLE `zj_wallet`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     bigint  NOT NULL,
    `fund_type`   tinyint NOT NULL COMMENT '钱包类型',
    `balance`     decimal(10, 2) DEFAULT '0.00' COMMENT '余额',
    `freeze`      decimal(10, 2) DEFAULT '0.00' COMMENT '锁定金额',
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id_fund_type` (`user_id`,`fund_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for zj_wallet_record
-- ----------------------------
DROP TABLE IF EXISTS `zj_wallet_record`;
CREATE TABLE `zj_wallet_record`
(
    `id`               bigint(0) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`          bigint(0) NOT NULL,
    `wallet_type`      tinyint(0) NULL DEFAULT NULL COMMENT '钱包类型',
    `transaction_type` tinyint(0) NULL DEFAULT NULL COMMENT '交易类型',
    `transaction_sn`   varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '事务号',
    `balance_before`   decimal(10, 2) NULL DEFAULT NULL COMMENT '更改前金额',
    `change_balance`   decimal(10, 2) NULL DEFAULT NULL COMMENT '更改金额',
    `balance_after`    decimal(10, 2) NULL DEFAULT NULL COMMENT '更改之后金额',
    `remark`           varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL,
    `create_time`      datetime(0) DEFAULT CURRENT_TIMESTAMP,
    `update_time`      datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

CREATE TABLE `zj_withdraw`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id`      bigint         NOT NULL,
    `withdraw_num` decimal(10, 2) NOT NULL,
    `audit_status` tinyint  DEFAULT NULL,
    `draw_time`    datetime DEFAULT NULL,
    `audit_time`   datetime DEFAULT NULL,
    `create_time`  datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `zj_express_order`
(
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT,
    `order_id`          bigint unsigned DEFAULT NULL,
    `user_id`           bigint         DEFAULT NULL,
    `order_sn`          bigint unsigned DEFAULT NULL,
    `freight_amount`    decimal(10, 2) DEFAULT NULL COMMENT '运费',
    `receive_user_name` varchar(32)    DEFAULT NULL COMMENT '收货人名称',
    `courier_firm_name` varchar(64)    DEFAULT NULL COMMENT '快递公司名称',
    `ems_no`            varchar(255)   DEFAULT NULL COMMENT '快递单号',
    `addr_id`           bigint         DEFAULT NULL COMMENT '地址id',
    `delivery_time`     datetime       DEFAULT NULL,
    `addr`              text CHARACTER SET utf8mb4 COMMENT '地址',
    `express_status`    tinyint        DEFAULT NULL,
    `receive_time`      datetime       DEFAULT NULL,
    `create_time`       datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time`       datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
