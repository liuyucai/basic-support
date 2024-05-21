package com.lyc.support.service;

import com.lyc.auth.dto.LogLoginDTO;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.LogLoginReqDTO;
import com.lyc.support.dto.LogLoginRespDTO;
import com.lyc.support.entity.LogLogin;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:03
 * @Description:
 */
public interface LogLoginService extends BaseService<LogLoginDTO, LogLogin,String> {
    Page<LogLoginRespDTO> getPageList(PageRequestVO<LogLoginReqDTO> page);
}
