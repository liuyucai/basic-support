package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.security.service.SecurityService;
import com.lyc.security.service.ServiceApiService1;
import com.lyc.simple.dto.BaseDTO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.StringUtils;
import com.lyc.support.dto.OauthServiceApiAuthStatusUpdateDTO;
import com.lyc.support.dto.OauthServiceApiBaseDTO;
import com.lyc.support.dto.OauthServiceApiDTO;
import com.lyc.support.emun.ResponseCodeEnum;
import com.lyc.support.entity.OauthServiceApi;
import com.lyc.support.repository.OauthServiceApiRepository;
import com.lyc.support.service.OauthServiceApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/7/30 12:34
 * @Description:
 */
@Service
@Log4j2
public class OauthServiceApiServiceImpl extends SimpleServiceImpl<OauthServiceApiDTO, OauthServiceApi,String, OauthServiceApiRepository> implements OauthServiceApiService {


    @Autowired
    OauthServiceApiRepository oauthServiceApiRepository;

    @Autowired
    SecurityService securityService;

    @Override
    public Page<OauthServiceApiDTO> getPageList(PageRequestVO<OauthServiceApiDTO> page) {
        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());
        return this.findPage(page.getCondition(),pageRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<BaseDTO> insertApi(OauthServiceApiBaseDTO oauthServiceApiBaseDTO) {

        //先api格式化
        String formatUrl = oauthServiceApiBaseDTO.getUrl().replaceAll("\\{.*?\\}}","");

        //先判断有没有该路由信息
        Integer count = oauthServiceApiRepository.getCountUrlNumber(oauthServiceApiBaseDTO.getServiceId(),formatUrl);

        if(count != null &&  count.intValue()>0){
            return ResponseVO.fail(ResponseCodeEnum.API_EXIST_ERROR.getCode(),ResponseCodeEnum.API_EXIST_ERROR.getDesc());
        }

        OauthServiceApiDTO oauthServiceApiDTO = new OauthServiceApiDTO();
        BeanUtils.copyProperties(oauthServiceApiBaseDTO,oauthServiceApiDTO);

        oauthServiceApiDTO.setSourceType("INSERT");
        oauthServiceApiDTO.setAuthSetting("UPDATE");
        oauthServiceApiDTO.setVersion(1);
        oauthServiceApiDTO.setCompareStatus("1");
        oauthServiceApiDTO.setFormatUrl(formatUrl);

        oauthServiceApiDTO.setPermissionType("UPDATE");

        this.save(oauthServiceApiDTO);
        oauthServiceApiBaseDTO.setId(oauthServiceApiDTO.getId());

        //添加完成后，重新刷新路由
        securityService.loadApiAuth(oauthServiceApiBaseDTO.getServiceId(),null);

        return ResponseVO.success(oauthServiceApiBaseDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<BaseDTO> updateApi(OauthServiceApiBaseDTO oauthServiceApiBaseDTO) {

        //如果是系统添加的，只能更改权限配置，如果是手工添加的，可以更改其他的

        //先获取原来的信息
        OauthServiceApiDTO oauthServiceApiDTO = this.get(oauthServiceApiBaseDTO.getId());

        if("SYSTEM".equals(oauthServiceApiDTO.getSourceType())){
            OauthServiceApiAuthStatusUpdateDTO oauthServiceApiAuthStatusUpdateDTO = new OauthServiceApiAuthStatusUpdateDTO();
            oauthServiceApiAuthStatusUpdateDTO.setId(oauthServiceApiBaseDTO.getId());
            oauthServiceApiAuthStatusUpdateDTO.setAuthSetting("UPDATE");
            oauthServiceApiAuthStatusUpdateDTO.setAuthStatus(oauthServiceApiBaseDTO.getAuthStatus());
            this.update(oauthServiceApiAuthStatusUpdateDTO);
        }else{
            this.update(oauthServiceApiBaseDTO);
        }

        //添加完成后，重新刷新路由
        securityService.loadApiAuth(oauthServiceApiDTO.getServiceId(),null);

        return ResponseVO.success(oauthServiceApiBaseDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApi(String id) {

        OauthServiceApiDTO oauthServiceApiDTO = this.get(id);

        this.delete(id);

        //添加完成后，重新刷新路由
        securityService.loadApiAuth(oauthServiceApiDTO.getServiceId(),null);
    }
}
