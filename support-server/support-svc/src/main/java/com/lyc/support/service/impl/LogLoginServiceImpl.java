package com.lyc.support.service.impl;

import com.lyc.auth.dto.LogLoginDTO;
import com.lyc.common.utils.StringUtils;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.support.dto.LogLoginReqDTO;
import com.lyc.support.dto.LogLoginRespDTO;
import com.lyc.support.entity.LogLogin;
import com.lyc.support.entity.qo.LogLoginRespQO;
import com.lyc.support.repository.LogLoginRepository;
import com.lyc.support.service.LogLoginService;
import com.lyc.support.sql.LogLoginSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:04
 * @Description:
 */
@Service
@Log4j2
public class LogLoginServiceImpl extends SimpleServiceImpl<LogLoginDTO, LogLogin,String, LogLoginRepository> implements LogLoginService {

    @Autowired
    LogLoginSqlConfig logLoginSqlConfig;

    @Override
    public Page<LogLoginRespDTO> getPageList(PageRequestVO<LogLoginReqDTO> page) {

        PageRequest pageRequest =  PageRequest.of(page.getPage() - 1, page.getSize());


        StringBuilder sql = new StringBuilder(logLoginSqlConfig.getQueryPageSql().getColumns());
        sql.append(logLoginSqlConfig.getQueryPageSql().getTables());
        sql.append(logLoginSqlConfig.getQueryPageSql().getCondition());

        LogLoginReqDTO condition = page.getCondition();

        Map<String, Object> params = new HashMap<>();

        if(condition != null){

            if(StringUtils.isNotBlank(condition.getClientId())){
                sql.append(" and l.client_id = :clientId");
                params.put("clientId",condition.getClientId());
            }

            if(StringUtils.isNotBlank(condition.getUserName())){
                sql.append(" and l.user_name = :userName");
                params.put("userName",condition.getUserName());
            }
            if(StringUtils.isNotBlank(condition.getIpAddress())){
                sql.append(" and l.ip_address like % :userName %");
                params.put("ipAddress",condition.getIpAddress());
            }

            if(StringUtils.isNotBlank(condition.getStatus())){
                sql.append(" and l.status = :status");
                params.put("status",condition.getStatus());
            }

            if(StringUtils.isNotBlank(condition.getState())){

                if("1".equals(condition.getState())){
                    sql.append(" and l.status = '200'");
                }else{
                    sql.append(" and l.status != '200'");
                }
            }

            if(condition.getStartTime() != null){
                sql.append(" and l.login_time >= :startTime ");
                params.put("startTime",condition.getStartTime());
            }
            if(condition.getEndTime() != null){
                sql.append(" and l.login_time <= :endTime ");
                params.put("endTime",condition.getEndTime());
            }
        }

        Page<LogLoginRespDTO> page1 = this.nativeQueryByPage(sql.toString(),pageRequest,params, LogLoginRespQO.class,LogLoginRespDTO.class);

        return page1;
    }
}
