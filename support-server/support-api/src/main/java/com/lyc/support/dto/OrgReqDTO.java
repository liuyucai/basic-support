package com.lyc.support.dto;

import com.lyc.common.vo.OrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/29 8:43
 * @Description:
 */
@Data
@ApiModel
public class OrgReqDTO implements Serializable {


    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String id;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String name;

    /**
     * 上级组织id
     */
    @ApiModelProperty("上级组织id")
    private String pid;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer state;

    /**
     * 组织机构索引
     */
    @ApiModelProperty("组织机构索引")
    private String orgIndex;

    private List<OrderVO> sort;

}
