package com.zj.auction.common.constant;

import java.util.Date;

/**
 * 
 *************************************************
 * 	公用返回的msg
 * @author  Mr.Chen->MengDaNai                   
 * @version 1.0                
 * @date    2019年1月29日 创建文件                                            
 * @See                            
 *************************************************
 */
public final class SystemConstant {
	public static Date access_token_time = new Date();
	
	SystemConstant(){}
	
	/** 默认的页容量大小 **/
	public static final int DEFAULT_PAGESIZE = 10;
	
	/**	-1,操作失败 	**/
	public static final int OPERATION_FAILED_CODE = -1;
	public static final String OPERATION_FAILED = "操作失败";
	
	/** 参数不能为空 **/
	public static final int DATA_NO_ISEMPTY_CODE = 345;
	public static final String DATA_NO_ISEMPTY = "缺少参数";
	
	/**	0,成功状态码	**/
	public static final int SUCCESS_CODE = 0;
	
	/**	304,没有更多的数据了 **/
	public static final int NO_MORE_DATA_CODE = 304;
	public static final String NO_MORE_DATA = "没有更多数据了";
	
	/** 未获取到数据 **/
	public static final int NO_DATA_OBTAINED_CODE =	204 ;
	public static final String NO_DATA_OBTAINED = "未获取到数据";
	
	/**	320,未登入 **/
	public static final int NO_SESSION_CODE = 320;
	public static final String NO_SESSION = "未登入";

	/**	322,角色异常 **/
	public static final int ROLE_IS_WRONG_CODE = 322;
	public static final String ROLE_IS_WRONG = "登录失败,请重新选择角色!";
	
	/**	321,已注册 **/
	public static final int ALREADY_REGISTERED_CODE = 321;
	public static final String ALREADY_REGISTERED = "已注册";
	
	/**	323,修改失败 **/
	public static final int FAIL_TO_EDIT_CODE = 323;
	public static final String FAIL_TO_EDIT = "修改失败";
	
	/**	331,账号或密码错误 **/
	public static final int INCORRECT_USERNAME_OR_PASSWORD_CODE = 331;
	public static final String INCORRECT_USERNAME_OR_PASSWORD = "账号或密码错误";
	
	/**	344,数据校验不通过 **/
	public static final int DATA_ILLEGALITY_CODE = 344;
	public static final String DATA_ILLEGALITY = "数据非法";
	
	/**	401,未授权 **/
	public static final int AUTHORIZATION_FAILED_CODE = 401;
	public static final String AUTHORIZATION_FAILED = "用户未授权";
	
	/**	402,已冻结 **/
	public static final int FROZEN_CODE = 402;
	public static final String FROZEN = "已冻结";
	
	/**	403,验证码错误 **/
	public static final int ERROR_MESSAGE_CODE = 403;
	public static final String ERROR_MESSAGE = "验证码错误";
	
	/** 405,图形验证码错误**/
	public static final int ERROR_CHECKCODE_CODE = 405;
	public static final String ERROR_CHECKCODE = "图形验证码错误";
	
	/** 406,商铺不存在或已下架 **/
	public static final int NO_SHOP_CODE = 406;
	public static final String NO_SHOP = "商铺不存在或已下架";

	/**	345,请勿重复添加 **/
	public static final int DATA_REPEAT_CODE = 345;
	public static final String DATA_REPEAT = "请勿重复添加";

	/** 407,商品不存在或已下架 **/
	public static final int NO_GOODS_CODE = 407;
	public static final String NO_GOODS = "商品不存在或已下架";
	
	/** 408,商品库存不足 **/
	public static final int NO_PRODUCTNUM_CODE = 408;
	public static final String NO_PRODUCTNUM = "商品库存不足";
	
	/** 409,没有收货地址 **/
	public static final int NO_ADDR_CODE = 409;
	public static final String NO_ADDR = "没有收货地址";
	
	/** 410,订单不存在 **/
	public static final int NO_ORDER_CODE = 410;
	public static final String NO_ORDER = "订单不存在";
	
	public static final int NO_MONEY_CODE = 412;
	public static final String NO_MONEY = "余额不足";
	
	public static final int UPLOAD_FAIL_CODE = 500;
	public static final String UPLOAD_FAIL = "上传失败";

	public static final int ACTIVE_END_CODE = 600;
	public static final String ACTIVE_END = "活动已结束";

	public static final int ALREADY_SPINWIN_CODE = 601;
	public static final String ALREADY_SPINWIN = "今天已经抽过奖了";

	public static final int NO_COIN_CODE = 602;
	public static final String NO_COIN = "金币不足";

	/** 伪删除状态: false:1 无效**/
	public static final Integer DELETED_FALSE = 1;

	/** 300金额有误 **/
	public static final int ERROR_MONEY_CODE = 300;
	public static final String ERROR_MONEY = "金额有误";

	/** 606超出正常购买数量 0 或者负数 **/
	public static final int OUT_NORMAL_QUANTITYY_CODE = 606;
	public static final String OUT_NORMAL_QUANTITY = "无可更改购买数量";


	/** 600,订单状态已改变，请刷新后重试 **/
	public static final int OUT_OF_DELIVERY_CODE = 700;
	public static final String OUT_OF_DELIVERY = "超出配送范围";

	/** 410,商品规格不存在 **/
	public static final int NO_SPECIFICATION_CODE = 410;
	public static final String NO_SPECIFICATION = "商品规格不存在，请删除购物车并购买新规格商品";

	/**	303,数据不存在 **/
	public static final int NO_EXIST_DATA_CODE = 303;
	public static final String NO_EXIST_DATA = "数据不存在";

	/** 430,订单状态已改变，请刷新后重试 **/
	public static final int ORDER_STATUS_CHANGED_CODE = 430;
	public static final String ORDER_STATUS_CHANGED = "订单状态已改变，请刷新后重试";

	/**	801,房间不存在 **/
	public static final int NO_ROOM_DATA_CODE = 801;
	public static final String NO_ROOM_DATA = "房间不存在或还未开播";


	/** 平台基础角色id,开发者、系统管理员、用户、商户 **/
	public static final long ROLE_DEVELOPER_ID = 1;
	public static final long ROLE_ADMIN_ID = 2;
	public static final long ROLE_SHOPER_ID = 4;

	//http://duoqio20180105.oss-cn-beijing.aliyuncs.com/2019/06/03/09/35/c2eecb5b-1fe0-43b3-8408-56fd5567ae23.png
	public static final String USER_IMG =
			"https://duoqio20180105.oss-cn-beijing.aliyuncs.com/fy/2021/03/10/16/12/b52e9a06-93cb-4153-a665-1c9508d04d54.png";
	public static final String BACKGROUD_IMG =
			"https://duoqio20180105.oss-cn-beijing.aliyuncs.com/2019/08/12/17/17/7bcacefb-0555-4e98-8ad9-1e77e4877300.png";




}
