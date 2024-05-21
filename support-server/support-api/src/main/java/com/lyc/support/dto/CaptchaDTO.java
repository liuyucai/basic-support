package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/28 21:43
 * @Description:
 */
@Data
@ApiModel
public class CaptchaDTO implements Serializable {

    private String base64;

    private long timestamp;
}
