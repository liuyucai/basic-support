package com.lyc.common.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/3/26 10:35
 * @Description:
 */
@Data
public class PageResponseVO<T> extends ResponseVO<T>{

    private int page = 1;

    private int size = 10;

    private long totalElement = 0L;

    private int totalPages = 0;

    private boolean last = false;

    public static <T> PageResponseVO<T> of(Page page) {
        PageResponseVO<T> result = new PageResponseVO();
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        result.setData((T) page.getContent());
        result.last = page.isLast();
        result.page = page.getNumber() + 1;
        result.size = page.getSize();
        result.totalElement = page.getTotalElements();
        result.totalPages = page.getTotalPages();
        return result;
    }

    public static <T> PageResponseVO<T> success(T data) {
        PageResponseVO<T> result = new PageResponseVO();
        result.setData(data);
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        return result;
    }

    public static <T> PageResponseVO<T> success() {
        PageResponseVO<T> result = new PageResponseVO();
        result.setResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc());
        return result;
    }

    public static <T> PageResponseVO<T> fail() {
        PageResponseVO<T> result = new PageResponseVO();
        result.setResult(ResultCode.FAIL.getCode(), ResultCode.FAIL.getDesc());
        return result;
    }
}
