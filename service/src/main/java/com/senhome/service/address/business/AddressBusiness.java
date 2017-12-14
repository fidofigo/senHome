package com.senhome.service.address.business;

import com.senhome.service.address.dal.dataobject.Address;
import com.senhome.service.address.dal.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBusiness
{
    @Autowired
    private AddressMapper addressMapper;

    public Address findAddressById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return addressMapper.findById(id);
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

    public Integer updateAddress(Address address)
    {
        if(address == null)
        {
            return 0;
        }

        return addressMapper.updateAddress(address);
    }

}
