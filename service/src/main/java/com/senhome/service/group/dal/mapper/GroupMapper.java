package com.senhome.service.group.dal.mapper;

import com.senhome.service.group.dal.dataobject.Group;

import java.util.List;
import java.util.Map;

/**
 * Created by fidofigo on 17/4/4.
 */
public interface GroupMapper {
    /**
     * 通过id获取组合信息
     * @param id
     * @return
     */
    Group findById(Integer id);

    /**
     * 批量获取组合详情列表
     * @param ids
     * @return
     */
    List<Group> findGroupByIds(List<Integer> ids);

    /**
     * 获取组合
     * @param groupIds
     * @return
     */
    List<Map<String, Object>> findProductIdsGroupIdsByGroupIds(List<Integer> groupIds);

    /**
     * 获取组合对应的商品id
     * @param groupIds
     * @return
     */
    List<Integer> findProductIdsByGroupIds(List<Integer> groupIds);
}
