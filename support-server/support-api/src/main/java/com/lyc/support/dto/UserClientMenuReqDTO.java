package com.lyc.support.dto;

import com.lyc.common.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/10/7 8:47
 * @Description:
 */
@Data
@ApiModel
public class UserClientMenuReqDTO implements Serializable {

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

    /**
     * 客户端secret
     */
    @ApiModelProperty("客户端secret")
    private String clientSecret;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String menuType;

    /**
     * 是否鉴权
     */
    @ApiModelProperty("是否鉴权")
    private Integer authentication;

    private List<OrderVO> sort;
}
