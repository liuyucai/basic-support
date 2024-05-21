package com.lyc.auth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/11/9 8:57
 * @Description:
 */
@Data
@ApiModel
public class RoleApiDTO implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * serviceName
     */
    private String serviceName;

    /**
     * url
     */
    private String url;
}
