package com.lyc.support.dto;

import com.lyc.common.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/20 17:55
 * @Description:
 */
@Data
@ApiModel
public class ClientMenuReqDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    /**
     * 上级菜单id
     */
    @ApiModelProperty("上级菜单id")
    private String pid;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 触发动作，路由/新开标签页
     */
    @ApiModelProperty("触发动作，路由/新开标签页")
    private String action;

    /**
     * 是否显示
     */
    @ApiModelProperty("是否显示")
    private Integer visiable;

    /**
     * 是否缓冲
     */
    @ApiModelProperty("是否缓冲")
    private Integer keepAlive;

    private List<OrderVO> sort;

}
