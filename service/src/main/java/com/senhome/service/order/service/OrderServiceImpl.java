package com.senhome.service.order.service;

import com.senhome.api.order.api.OrderServiceApi;
import com.senhome.api.order.model.*;
import com.senhome.service.address.business.AddressBusiness;
import com.senhome.service.address.dal.dataobject.Address;
import com.senhome.service.address.dal.dataobject.OrderAddress;
import com.senhome.service.cart.business.CartBusiness;
import com.senhome.service.cart.dal.dataobject.Cart;
import com.senhome.service.goods.business.GoodsBusiness;
import com.senhome.service.goods.business.ShopGoodsBusiness;
import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.service.goods.dal.dataobject.ShopGoods;
import com.senhome.service.order.business.OrderBusiness;
import com.senhome.service.order.dal.dataobject.Order;
import com.senhome.service.order.dal.dataobject.OrderConfirm;
import com.senhome.service.order.dal.dataobject.OrderConfirmGoods;
import com.senhome.service.order.dal.dataobject.OrderGoods;
import com.senhome.service.shop.business.ShopBusiness;
import com.senhome.service.shop.dal.dataobject.Shop;
import com.senhome.shell.common.lang.CommonUtil;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.shell.common.time.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderServiceApi
{
    @Autowired
    private OrderBusiness orderBusiness;

    @Autowired
    private GoodsBusiness goodsBusiness;

    @Autowired
    private ShopGoodsBusiness shopGoodsBusiness;

    @Autowired
    private ShopBusiness shopBusiness;

    @Autowired
    private AddressBusiness addressBusiness;

    @Autowired
    private CartBusiness cartBusiness;

    @Override
    @Transactional
    public ViewResult orderConfirm(List<Integer> cartIds, Integer payPrice, Integer addressId, Integer accountId, Integer shopId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || accountId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }

        //获取用户订单商品
        List<Cart> cartList = cartBusiness.findCartByCartListAndAccountId(cartIds, accountId);
        if(cartList == null || cartList.size() == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("cart goods not exist");
            return viewResult;
        }

        //获取用户地址
        Address address = addressBusiness.findAddressById(addressId);
        if(address == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("confirm address not exist");
            return viewResult;
        }

        //获取订单商品
        List<Integer> goodsIds = cartList.stream().map(Cart::getGoodsId).collect(Collectors.toList());
        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        if(goodsList == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("confirm goods not exist");
            return viewResult;
        }

        Map<Integer, Goods> goodsMap = goodsList.parallelStream().collect(Collectors.toMap(Goods::getId, e -> e));

        //订单总价与输入的总价比价,保证订单一致性
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderGoodsDetailDTO> orderGoodsDetailDTOList = new ArrayList<>();
        for(Cart cart : cartList)
        {
            Goods goods = goodsMap.get(cart.getGoodsId());

            if(goods == null)
            {
                continue;
            }

            OrderGoodsDetailDTO orderGoodsDetail = new OrderGoodsDetailDTO();
            orderGoodsDetail.setCount(cart.getGoodsCount());
            orderGoodsDetail.setGoodsId(cart.getGoodsId());
            orderGoodsDetail.setImage(goods.getImage1());
            orderGoodsDetail.setName(goods.getName());
            orderGoodsDetail.setPrice(BigDecimal.valueOf(goods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());

            totalPrice = (BigDecimal.valueOf(goods.getSalesPrice()).multiply(BigDecimal.valueOf(cart.getGoodsCount()))).add(totalPrice);

            orderGoodsDetailDTOList.add(orderGoodsDetail);
        }

        if(!totalPrice.equals(BigDecimal.valueOf(payPrice)))
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("confirm price not equals");
            return viewResult;
        }

        //生成唯一事务id
        String orderConfirmNumber = CommonUtil.getUUID();
        if(orderConfirmNumber == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("create confirm orderNumber fail");
            return viewResult;
        }

        //创建订单收货地址
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setDetailAddress(address.getDetailAddress());
        orderAddress.setMobileNumber(address.getMobileNumber());
        orderAddress.setName(address.getName());
        orderAddress.setCode(address.getCode());
        Integer orderAddressId = addressBusiness.insertOrderAddress(orderAddress);

        if(orderAddressId <= 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("create address order fail");
            return viewResult;
        }
        orderAddressId = orderAddress.getId();

        //创建订单确认数据
        OrderConfirm orderConfirm = new OrderConfirm();
        orderConfirm.setAccountId(accountId);
        orderConfirm.setNumber(orderConfirmNumber);
        orderConfirm.setReceiveAddressId(orderAddressId);
        orderConfirm.setTotalPrice(payPrice);
        orderConfirm.setShopId(shopId);

        Integer orderConfirmId = orderBusiness.insertOrderConfirm(orderConfirm);

        if(orderConfirmId <= 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("create confirm order fail");
            return viewResult;
        }

        orderConfirmId = orderConfirm.getId();

        for(Cart cart : cartList)
        {
            OrderConfirmGoods orderConfirmGoods = new OrderConfirmGoods();
            orderConfirmGoods.setCount(cart.getGoodsCount());
            orderConfirmGoods.setGoodsId(cart.getGoodsId());
            orderConfirmGoods.setOrderConfirmId(orderConfirmId);
            orderConfirmGoods.setShoppingCartId(cart.getId());

            if(orderBusiness.insertOrderConfirmGoods(orderConfirmGoods) <= 0)
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("create confirm orderGoods fail");
                return viewResult;
            }
        }

        OrderConfirmDTO orderConfirmDTO = new OrderConfirmDTO();
        orderConfirmDTO.setTotalPrice(totalPrice.toString());
        orderConfirmDTO.setAddressDetail(address.getDetailAddress() + address.getCode());
        orderConfirmDTO.setConfirmId(orderConfirmNumber.toString());
        orderConfirmDTO.setMobileNumber(address.getMobileNumber());
        orderConfirmDTO.setOrderGoods(orderGoodsDetailDTOList);

        return viewResult.putDefaultModel(orderConfirmDTO);
    }

    @Override
    @Transactional
    public ViewResult orderAdd(String confirmId, Integer payPrice, Integer accountId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || accountId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }

        //获取确认订单相关内容
        OrderConfirm orderConfirm = orderBusiness.findOrderConfirmByNumber(confirmId);
        if(orderConfirm == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("order not exist");
            return viewResult;
        }

        List<OrderConfirmGoods> orderConfirmGoodsList = orderBusiness.getOrderConfirmGoodsList(orderConfirm.getId());
        if(orderConfirmGoodsList == null || orderConfirmGoodsList.size() == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("order not exist");
            return viewResult;
        }

        //获取、创建订单商品, 判断商品是否有货
        List<Integer> goodsIds = orderConfirmGoodsList.stream().map(OrderConfirmGoods::getGoodsId).collect(Collectors.toList());
        List<ShopGoods> shopGoodsList = shopGoodsBusiness.findShopGoodsListByIdsForUpdate(goodsIds, orderConfirm.getShopId());

        Map<Integer, ShopGoods> idShopGoodsMap = shopGoodsList.stream().collect(Collectors.toMap(ShopGoods::getGoodsId, x -> x, (t, u) -> u));
        for(OrderConfirmGoods orderConfirmGoods : orderConfirmGoodsList)
        {
            ShopGoods shopGoods = idShopGoodsMap.get(orderConfirmGoods.getGoodsId());
            if(shopGoods.getStock() < orderConfirmGoods.getCount())
            {
                Goods goods = goodsBusiness.findGoodsById(orderConfirmGoods.getGoodsId());

                viewResult.setSuccess(false);
                viewResult.setMessage(goods.getName() + " sale out");
                return viewResult;
            }
        }

        Order order = new Order();
        order.setAccountId(accountId);
        order.setReceiveAddressId(orderConfirm.getReceiveAddressId());
        order.setTotalPrice(payPrice);
        order.setShopId(orderConfirm.getShopId());
        order.setType(Byte.valueOf("1"));
        order.setPayChannel(Byte.valueOf("1"));

        Integer orderId = orderBusiness.insertOrder(order);

        if(orderId <= 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("create order fail");
            return viewResult;
        }

        orderId = order.getId();

        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        if(goodsList == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("goods not exist");
            return viewResult;
        }

        Map<Integer, Goods> goodsMap = goodsList.parallelStream().collect(Collectors.toMap(Goods::getId, e -> e));

        List<Integer> cartList = new ArrayList<>();

        for(OrderConfirmGoods orderConfirmGoods : orderConfirmGoodsList)
        {
            Goods goods = goodsMap.get(orderConfirmGoods.getGoodsId());

            if(goods == null)
            {
                continue;
            }

            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setCount(orderConfirmGoods.getCount());
            orderGoods.setGoodsId(orderConfirmGoods.getGoodsId());
            orderGoods.setOrderId(orderId);
            orderGoods.setSalesPrice(goods.getSalesPrice());

            cartList.add(orderConfirmGoods.getShoppingCartId());

            if(orderBusiness.insertOrderGoods(orderGoods) <= 0)
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("create orderGoods fail");
                return viewResult;
            }
        }

        //删除购物车商品
        cartBusiness.deleteCartByIds(cartList);

        OrderAddDTO orderAdd = new OrderAddDTO();
        orderAdd.setOrderId(orderId);

        return viewResult.putDefaultModel(orderAdd);
    }

    @Override
    @Transactional
    public ViewResult orderPay(Integer orderId, Byte channel, Integer payPrice, Integer accountId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || accountId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }

        Order order = orderBusiness.findOrderById(orderId);
        order.setType(Byte.valueOf("2"));
        order.setPayTime(DateUtil.now());
        order.setPayChannel(Byte.valueOf("1"));
        orderBusiness.updateOrder(order);

        //减商家库存
        List<OrderGoods> orderGoodsList = orderBusiness.findOrderGoodsByOrderId(orderId);

        List<Integer> goodsIds = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
        List<ShopGoods> shopGoodsList = shopGoodsBusiness.findShopGoodsListByIdsForUpdate(goodsIds, order.getShopId());

        Map<Integer, ShopGoods> idShopGoodsMap = shopGoodsList.stream().collect(Collectors.toMap(ShopGoods::getGoodsId, x -> x, (t, u) -> u));
        for(OrderGoods orderGoods : orderGoodsList)
        {
            ShopGoods shopGoods = idShopGoodsMap.get(orderGoods.getGoodsId());
            if(shopGoods == null)
            {
                continue;
            }

            shopGoods.setLock(shopGoods.getLock() + orderGoods.getCount());
            shopGoods.setStock(shopGoods.getStock() - orderGoods.getCount());
            shopGoodsBusiness.updateShopGoods(shopGoods);
        }

        OrderPayDTO orderPay = new OrderPayDTO();
        orderPay.setPayStr("success");

        return viewResult.putDefaultModel(orderPay);
    }

    @Override
    public ViewResult orderDetail(Integer orderId, Boolean isShop)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Order order = orderBusiness.findOrderById(orderId);
        if(order == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("orderDetail not exist");
            return viewResult;
        }

        List<OrderGoods> orderGoodsList = orderBusiness.findOrderGoodsByOrderId(orderId);

        //获取、创建订单商品
        List<Integer> goodsIds = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        if(goodsList == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("goods not exist");
            return viewResult;
        }

        Map<Integer, Goods> goodsMap = goodsList.parallelStream().collect(Collectors.toMap(Goods::getId, e -> e));

        List<OrderGoodsDetailDTO> orderGoodsDetailDTOList = new ArrayList<>();
        Integer income = 0;
        for(OrderGoods orderGoods : orderGoodsList)
        {
            Goods goods = goodsMap.get(orderGoods.getGoodsId());

            if(goods == null)
            {
                continue;
            }

            OrderGoodsDetailDTO orderGoodsDetail = new OrderGoodsDetailDTO();
            orderGoodsDetail.setCount(orderGoods.getCount());
            orderGoodsDetail.setGoodsId(orderGoods.getGoodsId());
            orderGoodsDetail.setImage(goods.getImage1());
            orderGoodsDetail.setName(goods.getName());
            orderGoodsDetail.setPrice(BigDecimal.valueOf(orderGoods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            if(isShop)
            {
                Integer orderGoodsIncome = Integer.valueOf(BigDecimal.valueOf(goods.getIncome())
                    .multiply(BigDecimal.valueOf(orderGoods.getCount()))
                    .toString());
                orderGoodsDetail.setIncome(BigDecimal.valueOf(orderGoodsIncome).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
                income += orderGoodsIncome;
            }

            orderGoodsDetailDTOList.add(orderGoodsDetail);
        }

        //获取用户地址
        OrderAddress address = addressBusiness.findOrderAddressById(order.getReceiveAddressId());
        if(address == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("confirm address not exist");
            return viewResult;
        }

        OrderDetailDTO orderDetail = new OrderDetailDTO();
        orderDetail.setAddressDetail(address.getDetailAddress());
        orderDetail.setCode(address.getCode());
        orderDetail.setIsPay(order.getPayTime() == null ? 0 : 1);
        orderDetail.setOrderGoods(orderGoodsDetailDTOList);
        orderDetail.setPayPrice(BigDecimal.valueOf(order.getTotalPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
        orderDetail.setType(order.getType());
        if(isShop)
        {
            orderDetail.setIncome(BigDecimal.valueOf(income).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
        }

        return viewResult.putDefaultModel(orderDetail);
    }

    @Override
    @Transactional
    public ViewResult updateOrder(Integer orderId, Integer type)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(orderId == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("order not exist");
            return viewResult;
        }

        Order order = new Order();
        order.setId(orderId);
        order.setType(Byte.valueOf(type.toString()));

        //商品状态改为待配送,去掉锁定的库存
        if(type == 3)
        {
            //减商家库存
            List<OrderGoods> orderGoodsList = orderBusiness.findOrderGoodsByOrderId(orderId);

            List<Integer> goodsIds = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
            Order shopOrder = orderBusiness.findOrderById(orderId);
            List<ShopGoods> shopGoodsList = shopGoodsBusiness.findShopGoodsListByIdsForUpdate(goodsIds, shopOrder.getShopId());

            Map<Integer, ShopGoods> idShopGoodsMap = shopGoodsList.stream().collect(Collectors.toMap(ShopGoods::getGoodsId, x -> x, (t, u) -> u));
            for(OrderGoods orderGoods : orderGoodsList)
            {
                ShopGoods shopGoods = idShopGoodsMap.get(orderGoods.getGoodsId());
                if(shopGoods == null)
                {
                    continue;
                }

                Integer lock = shopGoods.getLock() - orderGoods.getCount();
                if(lock < 0)
                {
                    lock = 0;
                }

                shopGoods.setLock(lock);
                shopGoodsBusiness.updateShopGoods(shopGoods);
            }
        }

        if(orderBusiness.updateOrder(order) <= 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("update order fail");
            return viewResult;
        }

        return viewResult;
    }

    @Override
    public ViewResult orderList(Byte type, Integer page, Integer pageCount, Integer accountId, Boolean isShop)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(accountId == null || accountId == 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }

        //获取订单列表
        List<Order> orderList = null;
        if(isShop)
        {
            orderList = orderBusiness.findOrderByShopIdAndType(accountId, page, pageCount, type);
        }
        else
        {
            orderList = orderBusiness.findOrderByAccountIdAndType(accountId, page, pageCount, type);
        }

        if(orderList == null || orderList.size() == 0)
        {
            return viewResult;
        }

        List<Integer> orderIds = orderList.stream().map(Order::getId).collect(Collectors.toList());

        //获取订单商品列表
        List<OrderGoods> orderGoodsList = orderBusiness.findOrderGoodsByOrderId(orderIds);
        Map<Integer, List<OrderGoods>> idGoodsMap = orderGoodsList.stream().collect(Collectors.groupingBy(OrderGoods::getOrderId));

        //获取店铺信息
        List<Integer> shopIds = orderList.stream().map(Order::getShopId).collect(Collectors.toList());

        List<Shop> shopList = shopBusiness.findShopsListByIds(shopIds);
        Map<Integer, Shop> idShopMap = shopList.parallelStream().collect(Collectors.toMap(Shop::getId, e -> e));

        //获取、创建订单商品
        List<Integer> goodsIds = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        if(goodsList == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("goods not exist");
            return viewResult;
        }

        Map<Integer, Goods> goodsMap = goodsList.parallelStream().collect(Collectors.toMap(Goods::getId, e -> e));

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        //获取收货地址列表
        List<Integer> addressIds = orderList.stream().map(Order::getReceiveAddressId).collect(Collectors.toList());
        List<OrderAddress> orderAddressList = addressBusiness.findOrderAddressByIds(addressIds);
        Map<Integer, OrderAddress> orderAddressMap = orderAddressList.parallelStream().collect(Collectors.toMap(OrderAddress::getId, e -> e));

        for(Order order : orderList)
        {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setPayPrice(BigDecimal.valueOf(order.getTotalPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            orderDetailDTO.setIsPay(order.getPayTime() == null ? 0 : 1);
            orderDetailDTO.setOrderId(order.getId());
            orderDetailDTO.setType(order.getType());
            orderDetailDTO.setCreateTime(DateUtil.formatString(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

            Shop shop = idShopMap.get(order.getShopId());
            orderDetailDTO.setShopImage(shop.getHead());
            orderDetailDTO.setShopName(shop.getName());

            OrderAddress orderAddress = orderAddressMap.get(order.getReceiveAddressId());
            orderDetailDTO.setAddressDetail(orderAddress.getDetailAddress());

            List<OrderGoodsDetailDTO> goodsDetailDTOList = new ArrayList<>();

            List<OrderGoods> orderGoods = idGoodsMap.get(order.getId());
            Integer income = 0;
            if(orderGoods == null || orderGoods.size() == 0)
            {
                orderDetailDTO.setOrderGoods(goodsDetailDTOList);
            }
            else
            {
                for (OrderGoods goods : orderGoods)
                {
                    Goods goodsDetail = goodsMap.get(goods.getGoodsId());

                    if (goodsDetail == null)
                    {
                        continue;
                    }

                    OrderGoodsDetailDTO orderGoodsDetail = new OrderGoodsDetailDTO();
                    orderGoodsDetail.setCount(goods.getCount());
                    orderGoodsDetail.setGoodsId(goods.getGoodsId());
                    orderGoodsDetail.setImage(goodsDetail.getImage1());
                    orderGoodsDetail.setName(goodsDetail.getName());
                    orderGoodsDetail.setPrice(BigDecimal.valueOf(goods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
                    if(isShop)
                    {
                        Integer orderGoodsIncome = Integer.valueOf(BigDecimal.valueOf(goodsDetail.getIncome())
                            .multiply(BigDecimal.valueOf(goods.getCount()))
                            .toString());
                        orderGoodsDetail.setIncome(BigDecimal.valueOf(orderGoodsIncome).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
                        income += orderGoodsIncome;
                    }

                    goodsDetailDTOList.add(orderGoodsDetail);
                }

                orderDetailDTO.setOrderGoods(goodsDetailDTOList);
            }

            if(isShop)
            {
                orderDetailDTO.setIncome(BigDecimal.valueOf(income).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            }

            orderDetailDTOList.add(orderDetailDTO);
        }

        OrderListDTO orderListDTO = new OrderListDTO();
        orderListDTO.setOrderList(orderDetailDTOList);

        return viewResult.putDefaultModel(orderListDTO);
    }
}
