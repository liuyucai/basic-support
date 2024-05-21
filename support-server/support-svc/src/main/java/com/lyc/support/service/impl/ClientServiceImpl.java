package com.lyc.support.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.lyc.common.vo.OrderVO;
import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.*;
import com.lyc.support.entity.Client;
import com.lyc.support.entity.qo.ClientPageQO;
import com.lyc.support.repository.ClientRepository;
import com.lyc.support.service.ClientService;
import com.lyc.support.sql.ClientSqlConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 21:27
 * @Description:
 */
@Service
@Log4j2
public class ClientServiceImpl extends SimpleServiceImpl<ClientDTO, Client,String, ClientRepository> implements ClientService {

    @Autowired
    ClientSqlConfig clientSqlConfig;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Page<ClientPageRespDTO> getPageList(PageRequestVO<ClientDTO> page) {


        ClientDTO condition = page.getCondition();
        StringBuilder sql = new StringBuilder(clientSqlConfig.getQueryPageSql().getColumns());
        sql.append(clientSqlConfig.getQueryPageSql().getTables());
        sql.append(clientSqlConfig.getQueryPageSql().getCondition());

        Map<String, Object> params = new HashMap<>();

        if(condition != null){
            if(StringUtils.isNotBlank(condition.getId())){
                sql.append(" and c.id = :id ");
                params.put("id", condition.getId());
            }

            if(StringUtils.isNotBlank(condition.getGroupId())){
                sql.append(" and c.group_id = :groupId ");
                params.put("groupId", condition.getGroupId());
            }

            if (StringUtils.isNotBlank(condition.getName())) {
                sql.append(" and c.name like :name ");
                params.put("name", "%" + condition.getName() + "%");
            }

            if(StringUtils.isNotBlank(condition.getAppScene())){
                sql.append(" and c.app_scene = :appScene ");
                params.put("appScene", condition.getAppScene());
            }

            if(StringUtils.isNotBlank(condition.getAppSource())){
                sql.append(" and c.app_source = :appSource ");
                params.put("appSource", condition.getAppSource());
            }

            if(StringUtils.isNotBlank(condition.getState())){
                sql.append(" and c.state = :state ");
                params.put("state", condition.getAppSource());
            }

            if(StringUtils.isNotBlank(condition.getGrantType())){

                String[] grantTypeArr = condition.getGrantType().split(",");

                if(grantTypeArr.length>0){

                    int index = 0;
                    for (String grantType:grantTypeArr) {
                        if(StringUtils.isNotBlank(grantType)){
                            if(index == 0){
                                sql.append(" and (c.grant_type like %"+grantType+"% ");
                            }else{
                                sql.append(" or c.grant_type like %"+grantType+"% ");
                            }
                            index++;
                        }
                    }
                    if(index>0){
                        sql.append(" ) ");
                    }
                }else{
                    sql.append(" and c.grant_type like :state ");
                    params.put("grantType", "%" + grantTypeArr[0] + "%");
                }
            }
        }

        Sort sort = SortUtils.getSort(page.getSort(),"c");


        PageRequest pageRequest =  sort == null?PageRequest.of(page.getPage() - 1, page.getSize()):PageRequest.of(page.getPage() - 1, page.getSize(), sort);

        Page<ClientPageRespDTO> p = this.nativeQueryByPage(
                sql.toString(),
                pageRequest,
                params,
                ClientPageQO.class,
                ClientPageRespDTO.class);
        return p;
    }

    @Override
    public ClientDTO insertClient(ClientBaseDTO clientBaseDTO) {

        //创建clientSecret
        String clientSecret = RandomUtil.randomString(8);

        Integer clientNumber = clientRepository.getClientNumberBySecret(clientSecret);

        //先获取是否有该clientSecret
        while (clientNumber.intValue()>0){
            clientSecret = RandomUtil.randomString(8);
            clientNumber = clientRepository.getClientNumberBySecret(clientSecret);
        }
        ClientDTO clientDTO = new ClientDTO();

        BeanUtils.copyProperties(clientBaseDTO,clientDTO);
        clientDTO.setClientSecret(clientSecret);

        this.save(clientDTO);

        return clientDTO;
    }

    @Override
    public ClientBaseDTO updateClient(ClientBaseDTO clientBaseDTO) {

        this.update(clientBaseDTO);
        return clientBaseDTO;
    }

    @Override
    public ResponseVO<List<ClientDTO>> getAllList(ClientReqDTO clientReqDTO) {

        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(clientReqDTO,clientDTO);
        List<ClientDTO> list = this.findAll(clientDTO,clientReqDTO.getSort());
        return ResponseVO.success(list);
    }

    @Override
    public ResponseVO<ClientDTO> getByClientSecret(String clientSecret) {

        Client client = clientRepository.getByClientSecret(clientSecret);

        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client,clientDTO);
        return ResponseVO.success(clientDTO);
    }
}
