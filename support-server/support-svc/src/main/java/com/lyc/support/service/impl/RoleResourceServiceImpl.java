package com.lyc.support.service.impl;

import com.lyc.common.vo.ResponseVO;
import com.lyc.common.vo.ResultCode;
import com.lyc.simple.enmus.QueryType;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.PredicateItem;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.*;
import com.lyc.support.emun.ResourceTypeEnum;
import com.lyc.support.entity.ClientMenu;
import com.lyc.support.entity.Role;
import com.lyc.support.entity.RoleResource;
import com.lyc.support.entity.qo.*;
import com.lyc.support.repository.ClientMenuRepository;
import com.lyc.support.repository.ClientRouterRepository;
import com.lyc.support.repository.RoleRepository;
import com.lyc.support.repository.RoleResourceRepository;
import com.lyc.support.service.ClientMenuService;
import com.lyc.support.service.RoleResourceService;
import com.lyc.support.service.RoleService;
import com.lyc.support.sql.RoleResourceSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
 * @Created: 2023/5/13 15:22
 * @Description:
 */
@Service
@Log4j2
public class RoleResourceServiceImpl extends SimpleServiceImpl<RoleResourceDTO, RoleResource,String, RoleResourceRepository> implements RoleResourceService {

    @Autowired
    RoleResourceSqlConfig resourceSqlConfig;

    @Autowired
    ClientMenuService clientMenuService;

    @Autowired
    RoleResourceRepository roleResourceRepository;

    @Autowired
    ClientMenuRepository clientMenuRepository;

    @Autowired
    ClientRouterRepository clientRouterRepository;

    @Override
    public ResponseVO<List<RoleClientRespDTO>> getClientList(RoleClientReqDTO roleClientReqDTO) {

        StringBuilder sql = new StringBuilder(resourceSqlConfig.getQueryClientListSql().getColumns());
        sql.append(resourceSqlConfig.getQueryClientListSql().getTables());
        sql.append(resourceSqlConfig.getQueryClientListSql().getCondition());

        Map<String, Object> params = new HashMap<>();

        params.put("roleId",roleClientReqDTO.getRoleId());

        if (StringUtils.isNotBlank(roleClientReqDTO.getClientName())) {
            sql.append(" and c.name like :name ");
            params.put("name", "%" + roleClientReqDTO.getClientName() + "%");
        }



        List<RoleClientRespDTO> list = this.nativeQuery(
                sql.toString(),
                params,
                RoleClientQO.class,
                RoleClientRespDTO.class);
        return ResponseVO.success(list);
    }

    @Override
    public ResponseVO<List<RoleMenuRespDTO>> getMenuList(RoleResourceReqDTO roleResourceReqDTO) {
        StringBuilder sql = new StringBuilder(resourceSqlConfig.getQueryMenuListSql().getColumns());
        sql.append(resourceSqlConfig.getQueryMenuListSql().getTables());
        sql.append(resourceSqlConfig.getQueryMenuListSql().getCondition());

        Map<String, Object> params = new HashMap<>();

        params.put("roleId",roleResourceReqDTO.getRoleId());
        params.put("clientId",roleResourceReqDTO.getClientId());


        List<RoleMenuRespDTO> list = this.nativeQuery(
                sql.toString(),
                params,
                RoleMenuQO.class,
                RoleMenuRespDTO.class);
        return ResponseVO.success(list);
    }

    @Override
    public ResponseVO<List<RoleRouterRespDTO>> getRouterList(RoleResourceReqDTO roleResourceReqDTO) {
        StringBuilder sql = new StringBuilder(resourceSqlConfig.getQueryRouterListSql().getColumns());
        sql.append(resourceSqlConfig.getQueryRouterListSql().getTables());
        sql.append(resourceSqlConfig.getQueryRouterListSql().getCondition());

        Map<String, Object> params = new HashMap<>();

        params.put("roleId",roleResourceReqDTO.getRoleId());
        params.put("clientId",roleResourceReqDTO.getClientId());

        List<RoleRouterRespDTO> list = this.nativeQuery(
                sql.toString(),
                params,
                RoleRouterQO.class,
                RoleRouterRespDTO.class);
        return ResponseVO.success(list);
    }

    @Override
    public ResponseVO<List<RoleFunctionRespDTO>> getFunctionList(RoleFunctionReqDTO roleFunctionReqDTO) {
        StringBuilder sql = new StringBuilder(resourceSqlConfig.getQueryFunctionListSql().getColumns());
        sql.append(resourceSqlConfig.getQueryFunctionListSql().getTables());
        sql.append(resourceSqlConfig.getQueryFunctionListSql().getCondition());

        Map<String, Object> params = new HashMap<>();

        params.put("roleId",roleFunctionReqDTO.getRoleId());
        params.put("clientId",roleFunctionReqDTO.getClientId());
        params.put("pid",roleFunctionReqDTO.getPid());

        List<RoleFunctionRespDTO> list = this.nativeQuery(
                sql.toString(),
                params,
                RoleFunctionQO.class,
                RoleFunctionRespDTO.class);
        return ResponseVO.success(list);
    }

    @Override
    public ResponseVO openClientAuth(RoleResourceOpenDTO roleResourceOpenDTO) {

        RoleResourceDTO roleResourceDTO = new RoleResourceDTO();

        BeanUtils.copyProperties(roleResourceOpenDTO,roleResourceDTO);

        roleResourceDTO.setResourceId(roleResourceDTO.getClientId());
        roleResourceDTO.setResourceType(ResourceTypeEnum.CLIENT.getCode());

        Integer number = roleResourceRepository.getNumberByResourceIdAndRoleId(roleResourceOpenDTO.getRoleId(),roleResourceOpenDTO.getResourceId());

        if(number.intValue() ==0){
            //先判断是否有该权限了
            this.save(roleResourceDTO);
            return ResponseVO.success(true);
        }else{
            return ResponseVO.fail(ResultCode.ALREADY_EXIST_RECORD.getCode(), ResultCode.ALREADY_EXIST_RECORD.getDesc());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO closeClientAuth(String id) {

        try{
            //先获取客户端的信息，删除改角色下的权限分配
            RoleResourceDTO roleResourceDTO = this.get(id);

            List<PredicateItem> predicates = new ArrayList<>(3);

            PredicateItem predicateItem1 = new PredicateItem("roleId","role_id",roleResourceDTO.getRoleId());
            PredicateItem predicateItem2 = new PredicateItem("clientId","client_id",roleResourceDTO.getClientId());

            predicates.add(predicateItem1);
            predicates.add(predicateItem2);

            this.delete(predicates);
            return ResponseVO.success();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO openMenuAuth(RoleResourceOpenDTO roleResourceOpenDTO) {

        try{
            //获取该菜单的父菜单，给父菜单放开权限
            ClientMenuDTO clientMenuDTO = clientMenuService.get(roleResourceOpenDTO.getResourceId());

            String[] ids = clientMenuDTO.getSeriesIds().split(",");

            for(String id:ids){
                if(!id.equals(roleResourceOpenDTO.getResourceId())){
                    //先判断是否有分配了，没有就添加
                    Integer number = roleResourceRepository.getNumberByResourceIdAndRoleId(roleResourceOpenDTO.getRoleId(),id);
                    if(number.intValue()==0){
                        RoleResourceDTO roleResourceDTO1 = new RoleResourceDTO();
                        roleResourceDTO1.setResourceId(id);
                        roleResourceDTO1.setClientId(roleResourceOpenDTO.getClientId());
                        roleResourceDTO1.setRoleId(roleResourceOpenDTO.getRoleId());
                        roleResourceDTO1.setResourceType(ResourceTypeEnum.MENU.getCode());
                        this.save(roleResourceDTO1);
                    }
                }
            }

            Integer number = roleResourceRepository.getNumberByResourceIdAndRoleId(roleResourceOpenDTO.getRoleId(),roleResourceOpenDTO.getResourceId());

            if(number.intValue()==0){
                RoleResourceDTO roleResourceDTO = new RoleResourceDTO();

                BeanUtils.copyProperties(roleResourceOpenDTO,roleResourceDTO);
                roleResourceDTO.setResourceType(ResourceTypeEnum.MENU.getCode());

                this.save(roleResourceDTO);
                return ResponseVO.success(true);
            }else{
                return ResponseVO.fail(ResultCode.ALREADY_EXIST_RECORD.getCode(), ResultCode.ALREADY_EXIST_RECORD.getDesc());
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO closeMenuAuth(String id) {

        try{
            //先获取该菜单的子菜单
            RoleResourceDTO roleResourceDTO = this.get(id);

            //获取子菜单信息
            List<ClientMenu> list = clientMenuRepository.getMenuByLikeSeriesIds(roleResourceDTO.getResourceId());
            if(list.size() == 1 && list.get(0).getId().equals(roleResourceDTO.getResourceId())){
                this.delete(id);
            }else{
                String inSql = "";
                int index = 0;
                for (ClientMenu clientMenu:list) {
                    if(index == 0){
                        inSql = "\'"+clientMenu.getId().trim()+"\'";
                    }else{
                        inSql = inSql+",\'"+clientMenu.getId().trim()+"\'";
                    }
                    index++;
                }

                List<PredicateItem> predicates = new ArrayList<>(4);

                PredicateItem predicateItem1 = new PredicateItem("roleId","role_id",roleResourceDTO.getRoleId());
                PredicateItem predicateItem2 = new PredicateItem("clientId","client_id",roleResourceDTO.getClientId());
                PredicateItem predicateItem3 = new PredicateItem("resourceType","resource_type",ResourceTypeEnum.MENU.getCode());
                PredicateItem predicateItem4 = new PredicateItem("resourceId","resource_id",inSql,QueryType.IN);

                predicates.add(predicateItem1);
                predicates.add(predicateItem2);
                predicates.add(predicateItem3);
                predicates.add(predicateItem4);
                this.delete(predicates);
            }
            return ResponseVO.success();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseVO openRouterAuth(RoleResourceOpenDTO roleResourceOpenDTO) {

        //先判断是否有该权限了
        Integer number = roleResourceRepository.getNumberByResourceIdAndRoleId(roleResourceOpenDTO.getRoleId(),roleResourceOpenDTO.getResourceId());

        if(number.intValue()==0){
            RoleResourceDTO roleResourceDTO = new RoleResourceDTO();

            BeanUtils.copyProperties(roleResourceOpenDTO,roleResourceDTO);
            roleResourceDTO.setResourceType(ResourceTypeEnum.ROUTER.getCode());

            this.save(roleResourceDTO);
            return ResponseVO.success(true);
        }else{
            return ResponseVO.fail(ResultCode.ALREADY_EXIST_RECORD.getCode(), ResultCode.ALREADY_EXIST_RECORD.getDesc());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO closeRouterAuth(String id) {

        try {

            //先获取资源信息
            RoleResourceDTO roleResourceDTO = this.get(id);

            //获取该路由的功能信息，
            List<String> functionsList = clientRouterRepository.getFunctionIdListByRouterId(id);

            //删除该功能的配置
            String inSql = "";
            int index = 0;
            for (String functionId : functionsList) {
                if (index == 0) {
                    inSql = "\'" + functionId.trim() + "\'";
                } else {
                    inSql = inSql + ",\'" + functionId.trim() + "\'";
                }
                index++;
            }

            List<PredicateItem> predicates = new ArrayList<>(4);

            PredicateItem predicateItem1 = new PredicateItem("roleId", "role_id", roleResourceDTO.getRoleId());
            PredicateItem predicateItem2 = new PredicateItem("clientId", "client_id", roleResourceDTO.getClientId());
            PredicateItem predicateItem3 = new PredicateItem("resourceType", "resource_type", ResourceTypeEnum.FUNCTION.getCode());
            PredicateItem predicateItem4 = new PredicateItem("resourceId", "resource_id", inSql, QueryType.IN);

            predicates.add(predicateItem1);
            predicates.add(predicateItem2);
            predicates.add(predicateItem3);
            predicates.add(predicateItem4);

            //先删掉该路由的功能权限
            this.delete(predicates);


            //删除路由的配置

            this.delete(id);

            return ResponseVO.success();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseVO openFunctionAuth(RoleResourceOpenDTO roleResourceOpenDTO) {
        //先判断是否有该权限了
        Integer number = roleResourceRepository.getNumberByResourceIdAndRoleId(roleResourceOpenDTO.getRoleId(),roleResourceOpenDTO.getResourceId());

        if(number.intValue()==0){
            RoleResourceDTO roleResourceDTO = new RoleResourceDTO();

            BeanUtils.copyProperties(roleResourceOpenDTO,roleResourceDTO);
            roleResourceDTO.setResourceType(ResourceTypeEnum.FUNCTION.getCode());

            this.save(roleResourceDTO);
            return ResponseVO.success(true);
        }else{
            return ResponseVO.fail(ResultCode.ALREADY_EXIST_RECORD.getCode(), ResultCode.ALREADY_EXIST_RECORD.getDesc());
        }
    }

    @Override
    public ResponseVO closeFunctionAuth(String id) {
        //删除路由的配置

        this.delete(id);

        return ResponseVO.success();
    }
}
