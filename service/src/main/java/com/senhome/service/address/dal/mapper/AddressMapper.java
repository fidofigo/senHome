package com.senhome.service.address.dal.mapper;

import com.senhome.service.address.dal.dataobject.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper
{
    /**
     * 查询收货地址详情
     * @param id
     * @return
     */
    Address findById(Integer id);

    /**
     * 查询默认收货地址详情
     * @param accountId
     * @return
     */
    Address findDefaultAddress(Integer accountId);

    /**
     * 查询用户收货地址列表
     * @param accountId
     * @return
     */
    List<Address> findByAccountId(Integer accountId);

    /**
     * 新增收货地址
     * @param address
     * @return
     */
    int insertAddress(Address address);

    /**
     * 更新收货地址
     * @param address
     * @return
     */
    int updateAddress(Address address);

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    int deleteAddress(Integer id);

    /**
     * 更新默认地址
     * @param accountId
     * @return
     */
    int updateAddressDefault(Integer accountId);
}
