package com.lyc.support.dto;

import com.lyc.common.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/15 22:07
 * @Description:
 */
@Data
@ApiModel
public class ClientGroupReqDTO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    private List<OrderVO> sort;
}
