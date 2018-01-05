package com.senhome.service.address.service;

import com.senhome.api.address.api.AddressServiceApi;
import com.senhome.api.address.model.AddressDTO;
import com.senhome.api.address.model.AddressIdDTO;
import com.senhome.api.address.model.AddressListDTO;
import com.senhome.service.account.service.AccountServiceImpl;
import com.senhome.service.address.business.AddressBusiness;
import com.senhome.service.address.dal.dataobject.Address;
import com.senhome.shell.common.result.ViewResult;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("addressServiceApi")
public class AddressServiceImpl implements AddressServiceApi
{
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AddressBusiness addressBusiness;

    @Override
    public ViewResult addressDetail(Integer addressId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(addressId == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("address not exist");
            return viewResult;
        }

        Mapper mapper = new DozerBeanMapper();

        Address address = addressBusiness.findAddressById(addressId);

        AddressDTO view = mapper.map(address, AddressDTO.class);

        return viewResult.putDefaultModel(view);
    }

    @Override
    public ViewResult deleteAddress(Integer addressId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(addressId == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("address not exist");
            return viewResult;
        }

        if(addressBusiness.deleteAddress(addressId) > 0)
        {
            return viewResult;
        }
        else
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("delete address fail");
            return viewResult;
        }
    }

    @Override
    public ViewResult changeAddress(Integer accountId, String detailAddress, String phone, String name, Integer isDefault, Integer addressId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || detailAddress == null || phone == null || name == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("address change fail");
            return viewResult;
        }

        if(isDefault == null || isDefault != 1)
        {
            isDefault = 0;
        }

        if(isDefault == 1)
        {
            List<Address> addressList = addressBusiness.findAccountIdAddress(accountId);
            if(addressList != null && addressList.size() > 0)
            {
                addressBusiness.updateAddressDefault(accountId);
            }
        }

        Address address = new Address();
        address.setAccountId(accountId);
        address.setDetailAddress(detailAddress);
        address.setMobileNumber(phone);
        address.setIsDefault(Byte.valueOf(isDefault.toString()));
        address.setName(name);

        if(addressId > 0)
        {
            address.setId(addressId);
            addressBusiness.updateAddress(address);
        }
        else
        {
            addressId = addressBusiness.insertAddress(address);
        }

        if(addressId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("address change fail");
            return viewResult;
        }

        AddressIdDTO addressIdDTO = new AddressIdDTO();
        addressIdDTO.setAddressId(addressId);

        return viewResult.putDefaultModel(addressIdDTO);
    }

    @Override
    public ViewResult addressList(Integer accountId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("address not exist");
            return viewResult;
        }

        List<Address> addressList = addressBusiness.findAccountIdAddress(accountId);

        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(Address address : addressList)
        {
            Mapper mapper = new DozerBeanMapper();
            AddressDTO view = mapper.map(address, AddressDTO.class);

            addressDTOList.add(view);
        }

        AddressListDTO addressListDTO = new AddressListDTO();
        addressListDTO.setAddressList(addressDTOList);

        return viewResult.putDefaultModel(addressListDTO);
    }
}
