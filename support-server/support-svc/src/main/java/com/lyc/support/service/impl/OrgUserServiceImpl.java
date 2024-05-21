package com.lyc.support.service.impl;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.utils.SecurityContextUtil;
import com.lyc.simple.enmus.QueryType;
import com.lyc.simple.jpa.SimpleServiceImpl;
import com.lyc.simple.utils.DeleteWapper;
import com.lyc.simple.utils.PredicateItem;
import com.lyc.simple.utils.SortUtils;
import com.lyc.support.dto.*;
import com.lyc.support.entity.Org;
import com.lyc.support.entity.OrgUser;
import com.lyc.support.entity.Role;
import com.lyc.support.entity.qo.*;
import com.lyc.support.repository.ClientRepository;
import com.lyc.support.repository.OrgRepository;
import com.lyc.support.repository.OrgUserRepository;
import com.lyc.support.repository.RoleUserRepository;
import com.lyc.support.service.OrgUserService;
import com.lyc.support.service.RoleUserService;
import com.lyc.support.sql.OrgUserSqlConfig;
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
 * @Created: 2023/5/1 21:41
 * @Description:
 */
@Service
@Log4j2
public class OrgUserServiceImpl extends SimpleServiceImpl<OrgUserDTO, OrgUser,String, OrgUserRepository> implements OrgUserService {

    @Autowired
    OrgUserRepository orgUserRepository;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    RoleUserRepository roleUserRepository;

    @Autowired
    OrgUserSqlConfig orgUserSqlConfig;

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    ClientRepository clientRepository;




    @Override
    public List<OrgDTO> getOrgListByUserId(String userId) {

        OrgUserDTO orgUserDTO = new OrgUserDTO();
        orgUserDTO.setUserId(userId);

        List<OrgDTO> list = this.complexFindAll(orgUserDTO, UserOrgQO.class,OrgDTO.class);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<OrgUserSaveDTO> saveOrgUser(OrgUserSaveDTO orgUserSaveDTO) {

        try{

            //获取该机构的用户数量
            Integer number = orgUserRepository.getOrgUserNumberByInfo(orgUserSaveDTO.getOrgId(),orgUserSaveDTO.getUserId());

            if(number.intValue()==0){

                OrgUserDTO orgUserDTO = new OrgUserDTO();

                BeanUtils.copyProperties(orgUserSaveDTO,orgUserDTO);

                //判断是否有主用户，没有的话设为主用户，有的话普通用户
                Integer mainAccountNumber = orgUserRepository.getMainAccountNumberByUserId(orgUserSaveDTO.getUserId());


                if(mainAccountNumber.intValue() >0){
                    orgUserDTO.setType(0);
                }else{
                    orgUserDTO.setType(1);
                }

                //添加用户信息
                this.save(orgUserDTO);

                orgUserSaveDTO.setId(orgUserDTO.getId());

                //添加角色信息
                for (String roleId:orgUserSaveDTO.getRoleIds()) {
                    RoleUserDTO roleUserDTO = new RoleUserDTO();
                    roleUserDTO.setOrgUserId(orgUserDTO.getId());
                    roleUserDTO.setRoleId(roleId);
                    roleUserService.save(roleUserDTO);
                }
                return ResponseVO.success(orgUserSaveDTO);
            }else{
                return ResponseVO.fail("已存在该机构的账号");
            }
        }catch (Exception e){
            log.error("保存角色信息失败",e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<OrgUserSaveDTO> updateOrgUser(OrgUserSaveDTO orgUserSaveDTO) {

        try{
            //修改的时候，判断是否修改账号信息，如果修改账号信息，要判断用户类型
            OrgUserDTO orgUserDTO1 = new OrgUserDTO();
            orgUserDTO1.setOrgId(orgUserSaveDTO.getOrgId());
            orgUserDTO1.setUserId(orgUserSaveDTO.getUserId());

            List<OrgUserDTO> list = this.findAll(orgUserDTO1);

            if(list.size()>1){
                return ResponseVO.fail("账号异常");
            }

            if(list.size()==1){
                //说明
                if(!orgUserSaveDTO.getId().equals(list.get(0).getId())){
                    return ResponseVO.fail("已存在该机构的账号");
                }
                // 说明账号和机构都没有换， 不用更新 type  只更新基本信息
                OrgUserDTO old = this.get(orgUserSaveDTO.getId());
                BeanUtils.copyProperties(orgUserSaveDTO,old);
                this.update(old);
            }else{
                //说明换了新的账号或机构,
                //判断新账号的主用户信息，  该账号                //账号 + 机构
                //如果只是换了机构，账号没有换，    除非换了账号，才用判断type
                OrgUserDTO old = this.get(orgUserSaveDTO.getId());
                BeanUtils.copyProperties(orgUserSaveDTO,old);

                //判断是否换了账号
                if(!old.getUserId().equals(orgUserSaveDTO.getUserId())){
                    //说明更换帐号了，判断是否有主用户，没有的话设为主用户，有的话普通用户
                    Integer mainAccountNumber = orgUserRepository.getMainAccountNumberByUserId(orgUserSaveDTO.getUserId());

                    if(mainAccountNumber.intValue() >0){
                        old.setType(0);
                    }else{
                        old.setType(1);
                    }
                }
                this.update(old);
            }

            String inSql = "";
            int index = 0;
            //判断角色信息变化了没
            for(String roleId:orgUserSaveDTO.getRoleIds()){
                if(StringUtils.isNotBlank(roleId)){
                    if(index == 0){
                        inSql = "\'"+roleId+"\'";
                    }else{
                        inSql = inSql+",\'"+roleId+"\'";
                    }
                    index++;
                }
            }
            DeleteWapper deleteWapper = new DeleteWapper();

            List<PredicateItem> predicateItemList = new ArrayList<>(2);
            PredicateItem predicateItem1 = new PredicateItem("orgUserId","org_user_id",orgUserSaveDTO.getId(),QueryType.EQUAL);
            PredicateItem predicateItem2 = new PredicateItem("roleId","role_id",inSql,QueryType.NOT_IN);
            predicateItemList.add(predicateItem1);
            predicateItemList.add(predicateItem2);
            deleteWapper.setTable("sys_role_user");
            deleteWapper.setPredicates(predicateItemList);
            roleUserService.delete(deleteWapper);
            for(String roleId:orgUserSaveDTO.getRoleIds()){
                Integer number = roleUserRepository.getNumberByInfo(orgUserSaveDTO.getId(),roleId);
                if(number.intValue()==0){
                    RoleUserDTO roleUserDTO = new RoleUserDTO();
                    roleUserDTO.setRoleId(roleId);
                    roleUserDTO.setOrgUserId(orgUserSaveDTO.getId());
                    roleUserService.save(roleUserDTO);
                }
            }
            return ResponseVO.success(orgUserSaveDTO);
        }catch (Exception e){
            log.error("修改角色信息失败",e);
            throw e;
        }
    }

    @Override
    public Page<OrgUserPageRespDTO> getPageList(PageRequestVO<OrgUserPageReqDTO> page) {

        StringBuilder sql = new StringBuilder(orgUserSqlConfig.getQueryPageSql().getColumns());

        sql.append(orgUserSqlConfig.getQueryPageSql().getTables());
        sql.append(orgUserSqlConfig.getQueryPageSql().getCondition());

        OrgUserPageReqDTO condition = page.getCondition();

        Map<String,Object> params = new HashMap<>(9);
        if(condition != null){

            if(condition.getUserId() !=null){
                sql.append(" and ou.user_id = :userId");
                params.put("userId",condition.getUserId());
            }
            if(StringUtils.isNotBlank(condition.getNickName())){
                sql.append(" and ou.nick_name like % :nickName" + "%");
                params.put("nickName",condition.getNickName());
            }

            if(condition.getEnabled() !=null){
                sql.append(" and ou.enabled = :enabled");
                params.put("enabled",condition.getEnabled());
            }

            if(StringUtils.isNotBlank(condition.getUserName())){
                sql.append(" and u.user_name like % :userName" + "%");
                params.put("userName",condition.getUserName());
            }

            if(StringUtils.isNotBlank(condition.getPhoneNo())){
                sql.append(" and u.phone_no like % :phoneNo" + "%");
                params.put("phoneNo",condition.getPhoneNo());
            }

            if(StringUtils.isNotBlank(condition.getOrgId())){


                if("1".equals(condition.getQueryRang())){
                    //本机构
                    sql.append(" and o.id = :orgId");
                    params.put("orgId",condition.getOrgId());
                }else{
                    //本机构及子级
                    sql.append(" and o.org_index like \'%"+condition.getOrgId()+"%\'");
                }
            }
        }

        Sort sort = SortUtils.getSort(page.getSort());

        PageRequest pageRequest =  sort == null?PageRequest.of(page.getPage() - 1, page.getSize()):PageRequest.of(page.getPage() - 1, page.getSize(), sort);

//        Page<OrgUserPageRespDTO> p = this.complexFindPage(page.getCondition(),pageRequest, OrgUserPageRespQO.class,OrgUserPageRespDTO.class);

        Page<OrgUserPageRespDTO> p = this.nativeQueryByPage(sql.toString(),pageRequest,params,OrgUserPageRespQO.class,OrgUserPageRespDTO.class);

        return p;
    }

    @Override
    public ResponseVO<OrgUserDetailDTO> getDetailById(String id) {


        StringBuilder sql = new StringBuilder(orgUserSqlConfig.getQueryDetailByIdSql().getColumns());
        sql.append(orgUserSqlConfig.getQueryDetailByIdSql().getTables());
        sql.append(orgUserSqlConfig.getQueryDetailByIdSql().getCondition());

        StringBuilder sql1 = new StringBuilder(orgUserSqlConfig.getQueryRoleInfoByIdSql().getColumns());
        sql1.append(orgUserSqlConfig.getQueryRoleInfoByIdSql().getTables());
        sql1.append(orgUserSqlConfig.getQueryRoleInfoByIdSql().getCondition());

        Map<String, Object> params = new HashMap<>(1);
        params.put("id",id);


        OrgUserDetailDTO orgUserDetailDTO = this.nativeQuerySingleResult(sql.toString(),params, OrgUserDetailQO.class,OrgUserDetailDTO.class);

        //获取用户角色信息
        List<RoleDTO> roleDTOList = this.nativeQuery(sql1.toString(),params, Role.class,RoleDTO.class);

        orgUserDetailDTO.setRoleList(roleDTOList);

        return ResponseVO.success(orgUserDetailDTO);
    }

    @Override
    public Page<OrgUserRolePageRespDTO> getRoleSetting(PageRequestVO<OrgUserRolePageReqDTO> page) {

        OrgUserRolePageReqDTO condition = page.getCondition();

        //获取该用户的机构信息
        String orgIndex = orgUserRepository.getOrgIndexByUserId(condition.getUserId());

        String[] orgList = orgIndex.split(",");

        String inSql = "";
        int index = 0;
        //判断角色信息变化了没
        for(String orgId:orgList){
            if(StringUtils.isNotBlank(orgId)){
                if(index == 0){
                    inSql = "\'"+orgId+"\'";
                }else{
                    inSql = inSql+",\'"+orgId+"\'";
                }
                index++;
            }
        }

        StringBuilder sql = new StringBuilder(orgUserSqlConfig.getQueryRoleSettingByIdSql().getColumns());
        sql.append(orgUserSqlConfig.getQueryRoleSettingByIdSql().getTables());

        Map<String, Object> params = new HashMap<>(1);
        params.put("orgUserId",condition.getUserId());
        params.put("orgIds",inSql);

        Sort sort = SortUtils.getSort(page.getSort());

        PageRequest pageRequest =  sort == null?PageRequest.of(page.getPage() - 1, page.getSize()):PageRequest.of(page.getPage() - 1, page.getSize(), sort);


        Page<OrgUserRolePageRespDTO> result = this.nativeQueryByPage(sql.toString(),pageRequest,params, OrgUserRolePageRespQO.class,OrgUserRolePageRespDTO.class);

        return result;
    }

    @Override
    public ResponseVO<LoginUserDetailDTO> getLoginUserDetail() {

        //如果用户的机构发生变化，要重新刷新该机构下的所有用户登录信息？

        UserDetailDTO userDetailDTO = SecurityContextUtil.getUser();

        if(userDetailDTO == null){
            ResponseVO.fail();
        }

        LoginUserDetailDTO loginUserDetailDTO = new LoginUserDetailDTO();

        BeanUtils.copyProperties(userDetailDTO,loginUserDetailDTO);

        //获取ordIndex的机构信息

        String[] orgIdList = userDetailDTO.getOrgIndex().split(",");

        String orgs = "";
        int index = 0;

        //判断角色信息变化了没
        for (String orgId : orgIdList) {
            if (StringUtils.isNotBlank(orgId)) {
                if (index == 0) {
                    orgs = "\'" + orgId + "\'";
                } else {
                    orgs = orgs+",\'" + orgId + "\'";
                }
                index++;
            }
        }

        List<Org> orgList = orgRepository.getListByOrgList(orgs);

        if(orgList != null){
            List<OrgDTO> orgDTOList = new ArrayList<>(orgList.size());

            for (Org org:orgList) {
                OrgDTO orgDTO = new OrgDTO();
                BeanUtils.copyProperties(org,orgDTO);
                orgDTOList.add(orgDTO);
            }

            loginUserDetailDTO.setOrgList(orgDTOList);
        }
        return ResponseVO.success(loginUserDetailDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO deleteUser(String id) {

        //先判断该用户是否是主用户
        OrgUserDTO orgUserDTO = this.get(id);

        this.delete(id);

        if(orgUserDTO.getType().intValue() == 1){
            //获取下一个用户，并设为主用户
            String nextId = orgUserRepository.getNextUserByUserId(orgUserDTO.getUserId());

            if(StringUtils.isNotBlank(nextId)){

                UpdateOrgUserTypeDTO updateOrgUserTypeDTO = new UpdateOrgUserTypeDTO();
                updateOrgUserTypeDTO.setId(nextId);
                updateOrgUserTypeDTO.setType(1);
                this.update(updateOrgUserTypeDTO);
            }
        }

        return ResponseVO.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO updateUserType(UpdateOrgUserTypeDTO updateOrgUserTypeDTO) {
        //先判断该用户是否是主用户
        OrgUserDTO orgUserDTO = this.get(updateOrgUserTypeDTO.getId());

        if(updateOrgUserTypeDTO.getType() != null){
            if(updateOrgUserTypeDTO.getType().intValue() != orgUserDTO.getType().intValue()){

                //先判断 原先的是什么
                if(orgUserDTO.getType().intValue() == 1){


                    this.update(updateOrgUserTypeDTO);

                    //获取下一个用户，并设为主用户
                    String nextId = orgUserRepository.getNextUserByUserIdAndNotId(orgUserDTO.getUserId(),updateOrgUserTypeDTO.getId());
                    UpdateOrgUserTypeDTO oldUpdateOrgUserTypeDTO = new UpdateOrgUserTypeDTO();
                    oldUpdateOrgUserTypeDTO.setId(nextId);
                    oldUpdateOrgUserTypeDTO.setType(1);
                    this.update(oldUpdateOrgUserTypeDTO);

                }else{
                    //获取原先的
                    String nextId = orgUserRepository.getNextUserByUserId(orgUserDTO.getUserId());
                    //更改原先的
                    UpdateOrgUserTypeDTO oldUpdateOrgUserTypeDTO = new UpdateOrgUserTypeDTO();
                    oldUpdateOrgUserTypeDTO.setId(nextId);
                    oldUpdateOrgUserTypeDTO.setType(0);
                    this.update(oldUpdateOrgUserTypeDTO);
                    //更改现在的
                    this.update(updateOrgUserTypeDTO);
                }
            }
            return ResponseVO.success();
        }
        return ResponseVO.fail();
    }

    @Override
    public ResponseVO<List<OrgUserDTO>> getAllList(OrgUserDTO orgUserDTO) {

        List<OrgUserDTO> list = this.findAll(orgUserDTO);
        return ResponseVO.success(list);
    }

    @Override
    public ResponseVO<List<OrgUserDTO>> getClientAuthUserList(ClientAuthOrgUserReqDTO clientAuthOrgUserReqDTO) {

        //根据客户端密钥获取客户端信息
        String clientId = clientRepository.getClientIdByClientSecret(clientAuthOrgUserReqDTO.getClientId());

        //  用户、角色、权限，获取用户后，一个一个去获取每个用户的权限进行遍历？   用户  -> 角色   机构  - > 客户端
        //先获取 该账号的 用户信息 orgIdIndex
        StringBuilder sql = new StringBuilder( "select u.id,u.org_id,u.user_id,u.nick_name,u.enabled,u.type,o.org_index from sys_org_user u left join sys_org o on u.org_id = o.id and o.deleted = '0' where u.deleted = '0' ");

        Map<String,Object> params = new HashMap<>(9);
        if(clientAuthOrgUserReqDTO != null){
            if(StringUtils.isNotBlank(clientAuthOrgUserReqDTO.getUserId())){
                sql.append(" AND u.user_id = :userId ");
                params.put("userId",clientAuthOrgUserReqDTO.getUserId());
            }

            if(StringUtils.isNotBlank(clientAuthOrgUserReqDTO.getId())){
                sql.append(" AND u.id = :id ");
                params.put("id",clientAuthOrgUserReqDTO.getId());
            }

            if(StringUtils.isNotBlank(clientAuthOrgUserReqDTO.getOrgId())){
                sql.append(" AND u.org_id = :orgId ");
                params.put("orgId",clientAuthOrgUserReqDTO.getOrgId());
            }

            if(StringUtils.isNotBlank(clientAuthOrgUserReqDTO.getNickName())){
                sql.append(" AND u.nick_name like \'%" + clientAuthOrgUserReqDTO.getNickName()+"%\'");
            }

            if(clientAuthOrgUserReqDTO.getEnabled() != null){
                sql.append(" AND u.enabled = :enabled ");
                params.put("enabled",clientAuthOrgUserReqDTO.getEnabled());
            }
        }
        List<ClientAuthOrgUserDTO> list = this.nativeQuery(sql.toString(),params, ClientAuthOrgUserQO.class, ClientAuthOrgUserDTO.class);

        List<OrgUserDTO> orgUserDTOList = null;

        //遍历判断是否有该客户端权限
        if(list != null){
            orgUserDTOList = new ArrayList<>(list.size());
            for (ClientAuthOrgUserDTO clientAuthOrgUserDTO:list) {
                String[] orgIdList = clientAuthOrgUserDTO.getOrgIndex().split(",");
                String orgs = "";
                int index = 0;
                //判断角色信息变化了没
                for (String orgId : orgIdList) {
                    if (StringUtils.isNotBlank(orgId)) {
                        if (index == 0) {
                            orgs = "\'" + orgId + "\'";
                        } else {
                            orgs = orgs+",\'" + orgId + "\'";
                        }
                        index++;
                    }
                }

                //判断用户是否有该客户端权限
                Integer count =  orgUserRepository.judgeUserHaveClientAuth(clientAuthOrgUserDTO.getId(),orgs,clientId);

                if(count.intValue()>0){
                    OrgUserDTO orgUserDTO = new OrgUserDTO();
                    BeanUtils.copyProperties(clientAuthOrgUserDTO,orgUserDTO);
                    orgUserDTOList.add(orgUserDTO);
                }
            }
        }

        return ResponseVO.success(orgUserDTOList);
    }
}
