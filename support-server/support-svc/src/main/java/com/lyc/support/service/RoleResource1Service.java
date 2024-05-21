package com.lyc.support.service;

import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.*;
import com.lyc.support.entity.RoleResource;
import com.lyc.support.entity.RoleResource1;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 15:19
 * @Description:
 */
public interface RoleResource1Service extends BaseService<RoleResourceDTO, RoleResource1,String> {
    ResponseVO<List<RoleClientRespDTO>> getClientList(RoleClientReqDTO roleClientReqDTO);

    ResponseVO<List<RoleMenuRespDTO>> getMenuList(RoleResourceReqDTO roleResourceReqDTO);

    ResponseVO<List<RoleRouterRespDTO>> getRouterList(RoleResourceReqDTO roleResourceReqDTO);

    ResponseVO<List<RoleFunctionRespDTO>> getFunctionList(RoleFunctionReqDTO roleFunctionReqDTO);

    ResponseVO openClientAuth(RoleResourceOpenDTO roleResourceOpenDTO);

    ResponseVO closeClientAuth(String id);

    ResponseVO openMenuAuth(RoleResourceOpenDTO roleResourceOpenDTO);

    ResponseVO closeMenuAuth(String id);

    ResponseVO openRouterAuth(RoleResourceOpenDTO roleResourceOpenDTO);

    ResponseVO closeRouterAuth(String id);

    ResponseVO openFunctionAuth(RoleResourceOpenDTO roleResourceOpenDTO);

    ResponseVO closeFunctionAuth(String id);

    ResponseVO saveMenuAuth(RoleMenuResourcesDTO roleMenuResourcesDTO);
}
