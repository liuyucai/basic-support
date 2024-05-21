package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.ClientDTO;
import com.lyc.support.dto.RoleDTO;
import com.lyc.support.dto.RolePageRespDTO;
import com.lyc.support.entity.Role;
import org.springframework.data.domain.Page;

/**
 * @author: liuyucai
 * @Created: 2023/4/29 13:50
 * @Description:
 */
public interface RoleService extends BaseService<RoleDTO, Role,String> {
    Page<RolePageRespDTO> getPageList(PageRequestVO<RoleDTO> page);
}
