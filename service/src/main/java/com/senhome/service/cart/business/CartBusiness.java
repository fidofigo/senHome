package com.senhome.service.cart.business;

import com.senhome.service.cart.dal.dataobject.Cart;
import com.senhome.service.cart.dal.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CartBusiness
{
    @Autowired
    private CartMapper cartMapper;

    public List<Cart> findCartByAccountId(Integer accountId, Integer shopId)
    {
        if(accountId == null)
        {
            return null;
        }

        return cartMapper.findCartByAccountId(accountId, shopId);
    }

    public List<Cart> findCartByCartListAndAccountId(List<Integer> ids, Integer accountId)
    {
        if(accountId == null || CollectionUtils.isEmpty(ids))
        {
            return null;
        }

        return cartMapper.findCartByCartListAndAccountId(ids, accountId);
    }

    public Cart findByCartId(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return cartMapper.findById(id);
    }

    public Cart findByAccountIdAndGoodsId(Integer accountId, Integer goodsId, Integer shopId)
    {
        if(accountId == null || goodsId == null)
        {
            return null;
        }

        return cartMapper.findByAccountIdAndGoodsId(accountId, goodsId, shopId);
    }

    public Integer findCountByAccountId(Integer accountId, Integer shopId)
    {
        if(accountId == null || accountId == 0)
        {
            return 0;
        }

        return cartMapper.findCountByAccountId(accountId, shopId);
    }

    public Integer insertCart(Cart cart)
    {
        if(cart == null)
        {
            return null;
        }

        return cartMapper.insertCart(cart);
    }

    public Integer updateCart(Cart cart)
    {
        if(cart == null)
        {
            return null;
        }

        return cartMapper.updateCart(cart);
    }

    public Integer deleteCartByIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return null;
        }

        return cartMapper.deleteCartByIds(ids);
    }
}
