package com.zj.auction.general.vo;
import com.zj.auction.common.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 登录返回
 *
 * @author 胖胖不胖
 * @date 2022/05/28
 */
@Data
@ApiModel("登录返回结果")
public class LoginResp implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "登录凭证 token")
    private String token;

    @ApiModelProperty(value = "用户信息，null表示未注册需调用注册接口")
    private User userInfo;

    @ApiModelProperty(value = "登录提示，如会员过期等（可为空）")
    private String msg;

}
