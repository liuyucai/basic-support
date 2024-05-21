package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.ClientDTO;
import com.lyc.support.dto.RoleDTO;
import com.lyc.support.dto.RolePageRespDTO;
import com.lyc.support.entity.Role;
import com.lyc.support.entity.qo.RolePageRespQO;
import com.lyc.support.repository.RoleRepository;
import com.lyc.support.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author: liuyucai
 * @Created: 2023/4/29 13:51
 * @Description:
 */
@Service
@Log4j2
public class RoleServiceImpl extends SimpleServiceImpl<RoleDTO, Role,String, RoleRepository> implements RoleService{
    @Override
    public Page<RolePageRespDTO> getPageList(PageRequestVO<RoleDTO> page) {
        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());

        return this.complexFindPage(page.getCondition(),pageRequest, RolePageRespQO.class,RolePageRespDTO.class);
    }
}
