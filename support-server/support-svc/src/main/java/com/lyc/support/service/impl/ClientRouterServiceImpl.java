package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.DeleteWapper;
import com.lyc.simple.utils.PredicateItem;
import com.lyc.simple.utils.SortUtils;
import com.lyc.simple.utils.StringUtils;
import com.lyc.support.dto.*;
import com.lyc.support.emun.RouterTypeEnum;
import com.lyc.support.entity.Client;
import com.lyc.support.entity.ClientRouter;
import com.lyc.support.entity.qo.RouterInfoPageQO;
import com.lyc.support.entity.qo.UserClientMenuQO;
import com.lyc.support.entity.qo.UserClientRouterQO;
import com.lyc.support.repository.ClientRepository;
import com.lyc.support.repository.ClientRouterRepository;
import com.lyc.support.service.ClientRouterService;
import com.lyc.support.service.ClientService;
import com.lyc.support.sql.ClientRouterSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/4/23 18:29
 * @Description:
 */
@Service
@Log4j2
public class ClientRouterServiceImpl extends SimpleServiceImpl<ClientRouterDTO, ClientRouter,String, ClientRouterRepository> implements ClientRouterService {


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientRouterSqlConfig clientRouterSqlConfig;

    @Override
    public ResponseVO<RouterInfoDTO> saveRouterInfo(RouterInfoDTO routerInfoDTO) {

        ClientRouterDTO clientRouterDTO = new ClientRouterDTO();
        BeanUtils.copyProperties(routerInfoDTO,clientRouterDTO);

        clientRouterDTO.setType(RouterTypeEnum.ROUTER.getCode());
        this.save(clientRouterDTO);

        routerInfoDTO.setId(clientRouterDTO.getId());
        return ResponseVO.success(routerInfoDTO);
    }

    @Override
    public ResponseVO<RouterInfoDTO> updateRouterInfo(RouterInfoDTO routerInfoDTO) {

        this.update(routerInfoDTO);
        return ResponseVO.success(routerInfoDTO);
    }

    @Override
    public Page<RouterInfoPageRespDTO> getPageList(PageRequestVO<RouterInfoReqDTO> page) {



//        ClientRouterDTO clientRouterDTO = new ClientRouterDTO();
//        BeanUtils.copyProperties(page.getCondition(),clientRouterDTO);

        Sort sort = SortUtils.getSort(page.getSort());

        PageRequest pageRequest =  sort == null?PageRequest.of(page.getPage() - 1, page.getSize()):PageRequest.of(page.getPage() - 1, page.getSize(), sort);

//        Page<RouterInfoDTO> p = this.findPage(clientRouterDTO, pageRequest,RouterInfoDTO.class);
//        return p;

        Page<RouterInfoPageRespDTO> p = this.complexFindPage(page.getCondition(),pageRequest, RouterInfoPageQO.class,RouterInfoPageRespDTO.class);

        return  p;

    }

    @Override
    public List<FunctionInfoDTO> getFunctionList(FunctionInfoDTO functionInfoDTO) {

        ClientRouterDTO clientRouterDTO = new ClientRouterDTO();
        BeanUtils.copyProperties(functionInfoDTO,clientRouterDTO);

        clientRouterDTO.setType(RouterTypeEnum.FUNCTION.getCode());

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "sort");
        List<FunctionInfoDTO> functionInfoDTOList = this.findAll(clientRouterDTO,FunctionInfoDTO.class,Sort.by(order));
        return functionInfoDTOList;
    }

    @Override
    public ResponseVO<FunctionInfoDTO> saveFunctionInfo(FunctionInfoDTO functionInfoDTO) {
        ClientRouterDTO clientRouterDTO = new ClientRouterDTO();
        BeanUtils.copyProperties(functionInfoDTO,clientRouterDTO);

        clientRouterDTO.setType(RouterTypeEnum.FUNCTION.getCode());
        this.save(clientRouterDTO);

        functionInfoDTO.setId(clientRouterDTO.getId());
        return ResponseVO.success(functionInfoDTO);
    }

    @Override
    public ResponseVO<FunctionInfoDTO> updateFunctionInfo(FunctionInfoDTO functionInfoDTO) {
        this.update(functionInfoDTO);
        return ResponseVO.success(functionInfoDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRouter(String id) {

        PredicateItem predicateItem = new PredicateItem();
        predicateItem.setName("pid");
        predicateItem.setColumn("pid");
        predicateItem.setValue(id);

        this.delete(predicateItem);

        this.delete(id);
    }

    @Override
    public List<UserClientRouterDTO> getByClientSecret(ClientRouterReqDTO clientRouterReqDTO) {


        try{
            if(StringUtils.isBlank(clientRouterReqDTO.getClientId())){
                //获取clientId信息
                Client client = clientRepository.getByClientSecret(clientRouterReqDTO.getClientSecret());

                clientRouterReqDTO.setClientId(client.getId());
            }

            UserDetailDTO userDetailDTO = SecurityContextUtil.getUser();

            StringBuilder sql = new StringBuilder(clientRouterSqlConfig.getQueryUserRouterSql().getColumns());
            sql.append(clientRouterSqlConfig.getQueryUserRouterSql().getTables());

//            and re.role_id in (:roleIds)

            Map<String, Object> params = new HashMap<>(1);

            String inSql = "";
            int index = 0;
            //判断角色信息变化了没
//            for(String roleId:userDetailDTO.getRoleList()){
//                if(StringUtils.isNotBlank(roleId)){
//                    if(index == 0){
//                        inSql = "\'"+roleId+"\'";
//                    }else{
//                        inSql = ",\'"+roleId+"\'";
//                    }
//                    index++;
//                }
//            }

            //占位符的，不能用 \'
            for(String roleId:userDetailDTO.getRoleList()){
                if(StringUtils.isNotBlank(roleId)){
                    if(index == 0){
                        inSql = roleId;
                    }else{
                        inSql = ","+roleId;
                    }
                    index++;
                }
            }
            params.put("roleIds",inSql);

//            sql.append(" and re.role_id in ("+inSql +")");
            sql.append(clientRouterSqlConfig.getQueryUserRouterSql().getCondition());
            params.put("clientId",clientRouterReqDTO.getClientId());

            if(StringUtils.isNotBlank(clientRouterReqDTO.getType())){
                sql.append(" and r.type = :type");
                params.put("type",clientRouterReqDTO.getType());
            }

            List<UserClientRouterDTO> result = this.nativeQuery(sql.toString(),params, UserClientRouterQO.class, UserClientRouterDTO.class);

            return result;

        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
}
