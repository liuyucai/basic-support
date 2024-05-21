package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.common.vo.ResultCode;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.SortUtils;
import com.lyc.simple.utils.StringUtils;
import com.lyc.support.dto.*;
import com.lyc.support.emun.ResourceTypeEnum;
import com.lyc.support.entity.OauthMenuApi;
import com.lyc.support.entity.qo.OauthMenuApiQO;
import com.lyc.support.entity.qo.RouterInfoPageQO;
import com.lyc.support.repository.OauthMenuApiRepository;
import com.lyc.support.service.OauthMenuApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/11/1 8:51
 * @Description:
 */
@Service
@Log4j2
public class OauthMenuApiServiceImpl  extends SimpleServiceImpl<OauthMenuApiDTO, OauthMenuApi,String, OauthMenuApiRepository> implements OauthMenuApiService {

    @Autowired
    OauthMenuApiRepository oauthMenuApiRepository;

    @Override
    public Page<OauthMenuApiRespDTO> getPageList(PageRequestVO<OauthMenuApiReqDTO> page) {
        Sort sort = SortUtils.getSort(page.getSort());

        //用自定义SQL
        StringBuilder sql = new StringBuilder(" select s.id,s.name,s.url,s.source_type,s.service_id,s.request_method,s.auth_status,m.id as menu_api_id " +
                " from oauth_menu_api m left join oauth_service_api s on m.api_id = s.id " +
                " where m.deleted = '0' ");

        OauthMenuApiReqDTO condition = page.getCondition();

        Map<String,Object> param = new HashMap<>(8);

        if(condition != null){

            if(StringUtils.isNotBlank(condition.getId())){
                sql.append(" and m.menu_id = :id");
                param.put("id",condition.getId());
            }
            if(StringUtils.isNotBlank(condition.getMenuId())){
                sql.append(" and m.menu_id = :menuId");
                param.put("menuId",condition.getMenuId());
            }

            if(StringUtils.isNotBlank(condition.getServiceIds())){
                String [] serviceIds =  condition.getServiceIds().split(",");

                if(serviceIds.length == 1){
                    sql.append(" and s.service_id = :serviceId");
                    param.put("serviceId",serviceIds[0]);
                }else if(serviceIds.length > 1){

                    String inSql = "";
                    int i = 0;
                    for (String id:serviceIds) {
                        if(StringUtils.isNotBlank(id)){
                            if(i==0){
                                inSql = "\'"+id+"\'";
                            }else{
                                inSql = inSql+",\'"+id+"\'";
                            }
                            i++;
                        }
                    }
//                    sql.append(" and s.service_id in (:serviceId)");
//                    param.put("serviceId",condition.getServiceIds());

                    sql.append(" and s.service_id in ("+inSql+")");
                }
            }

            if(StringUtils.isNotBlank(condition.getApiName())){
                sql.append(" and s.name like %:apiName%");
                param.put("apiName",condition.getApiName());
            }

            if(StringUtils.isNotBlank(condition.getApiUrl())){
                sql.append(" and s.url like %:apiUrl%");
                param.put("apiUrl",condition.getApiUrl());
            }
        }

        PageRequest pageRequest =  sort == null?PageRequest.of(page.getPage() - 1, page.getSize()):PageRequest.of(page.getPage() - 1, page.getSize(), sort);

        Page<OauthMenuApiRespDTO> p = this.nativeQueryByPage(sql.toString(),pageRequest,param, OauthMenuApiQO.class,OauthMenuApiRespDTO.class);

        return  p;
    }

    @Override
    public ResponseVO openApiAuth(OauthMenuApiDTO oauthMenuApiDTO) {

        Integer number = oauthMenuApiRepository.getNumberByMenuIdAndApiId(oauthMenuApiDTO.getMenuId(),oauthMenuApiDTO.getApiId());

        if(number.intValue() ==0){
            //先判断是否有该权限了
            this.save(oauthMenuApiDTO);
            return ResponseVO.success(true);
        }else{
            return ResponseVO.fail(ResultCode.ALREADY_EXIST_RECORD.getCode(), ResultCode.ALREADY_EXIST_RECORD.getDesc());
        }
    }

    @Override
    public ResponseVO closeApiAuth(String id) {

        this.delete(id);
        return ResponseVO.success(true);
    }

    @Override
    public Page<OauthMenuApiRespDTO> getAllPageList(PageRequestVO<OauthMenuApiReqDTO> page) {
        Sort sort = SortUtils.getSort(page.getSort());

        //用自定义SQL
        StringBuilder sql = new StringBuilder(" select s.id,s.name,s.url,s.source_type,s.service_id,s.request_method,s.auth_status,m.id as menu_api_id " +
                " from oauth_service_api s left join oauth_menu_api m on s.id = m.api_id and m.deleted = '0' " +
                " where s.deleted = '0' ");

        OauthMenuApiReqDTO condition = page.getCondition();

        Map<String,Object> param = new HashMap<>(8);

        if(condition != null){

            if(StringUtils.isNotBlank(condition.getId())){
                sql.append(" and m.menu_id = :id");
                param.put("id",condition.getId());
            }
            if(StringUtils.isNotBlank(condition.getMenuId())){
                sql.append(" and m.menu_id = :menuId");
                param.put("menuId",condition.getMenuId());
            }

            if(StringUtils.isNotBlank(condition.getServiceIds())){
                String [] serviceIds =  condition.getServiceIds().split(",");

                if(serviceIds.length == 1){
                    sql.append(" and s.service_id = :serviceId");
                    param.put("serviceId",serviceIds[0]);
                }else if(serviceIds.length > 1){

                    String inSql = "";
                    int i = 0;
                    for (String id:serviceIds) {
                        if(StringUtils.isNotBlank(id)){
                            if(i==0){
                                inSql = "\'"+id+"\'";
                            }else{
                                inSql = inSql+",\'"+id+"\'";
                            }
                            i++;
                        }
                    }
//                    sql.append(" and s.service_id in (:serviceId)");
//                    param.put("serviceId",inSql);

                    sql.append(" and s.service_id in ("+inSql+")");
                }
            }

            if(StringUtils.isNotBlank(condition.getApiName())){
                sql.append(" and s.name like %:apiName%");
                param.put("apiName",condition.getApiName());
            }

            if(StringUtils.isNotBlank(condition.getApiUrl())){
                sql.append(" and s.url like %:apiUrl%");
                param.put("apiUrl",condition.getApiUrl());
            }
        }

        PageRequest pageRequest =  sort == null?PageRequest.of(page.getPage() - 1, page.getSize()):PageRequest.of(page.getPage() - 1, page.getSize(), sort);

        Page<OauthMenuApiRespDTO> p = this.nativeQueryByPage(sql.toString(),pageRequest,param, OauthMenuApiQO.class,OauthMenuApiRespDTO.class);

        return  p;
    }
}
