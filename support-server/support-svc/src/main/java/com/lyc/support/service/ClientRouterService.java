package com.lyc.support.service;

import com.lyc.common.vo.PageRequestVO;
import com.lyc.common.vo.ResponseVO;
import com.lyc.simple.jpa.BaseService;
import com.lyc.support.dto.*;
import com.lyc.support.entity.ClientRouter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/23 18:28
 * @Description:
 */
public interface ClientRouterService extends BaseService<ClientRouterDTO, ClientRouter,String> {
    ResponseVO<RouterInfoDTO> saveRouterInfo(RouterInfoDTO routerInfoDTO);

    ResponseVO<RouterInfoDTO> updateRouterInfo(RouterInfoDTO routerInfoDTO);

    Page<RouterInfoPageRespDTO> getPageList(PageRequestVO<RouterInfoReqDTO> page);

    List<FunctionInfoDTO> getFunctionList(FunctionInfoDTO functionInfoDTO);

    ResponseVO<FunctionInfoDTO> saveFunctionInfo(FunctionInfoDTO functionInfoDTO);

    ResponseVO<FunctionInfoDTO> updateFunctionInfo(FunctionInfoDTO functionInfoDTO);

    void deleteRouter(String id);

    List<UserClientRouterDTO> getByClientSecret(ClientRouterReqDTO clientRouterReqDTO);
}
