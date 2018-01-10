package com.senhome.service.cart.service;

import com.senhome.api.cart.api.CartApi;
import com.senhome.api.cart.model.CartDTO;
import com.senhome.api.cart.model.CartGoodsDTO;
import com.senhome.api.cart.model.CartGoodsDetailDTO;
import com.senhome.service.cart.business.CartBusiness;
import com.senhome.service.cart.dal.dataobject.Cart;
import com.senhome.service.goods.business.GoodsBusiness;
import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartApi
{
    @Autowired
    private CartBusiness cartBusiness;

    @Autowired
    private GoodsBusiness goodsBusiness;

    @Override
    public ViewResult cartCount(Integer accountId, Integer shopId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        int cartCount = cartBusiness.findCountByAccountId(accountId, shopId);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartCount(cartCount);

        return viewResult.putDefaultModel(cartDTO);
    }

    @Override
    public ViewResult addCartGoods(Integer accountId, Integer goodsId, Integer goodsCount, Integer shopId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || accountId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }

        //获取该商品是否加购
        Cart cart = cartBusiness.findByAccountIdAndGoodsId(accountId, goodsId, shopId);
        if(cart == null)
        {
            cart = new Cart();
            cart.setAccountId(accountId);
            cart.setGoodsId(goodsId);
            cart.setGoodsCount(goodsCount);
            if(cartBusiness.insertCart(cart) <= 0)
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("add cart fail");
                return viewResult;
            }
        }
        else
        {
            cart.setGoodsCount(cart.getGoodsCount() + goodsCount);
            if(cartBusiness.updateCart(cart) <= 0)
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("add cart fail");
                return viewResult;
            }
        }

        int cartCount = cartBusiness.findCountByAccountId(accountId, shopId);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartCount(cartCount);

        return viewResult.putDefaultModel(cartDTO);
    }

    @Override
    public ViewResult editCartGoods(Integer cartId, Integer modifyCount)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Cart cart = cartBusiness.findByCartId(cartId);

        if(cart == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("edit cart fail");
            return viewResult;
        }

        cart.setGoodsCount(modifyCount);

        if(cartBusiness.updateCart(cart) > 0)
        {
            return viewResult;
        }
        else
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("edit cart fail");
            return viewResult;
        }
    }

    @Override
    public ViewResult deleteCartGoods(List<Integer> cartIds)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(cartBusiness.deleteCartByIds(cartIds) > 0)
        {
            return viewResult;
        }
        else
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("delete cart fail");
            return viewResult;
        }
    }

    @Override
    public ViewResult cartGoodsList(Integer accountId, Integer shopId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || accountId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }

        List<Cart> cartList = cartBusiness.findCartByAccountId(accountId, shopId);

        CartGoodsDTO cartGoodsDTO = new CartGoodsDTO();
        List<CartGoodsDetailDTO> cartGoodsDetailDTOList = new ArrayList<>();

        if(cartList == null || cartList.size() == 0)
        {
            cartGoodsDTO.setCartGoods(cartGoodsDetailDTOList);
            return viewResult.putDefaultModel(cartGoodsDTO);
        }

        List<Integer> goodsIds = cartList.stream().map(Cart::getGoodsId).collect(Collectors.toList());

        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        if(goodsList == null || goodsList.size() == 0)
        {
            cartGoodsDTO.setCartGoods(cartGoodsDetailDTOList);
            return viewResult.putDefaultModel(cartGoodsDTO);
        }

        Map<Integer, Goods> goodsMap = goodsList.parallelStream().collect(Collectors.toMap(Goods::getId, e -> e));

        for(Cart cart : cartList)
        {
            Goods goods = goodsMap.get(cart.getGoodsId());

            if(goods == null)
            {
                continue;
            }

            CartGoodsDetailDTO cartGoodDetail = new CartGoodsDetailDTO();
            cartGoodDetail.setCartId(cart.getId());
            cartGoodDetail.setCount(cart.getGoodsCount());
            cartGoodDetail.setGoodsId(cart.getGoodsId());
            cartGoodDetail.setImage(goods.getImage1());
            cartGoodDetail.setName(goods.getName());
            cartGoodDetail.setLimit(goods.getLimit());
            cartGoodDetail.setPrice(BigDecimal.valueOf(goods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());

            cartGoodsDetailDTOList.add(cartGoodDetail);
        }

        cartGoodsDTO.setCartGoods(cartGoodsDetailDTOList);
        return viewResult.putDefaultModel(cartGoodsDTO);
    }
}
