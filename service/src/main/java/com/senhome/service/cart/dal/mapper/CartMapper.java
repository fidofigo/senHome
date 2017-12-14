package com.senhome.service.cart.dal.mapper;

import com.senhome.service.cart.dal.dataobject.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper
{
    /**
     * 活动用户购物车列表
     * @param accountId
     * @return
     */
    List<Cart> findCartByAccountId(Integer accountId);

    /**
     * 获取用户购物车商品
     * @param ids
     * @param accountId
     * @return
     */
    List<Cart> findCartByCartListAndAccountId(@Param("ids") List<Integer> ids, @Param("accountId") Integer accountId);

    /**
     * 获取用户购物车商品数量
     * @param accountId
     * @return
     */
    int findCountByAccountId(Integer accountId);

    /**
     * 获取购物车商品
     * @param id
     * @return
     */
    Cart findById(Integer id);

    /**
     * 获取购物车商品
     * @param accountId
     * @param goodsId
     * @return
     */
    Cart findByAccountIdAndGoodsId(@Param("accountId") Integer accountId, @Param("goodsId") Integer goodsId);

    /**
     * 新增购物车商品
     * @param cart
     * @return
     */
    int insertCart(Cart cart);

    /**
     * 更新购物车商品
     * @param cart
     * @return
     */
    int updateCart(Cart cart);

    /**
     * 批量删除购物车商品
     * @param ids
     * @return
     */
    int deleteCartByIds(List<Integer> ids);
}
