package com.lyc.support.dto;

import com.lyc.common.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 15:58
 * @Description:
 */
@Data
@ApiModel
public class RoleClientReqDTO implements Serializable {

    /**
     * roleId
     */
    @ApiModelProperty("roleId")
    private String roleId;

    /**
     * 客户端名称
     */
    @ApiModelProperty("客户端名称")
    private String clientName;

}
