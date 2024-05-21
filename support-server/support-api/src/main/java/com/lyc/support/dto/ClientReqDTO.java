package com.lyc.support.dto;

import com.lyc.common.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/16 18:05
 * @Description:
 */
@Data
@ApiModel
public class ClientReqDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 客户端分组id
     */
    @ApiModelProperty("客户端分组id")
    private String groupId;

    /**
     * 客户端名称
     */
    @ApiModelProperty("客户端名称")
    private String name;

    /**
     * 应用场景，pc、移动
     */
    @ApiModelProperty("应用场景，pc、移动")
    private String appScene;

    /**
     * 应用来源
     */
    @ApiModelProperty("应用来源")
    private String appSource;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    /**
     * 鉴权类型
     */
    @ApiModelProperty("鉴权类型")
    private String grantType;

    /**
     * 状态，上架、下架
     */
    @ApiModelProperty("状态，上架、下架")
    private String state;

    /**
     * 是否匿名访问
     */
    @ApiModelProperty("是否匿名访问")
    private Integer anonymous;


    private List<OrderVO> sort;
}
