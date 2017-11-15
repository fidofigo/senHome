package com.senhome.service.group.business;

import com.senhome.service.group.dal.dataobject.Group;
import com.senhome.service.group.dal.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class GroupBusiness {

    @Autowired
    private GroupMapper groupMapper;

    public Group getGroup(Integer id)
    {
        if (id == null)
        {
            return null;
        }

        return groupMapper.findById(id);
    }

    public List<Group> getGroupListByIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return Collections.emptyList();
        }

        return groupMapper.findGroupByIds(ids);
    }

    public List<Integer> getProductIdsByGroupId(List<Integer> groupIds)
    {
        if(CollectionUtils.isEmpty(groupIds))
        {
            return Collections.emptyList();
        }

        return groupMapper.findProductIdsByGroupIds(groupIds);
    }

    public Map<Integer, List<Integer>> findProductIdsGroupIdsByGroupIds(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids))
        {
            return new HashMap<>();
        }

        List<Map<String, Object>> lines = groupMapper.findProductIdsGroupIdsByGroupIds(ids);
        Map<Integer, List<Integer>> retMap = new HashMap<>();
        for(Map<String, Object> line : lines)
        {
            Integer groupId = Integer.valueOf(line.get("groupId") + "");
            if(!retMap.containsKey(groupId))
            {
                retMap.put(groupId, new ArrayList<>());
            }
            retMap.get(groupId).add(Integer.valueOf(line.get("productId") + ""));
        }
        return retMap;
    }
}
