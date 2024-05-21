package com.lyc.simple.utils;

import lombok.Data;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/4/23 14:42
 * @Description:
 */
@Data
public class DeleteWapper {

    private String table;

    List<PredicateItem> predicates;
}
