package com.lyc.support.service.impl;

import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.*;
import com.lyc.support.entity.ClientMenu;
import com.lyc.support.entity.qo.OrgUserRolePageRespQO;
import com.lyc.support.entity.qo.UserClientMenuQO;
import com.lyc.support.repository.ClientMenuRepository;
import com.lyc.support.repository.OrgUserRepository;
import com.lyc.support.service.ClientMenuService;
import com.lyc.support.service.ClientService;
import com.lyc.support.sql.ClientMenuSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/4/20 16:36
 * @Description:
 */
@Service
@Log4j2
public class ClientMenuServiceImpl extends SimpleServiceImpl<ClientMenuDTO, ClientMenu,String, ClientMenuRepository> implements  ClientMenuService {

    @Autowired
    ClientService clientService;

    @Autowired
    OrgUserRepository orgUserRepository;

    @Autowired
    ClientMenuSqlConfig clientMenuSqlConfig;

    @Override
    public ResponseVO<List<ClientMenuDTO>> getAllList(ClientMenuReqDTO clientMenuReqDTO) {

        String clientId = clientMenuReqDTO.getClientId();
        if(StringUtils.isNotBlank(clientMenuReqDTO.getClientSecret()) && StringUtils.isBlank(clientMenuReqDTO.getClientId())){

            ClientDTO req = new ClientDTO();
            req.setClientSecret(clientMenuReqDTO.getClientSecret());
            ClientDTO clientDTO = clientService.getOne(req);
            clientId = clientDTO.getId();
        }
        Sort sort = SortUtils.getSort(clientMenuReqDTO.getSort());

        ClientMenuDTO clientMenuDTO = new ClientMenuDTO();
        BeanUtils.copyProperties(clientMenuReqDTO,clientMenuDTO);
        clientMenuDTO.setClientId(clientId);
        List<ClientMenuDTO> clientMenuDTOS = this.findAll(clientMenuDTO,sort);
        return ResponseVO.success(clientMenuDTOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<ClientMenuDTO> saveClientMenu(ClientMenuDTO clientMenuDTO) {


        try{
            this.save(clientMenuDTO);

            if("0".equals(clientMenuDTO.getPid())){
                //设置seriesIds
                clientMenuDTO.setSeriesIds(clientMenuDTO.getId());
                this.update(clientMenuDTO);
            }else{
                //获取该父id的seriesIds
                ClientMenuDTO parent = this.get(clientMenuDTO.getPid());
                StringBuilder stringBuilder = new StringBuilder(parent.getSeriesIds());
                stringBuilder.append(","+clientMenuDTO.getId());

                clientMenuDTO.setSeriesIds(stringBuilder.toString());
                this.update(clientMenuDTO);
            }
            return ResponseVO.success(clientMenuDTO);
        }catch (Exception e){
            log.info("保存菜单失败，e:{},clientMenuDTO:{}",e,clientMenuDTO);
            throw e;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<ClientMenuDTO> updateClientMenu(ClientMenuDTO clientMenuDTO) {
        try{

            ClientMenuDTO queryDto = new ClientMenuDTO();
            queryDto.setSeriesIds(clientMenuDTO.getId());

            //先判断父节点是否有变化
            ClientMenuDTO oldClientMenu = this.get(clientMenuDTO.getId());

            if(oldClientMenu.getPid().equals(clientMenuDTO.getPid())){
                clientMenuDTO.setSeriesIds(oldClientMenu.getSeriesIds());
                this.update(clientMenuDTO);
            }else{
                String parentSeriesIds = "";
                //只有自己
                if("0".equals(clientMenuDTO.getPid())){
                    //设置seriesIds
                    clientMenuDTO.setSeriesIds(clientMenuDTO.getId());
                    parentSeriesIds= clientMenuDTO.getId();
                    this.update(clientMenuDTO);
                }else{
                    //获取该父id的seriesIds
                    ClientMenuDTO parent = this.get(clientMenuDTO.getPid());
                    StringBuilder stringBuilder = new StringBuilder(parent.getSeriesIds());
                    stringBuilder.append(","+clientMenuDTO.getId());
                    clientMenuDTO.setSeriesIds(stringBuilder.toString());
                    parentSeriesIds= stringBuilder.toString();
                    this.update(clientMenuDTO);
                }

                List<ClientMenuDTO> list = this.findAll(queryDto);
                if(list.size()>1){
                    //有子节点
                    for (ClientMenuDTO item:list) {
                        if(!item.getId().equals(clientMenuDTO.getId())){
                            String[] ids = item.getSeriesIds().split(clientMenuDTO.getId());
                            item.setSeriesIds(parentSeriesIds+ids[1]);
                            this.update(item);
                        }
                    }
                }
            }
            return ResponseVO.success(clientMenuDTO);
        }catch (Exception e){
            log.info("修改菜单失败，e:{},clientMenuDTO:{}",e,clientMenuDTO);
            throw e;
        }
    }

    @Override
    public ResponseVO<List<UserClientMenuDTO>> getMenu(ClientMenuReqDTO clientMenuReqDTO) {
        //获取用户的角色信息
        UserDetailDTO userDetailDTO = SecurityContextUtil.getUser();

        //获取该用户的机构信息
        String orgIndex = orgUserRepository.getOrgIndexByUserId(userDetailDTO.getOrgUserId());


        String clientId = clientMenuReqDTO.getClientId();
        if(StringUtils.isNotBlank(clientMenuReqDTO.getClientSecret()) && StringUtils.isBlank(clientMenuReqDTO.getClientId())){

            ClientDTO req = new ClientDTO();
            req.setClientSecret(clientMenuReqDTO.getClientSecret());
            ClientDTO clientDTO = clientService.getOne(req);
            clientId = clientDTO.getId();
        }
        //获取该角色的客户端的菜单信息

        String[] orgList = orgIndex.split(",");

        String inSql = "";
        int index = 0;
        //判断角色信息变化了没
        for(String orgId:orgList){
            if(StringUtils.isNotBlank(orgId)){
                if(index == 0){
                    inSql = "\'"+orgId+"\'";
                }else{
                    inSql = ",\'"+orgId+"\'";
                }
                index++;
            }
        }

        StringBuilder sql = new StringBuilder(clientMenuSqlConfig.getQueryMenuByOrgIdsSql().getColumns());
        sql.append(clientMenuSqlConfig.getQueryMenuByOrgIdsSql().getTables());
        sql.append(clientMenuSqlConfig.getQueryMenuByOrgIdsSql().getCondition());

        Map<String, Object> params = new HashMap<>(1);
        params.put("orgUserId",userDetailDTO.getOrgUserId());
        params.put("orgIds",inSql);
        params.put("clientId",clientId);




        List<UserClientMenuDTO> result = this.nativeQuery(sql.toString(),params, UserClientMenuQO.class, UserClientMenuDTO.class);

        return ResponseVO.success(result);
    }
}
