package com.senhome.service.address.business;

import com.senhome.service.address.dal.dataobject.Address;
import com.senhome.service.address.dal.dataobject.OrderAddress;
import com.senhome.service.address.dal.mapper.AddressMapper;
import com.senhome.service.address.dal.mapper.OrderAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class AddressBusiness
{
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private OrderAddressMapper orderAddressMapper;

    public Address findAddressById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return addressMapper.findById(id);
    }

    public OrderAddress findOrderAddressById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return orderAddressMapper.findById(id);
    }

    public List<OrderAddress> findOrderAddressByIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return Collections.emptyList();
        }

        return orderAddressMapper.findOrderAddressByIds(ids);
    }

    public Address findDefaultAddress(Integer accountId)
    {
        if(accountId == null)
        {
            return null;
        }

        return addressMapper.findDefaultAddress(accountId);
    }

    public List<Address> findAccountIdAddress(Integer accountId)
    {
        if(accountId == null)
        {
            return null;
        }

        return addressMapper.findByAccountId(accountId);
    }

    public Integer insertAddress(Address address)
    {
        if(address == null)
        {
            return 0;
        }

        return addressMapper.insertAddress(address);
    }

    public Integer updateAddressDefault(Integer accountId)
    {
        if(accountId == null)
        {
            return null;
        }

        return addressMapper.updateAddressDefault(accountId);
    }

    public Integer insertOrderAddress(OrderAddress orderAddress)
    {
        if(orderAddress == null)
        {
            return 0;
        }

        return orderAddressMapper.insertOrderAddress(orderAddress);
    }

    public Integer updateAddress(Address address)
    {
        if(address == null)
        {
            return 0;
        }

        return addressMapper.updateAddress(address);
    }

    public Integer deleteAddress(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return addressMapper.deleteAddress(id);
    }

}
