
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for zj_auction
-- ----------------------------
DROP TABLE IF EXISTS `zj_auction`;
CREATE TABLE `zj_auction`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '拍品表',
  `auction_name` varchar(32) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '拍品名称',
  `goods_id` bigint(0) NOT NULL COMMENT '商品id',
  `prices` decimal(10, 2) NOT NULL COMMENT '价格',
  `stock_quantity` int(0) NULL DEFAULT NULL COMMENT '库存数量',
  `auction_status` tinyint(0) NULL DEFAULT NULL COMMENT '拍品状态(0:未开始,1:进行中，2:已结束)',
  `auction_area_id` int(0) NULL DEFAULT NULL COMMENT '拍卖区域ID',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

-- ----------------------------
-- Table structure for zj_auction_stock_relation
-- ----------------------------
DROP TABLE IF EXISTS `zj_auction_stock_relation`;
CREATE TABLE `zj_auction_stock_relation`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `auction_id` bigint(0) NOT NULL,
  `stock_number` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `stock_id` bigint(0) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

-- ----------------------------
-- Table structure for zj_goods
-- ----------------------------
DROP TABLE IF EXISTS `zj_goods`;
CREATE TABLE `zj_goods`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `post_id` int(0)  DEFAULT NULL COMMENT '邮费模板id',
  `user_id` bigint(0)  DEFAULT NULL COMMENT '用户id',
  `old_price` decimal(10, 2)  DEFAULT 0.00 COMMENT '原价',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '默认价格',
  `discount_prices` decimal(10, 2) NULL DEFAULT NULL COMMENT '折后价格',
  `acquire_integral` int(0)  DEFAULT 0 COMMENT '可返积分',
  `rebate_transaction_id` varchar(255) CHARACTER SET utf8  DEFAULT NULL COMMENT '模板的id,这个值>0表示购买这个会向上级返利',
  `hot_flag` int(0)  DEFAULT 0 COMMENT '是否在热销展示 1为展示',
  `discount_flag` int(0)  DEFAULT 0 COMMENT '是否在折扣区展示 1为展示',
  `new_flag` int(0)  DEFAULT 0 COMMENT '是否新品展示 1为展示',
  `goods_name` varchar(255) CHARACTER SET utf8   DEFAULT NULL COMMENT '商品名称',
  `img_url` varchar(255) CHARACTER SET utf8   DEFAULT NULL COMMENT '封面图',
  `video_url` varchar(255) CHARACTER SET utf8mb4   DEFAULT '' COMMENT '视频地址',
  `content` text CHARACTER SET utf8   COMMENT '商品详情',
  `type_id` int(0)  DEFAULT NULL COMMENT '类型id  默认0 普通商品  1 飞鱼自营 2 茅台专区',
  `status` int(0)  DEFAULT 0 COMMENT '状态,0：正常,1：下架',
  `transaction_id` varchar(255) CHARACTER SET utf8  DEFAULT NULL COMMENT '事务id',
  `remarks` varchar(255) CHARACTER SET utf8   DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0)  DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime(0)  DEFAULT NULL COMMENT '最近更新时间',
  `add_user_id` int(0)  DEFAULT NULL COMMENT '添加人id',
  `weight` double  DEFAULT NULL COMMENT '每件重量',
  `volume` double  DEFAULT NULL COMMENT '每件体积',
  `rebate_ratio` varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '返利比例',
  `service_tel` varchar(100) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '客服电话',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `update_user_id` bigint(0) UNSIGNED NULL DEFAULT 0 COMMENT '更新者',
  `sell_total` int(0) NOT NULL DEFAULT 0 COMMENT '销量',
  `recommend` tinyint(0) NULL DEFAULT 0 COMMENT '精品推荐 0 默认 1是',
  `frozen_explain` varchar(255) CHARACTER SET utf8mb4   DEFAULT NULL COMMENT '下架理由',
  `ship_address` varchar(255) CHARACTER SET utf8mb4   DEFAULT NULL COMMENT '发货地址',
  `buckle_point_ratio` decimal(19, 3)   DEFAULT 0.010 COMMENT '平台返佣',
  `specification` varchar(255) CHARACTER SET utf8mb4   DEFAULT NULL COMMENT '产品规格',
  `tag_id` bigint(0)  DEFAULT -1 COMMENT '标签 0 全国 其他金刚区',
  `auction_id` bigint(0)  DEFAULT -1 COMMENT '竞拍区编号  上午 下午 晚上',
  `auction_status` int(0)  DEFAULT NULL,
  `hand_num` int(0)  DEFAULT 61 COMMENT '交割手数',
  `delivery_money` decimal(20, 2)  DEFAULT NULL COMMENT '交割金额',
  `sub_type` tinyint(0)  DEFAULT 0 COMMENT '产品二级分类',
  `cash_price` decimal(20, 2) NULL DEFAULT 0.00 COMMENT '新价/现金部分',
  `is_deleted` tinyint(0)  DEFAULT 0 COMMENT '删除状态，0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16797 CHARACTER SET = utf8 COMMENT = '商品表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for zj_order
-- ----------------------------
DROP TABLE IF EXISTS `zj_order`;
CREATE TABLE `zj_order`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_sn` varchar(64) CHARACTER SET utf8  NOT NULL COMMENT '订单编号',
  `goods_id` bigint(0) NOT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户帐号',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总金额',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付金额（实际支付金额）',
  `order_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '手续费',
  `freight_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费金额',
  `pay_type` int(0) NULL DEFAULT NULL COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `order_type` int(0) NULL DEFAULT NULL COMMENT '订单类型：0->正常订单；1->秒杀订单',
  `delete_status` int(0) NOT NULL DEFAULT 0 COMMENT '删除状态：0->未删除；1->已删除',
  `order_status` int(0) NULL DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `item_id` bigint(0) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8  COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zj_pay_log
-- ----------------------------
DROP TABLE IF EXISTS `zj_pay_log`;
CREATE TABLE `zj_pay_log`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '支付日志表',
  `user_id` bigint(0) NULL DEFAULT NULL,
  `order_id` bigint(0) NULL DEFAULT NULL,
  `order_sn` varchar(64) CHARACTER SET utf8mb4   DEFAULT NULL,
  `pay_type` tinyint(0) NULL DEFAULT NULL COMMENT '支付方式',
  `req_param` varchar(255) CHARACTER SET utf8mb4   DEFAULT NULL,
  `req_status` tinyint(0) NULL DEFAULT NULL,
  `res_status` tinyint(0) NULL DEFAULT NULL,
  `call_back_param` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL,
  `call_back_status` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL,
  `err_info` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Table structure for zj_stock
-- ----------------------------
DROP TABLE IF EXISTS `zj_stock`;
CREATE TABLE `zj_stock`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT  COMMENT '商品库存表',
  `goods_id` bigint(0) NOT NULL COMMENT '商品id',
  `prices` decimal(10, 2) NOT NULL COMMENT '当前价格',
  `stock_number` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '库存编号',
  `stock_status` tinyint(0) NOT NULL COMMENT '货品状态',
  `owner_id` bigint(0) NOT NULL COMMENT '拥有者id',
  `transfer_num` int(0) NULL DEFAULT 0 COMMENT '转拍数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Table structure for zj_user_bill
-- ----------------------------
DROP TABLE IF EXISTS `zj_user_bill`;
CREATE TABLE `zj_user_bill`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户账单表',
  `user_id` bigint(0) NOT NULL,
  `bill_type` tinyint(0) NULL DEFAULT NULL COMMENT '账单类型',
  `pay_type` tinyint(0) NULL DEFAULT NULL COMMENT '支付方式',
  `bill_status` tinyint(0) NULL DEFAULT NULL COMMENT '账单状态',
  `transtion_sn` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流水号',
  `external_sn` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '外部流水号',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `remark` varchar(32) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Table structure for zj_wallet
-- ----------------------------
DROP TABLE IF EXISTS `zj_wallet`;
CREATE TABLE `zj_wallet`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户钱包表',
  `user_id` int(0) NULL DEFAULT NULL,
  `fund_type` tinyint(0) NULL DEFAULT NULL COMMENT '资产类型',
  `balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '余额',
  `freeze` decimal(10, 2) NULL DEFAULT NULL COMMENT '锁定金额',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Table structure for zj_wallet_record
-- ----------------------------
DROP TABLE IF EXISTS `zj_wallet_record`;
CREATE TABLE `zj_wallet_record`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户钱包明细表',
  `user_id` bigint(0) NOT NULL,
  `wallet_type` tinyint(0) NULL DEFAULT NULL COMMENT '钱包类型',
  `transaction_type` tinyint(0) NULL DEFAULT NULL COMMENT '交易类型',
  `balance_before` decimal(10, 2) NULL DEFAULT NULL COMMENT '更改前金额',
  `change_balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '更改金额',
  `balance_after` decimal(10, 2) NULL DEFAULT NULL COMMENT '更改之后金额',
  `remark` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
