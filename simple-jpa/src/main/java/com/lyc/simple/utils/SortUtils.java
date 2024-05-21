package com.lyc.simple.utils;

import com.lyc.common.vo.OrderVO;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/16 13:00
 * @Description:
 */
public class SortUtils {

    public static Sort getSort(List<OrderVO> orderVOS,String table){
        List<Sort.Order> orders=null;
        if(orderVOS !=null){
            orders = new ArrayList<>(orderVOS.size());

            for (OrderVO orderVO:orderVOS) {
                if(StringUtils.isNotBlank(orderVO.getProperty())){

                    String direction = orderVO.getDirection().toUpperCase();
                    if("DESC".equals(direction)){
                        if(StringUtils.isNotBlank(table)){
                            Sort.Order order = new Sort.Order(Sort.Direction.DESC, table+'.'+orderVO.getProperty());
                            orders.add(order);
                        }else{
                            Sort.Order order = new Sort.Order(Sort.Direction.DESC, orderVO.getProperty());
                            orders.add(order);
                        }
                    }else if("ASC".equals(direction)){
                        if(StringUtils.isNotBlank(table)){
                            Sort.Order order = new Sort.Order(Sort.Direction.ASC, table+'.'+orderVO.getProperty());
                            orders.add(order);
                        }else{
                            Sort.Order order = new Sort.Order(Sort.Direction.ASC, orderVO.getProperty());
                            orders.add(order);
                        }
                    }
                }
            }

            return Sort.by(orders);
        }else{
            return null;
        }
    }

    public static Sort getSort(List<OrderVO> orderVOS){
        return getSort(orderVOS,null);
    }
}
