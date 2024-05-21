package com.lyc.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/26 10:23
 * @Description:
 */
@Data
public class PageRequestVO<T> implements Serializable {

    private int page = 1;

    private int size=10;

    private T condition;

    private List<OrderVO> sort;

}
