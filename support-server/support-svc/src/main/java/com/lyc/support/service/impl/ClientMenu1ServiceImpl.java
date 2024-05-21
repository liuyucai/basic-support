package com.lyc.support.service.impl;

import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.*;
import com.lyc.support.entity.Client;
import com.lyc.support.entity.ClientMenu1;
import com.lyc.support.entity.qo.UserClientMenuQO;
import com.lyc.support.entity.qo.UserClientRouterQO;
import com.lyc.support.repository.ClientMenu1Repository;
import com.lyc.support.repository.ClientRepository;
import com.lyc.support.repository.OrgUserRepository;
import com.lyc.support.service.ClientMenu1Service;
import com.lyc.support.service.ClientService;
import com.lyc.support.sql.ClientMenu1SqlConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/8/25 14:35
 * @Description:
 */
@Service
@Log4j2
public class ClientMenu1ServiceImpl extends SimpleServiceImpl<ClientMenu1DTO, ClientMenu1,String, ClientMenu1Repository> implements ClientMenu1Service {

    @Autowired
    ClientService clientService;

    @Autowired
    OrgUserRepository orgUserRepository;

    @Autowired
    ClientMenu1SqlConfig clientMenu1SqlConfig;


    @Autowired
    ClientRepository clientRepository;

    @Override
    public ResponseVO<List<ClientMenu1DTO>> getAllList(ClientMenuReqDTO clientMenuReqDTO) {
        String clientId = clientMenuReqDTO.getClientId();
        if(StringUtils.isNotBlank(clientMenuReqDTO.getClientSecret()) && StringUtils.isBlank(clientMenuReqDTO.getClientId())){

            ClientDTO req = new ClientDTO();
            req.setClientSecret(clientMenuReqDTO.getClientSecret());
            ClientDTO clientDTO = clientService.getOne(req);
            clientId = clientDTO.getId();
        }
        Sort sort = SortUtils.getSort(clientMenuReqDTO.getSort());

        ClientMenu1DTO clientMenuDTO = new ClientMenu1DTO();
        BeanUtils.copyProperties(clientMenuReqDTO,clientMenuDTO);
        clientMenuDTO.setClientId(clientId);
        List<ClientMenu1DTO> clientMenuDTOS = this.findAll(clientMenuDTO,sort);
        return ResponseVO.success(clientMenuDTOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<ClientMenu1DTO> saveClientMenu(ClientMenu1DTO clientMenuDTO) {
        try{
            this.save(clientMenuDTO);

            if("0".equals(clientMenuDTO.getPid())){
                //设置seriesIds
                clientMenuDTO.setSeriesIds(clientMenuDTO.getId());
                this.update(clientMenuDTO);
            }else{
                //获取该父id的seriesIds
                ClientMenu1DTO parent = this.get(clientMenuDTO.getPid());
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
    public ResponseVO<ClientMenu1DTO> updateClientMenu(ClientMenu1DTO clientMenuDTO) {
        try{

            ClientMenu1DTO queryDto = new ClientMenu1DTO();
            queryDto.setSeriesIds(clientMenuDTO.getId());

            //先判断父节点是否有变化
            ClientMenu1DTO oldClientMenu = this.get(clientMenuDTO.getId());

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
                    ClientMenu1DTO parent = this.get(clientMenuDTO.getPid());
                    StringBuilder stringBuilder = new StringBuilder(parent.getSeriesIds());
                    stringBuilder.append(","+clientMenuDTO.getId());
                    clientMenuDTO.setSeriesIds(stringBuilder.toString());
                    parentSeriesIds= stringBuilder.toString();
                    this.update(clientMenuDTO);
                }

                List<ClientMenu1DTO> list = this.findAll(queryDto);
                if(list.size()>1){
                    //有子节点
                    for (ClientMenu1DTO item:list) {
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
    public List<UserClientMenuDTO> getByClientSecret(UserClientMenuReqDTO userClientMenuReqDTO) {

        try{
            if(StringUtils.isBlank(userClientMenuReqDTO.getClientId())){
                //获取clientId信息
                Client client = clientRepository.getByClientSecret(userClientMenuReqDTO.getClientSecret());

                userClientMenuReqDTO.setClientId(client.getId());
            }

            UserDetailDTO userDetailDTO = SecurityContextUtil.getUser();

            StringBuilder sql = new StringBuilder(clientMenu1SqlConfig.getQueryUserMenuSql().getColumns());
            sql.append(clientMenu1SqlConfig.getQueryUserMenuSql().getTables());

//            and re.role_id in (:roleIds)
            Map<String, Object> params = new HashMap<>(1);

            String inSql = "";
            int index = 0;

            //获取该用户的角色信息

            //占位符的，不能用 \'
            for(String roleId:userDetailDTO.getRoleList()){
                if(StringUtils.isNotBlank(roleId)){
                    if(index == 0){
                        inSql = roleId;
                    }else{
                        inSql = inSql+","+roleId;
                    }
                    index++;
                }
            }
            params.put("roleIds",inSql);

//            sql.append(" and re.role_id in ("+inSql +")");
            sql.append(clientMenu1SqlConfig.getQueryUserMenuSql().getCondition());
            params.put("clientId",userClientMenuReqDTO.getClientId());

            if(StringUtils.isNotBlank(userClientMenuReqDTO.getMenuType())){

                String[] arr = userClientMenuReqDTO.getMenuType().split(",");

                String types = "";
                index = 0;
                for(String menuType:arr){
                    if(StringUtils.isNotBlank(menuType)){
                        if(index == 0){
                            types = "\'"+menuType+"\'";
                        }else{
                            types = types+",\'"+menuType+"\'";
                        }
                        index++;
                    }
                }

                sql.append(" and m.menu_type in ("+types+")");

//                sql.append(" and m.menu_type in (:menuType)");
//                params.put("menuType",types);
            }

            List<UserClientMenuDTO> result = this.nativeQuery(sql.toString(),params, UserClientMenuQO.class, UserClientMenuDTO.class);

            return result;

        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
}
