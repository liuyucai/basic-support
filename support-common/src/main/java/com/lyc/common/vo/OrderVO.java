package com.lyc.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/3/26 10:30
 * @Description:
 */
@Data
public class OrderVO implements Serializable {

    private String property;

    private String direction;
}
