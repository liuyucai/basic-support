package com.lyc.support.service.impl;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.ClientMenuDTO;
import com.lyc.support.dto.OrgDTO;
import com.lyc.support.dto.OrgReqDTO;
import com.lyc.support.entity.Org;
import com.lyc.support.repository.OrgRepository;
import com.lyc.support.service.OrgService;
import com.lyc.support.service.OrgUserService;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/**
 * @author: liuyucai
 * @Created: 2023/3/5 18:31
 * @Description:
 */
@Service
@Log4j2
public class OrgServiceImpl extends SimpleServiceImpl<OrgDTO,Org,String,OrgRepository> implements OrgService {

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    OrgUserService orgUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<OrgDTO> insertOrg(OrgDTO orgDTO) {

        try{
            this.save(orgDTO);

            if("0".equals(orgDTO.getPid())){
                //设置seriesIds
                orgDTO.setOrgIndex(orgDTO.getId());
                this.update(orgDTO);
            }else{
                //获取该父id的seriesIds
                OrgDTO parent = this.get(orgDTO.getPid());
                StringBuilder stringBuilder = new StringBuilder(parent.getOrgIndex());
                stringBuilder.append(","+orgDTO.getId());

                orgDTO.setOrgIndex(stringBuilder.toString());
                this.update(orgDTO);
            }

            return ResponseVO.success(orgDTO);
        }catch (Exception e){
            log.info("保存机构失败，e:{},orgDTO:{}",e,orgDTO);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<OrgDTO> updateOrg(OrgDTO orgDTO) {

        try{

            OrgDTO queryDto = new OrgDTO();
            queryDto.setOrgIndex(orgDTO.getId());

            //先判断父节点是否有变化
            OrgDTO oldOrg = this.get(orgDTO.getId());

            if(oldOrg.getPid().equals(orgDTO.getPid())){
                orgDTO.setOrgIndex(oldOrg.getOrgIndex());
                this.update(orgDTO);
            }else{
                String parentSeriesIds = "";
                //只有自己
                if("0".equals(orgDTO.getPid())){
                    //设置seriesIds
                    orgDTO.setOrgIndex(orgDTO.getId());
                    parentSeriesIds= orgDTO.getId();
                    this.update(orgDTO);
                }else{
                    //获取该父id的seriesIds
                    OrgDTO parent = this.get(orgDTO.getPid());
                    StringBuilder stringBuilder = new StringBuilder(parent.getOrgIndex());
                    stringBuilder.append(","+orgDTO.getId());
                    orgDTO.setOrgIndex(stringBuilder.toString());
                    parentSeriesIds= stringBuilder.toString();
                    this.update(orgDTO);
                }

                List<OrgDTO> list = this.findAll(queryDto);
                if(list.size()>1){
                    //有子节点
                    for (OrgDTO item:list) {
                        if(!item.getId().equals(orgDTO.getId())){
                            String[] ids = item.getOrgIndex().split(orgDTO.getId());
                            item.setOrgIndex(parentSeriesIds+ids[1]);
                            this.update(item);
                        }
                    }
                }
            }
            return ResponseVO.success(orgDTO);
        }catch (Exception e){
            log.info("修改机构信息失败，e:{},orgDTO:{}",e,orgDTO);
            throw e;
        }
    }


    @Override
    public List<OrgDTO> getAllList(OrgReqDTO orgReqDTO) {
        Sort sort = SortUtils.getSort(orgReqDTO.getSort());

        OrgDTO orgDTO = new OrgDTO();
        BeanUtils.copyProperties(orgReqDTO,orgDTO);
        List<OrgDTO> orgDTOList = this.findAll(orgDTO,sort);
        return orgDTOList;
    }

    @Override
    public List<OrgDTO> getOrgListByUserId(String userId) {

        //先获取用户的机构信息，
        Map<String,String> orgIdMap = new HashMap<>();
        List<OrgDTO> orgDTOList = orgUserService.getOrgListByUserId(userId);

        for(OrgDTO orgDTO :orgDTOList){
            String[] orgArr = orgDTO.getOrgIndex().split(",");
            for(String orgId:orgArr){
                if(StringUtils.isNotBlank(orgId)){
                    orgIdMap.put(orgId,orgId);
                }
            }
        }

        //获取机构层级列表信息

        String inCondition = "";

        int index = 0;

        for(Map.Entry<String,String> entry:orgIdMap.entrySet()){
            if(index == 0){
                inCondition = "\'"+entry.getValue()+"\'";
            }else{
                inCondition = inCondition+",\'"+entry.getValue()+"\'";
            }
        };




        String sql = "select * from sys_org where org_id in ("+inCondition+") and deleted = 0 ";

        List<OrgDTO> orgDTOList1 = this.nativeQuery(sql,null,Org.class,OrgDTO.class);


        return orgDTOList1;
    }
}
