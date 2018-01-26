# box文档V1.0

标签（空格分隔）： box_api

---
[TOC]

---

## 一．说明

## 二．业务

###  首页管理
   
#### `首页详情`(url:/home/detail)
* `/home/detail`

    上行：
     
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |

    下行：
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |status| str|0[失败]、1[成功]|    
    |msg|str|提示信息|
    |bannerList|[[BannerComplex ](#BannerComplex )...]|banner列表|
    |categoryList|[[CategoryComplex ](#CategoryComplex )...]|类目列表|

#### `首页商品列表`(url:/home/productList)
* `/home/productList`

    上行： 
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |type|str|商品类型|
    |page|str|页数,默认1|
    |pageCount|str|每页返回数量,默认20|
    
    下行：
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |status| str|0[失败]、1[成功]|    
    |msg|str|提示信息|
    |total|str|符合条件的商品总数|
    |productList|[[HomeProductComplex ](#HomeProductComplex )...]|商品列表|

###  商品管理
   
####  `商品详情`(url:/product/detail)
* `/product/detail`

    上行： 
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |id|str|商品id|

    下行：
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |status| str|0[失败]、1[成功]|    
    |msg|str|提示信息|
    |imageList|[str...]|商品图片列表|
    |product|str|商品详情|
    |imageDetailList|[str...]|商品详情图片列表|

### 订单管理

#### 付款(url:/order/pay)
* `/order/pay`
           
    上行： 

    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
	|orderProductList|[[OrderProductComplex ](#OrderProductComplex )...]|订单商品列表|
    |payPrice|str|支付金额|
    |phone|str|手机号|
    |code|str|编码|
    |address|str|地址|

    下行：
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |status|str|0[失败]、1[成功]|
    |msg|str|提示信息|
    
###  用户管理
   
#### `用户注册`(url:/accountLogin/register)
* `/accountLogin/register`

    上行：
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |email|str|邮箱地址|
    |code|str|密码|
    |type|str|类型 1:注册, 2:登陆|

    下行：
    
    | 参数名称  | 类型|备注 |
    | ------------- |:------------------:|----:   |
    |status| str|0[失败]、1[成功]|    
    |msg|str|提示信息|
    |accountId|str|用户id|
   
## 三．复杂类型定义

<span id = "HomeProductComplex">HomeProductComplex</span>
```json
{
    id:str(商品id)
    image:str(商品主图),
	name:str(商品名称),
	marketPrice:str(商品原价),
    salesPrice:str(商品售价)
}
```
<span id = "ProductComplex">ProductComplex</span>
```json
{
    id:str(商品id)
	name:str(商品名称),
	marketPrice:str(商品原价),
    salesPrice:str(商品售价),
    brand:str(品牌),
    country:str(产地),
    desc:str(描述),
    limit:str(加入购物车限制),
}
```
<span id = "OrderProductComplex">OrderProductComplex</span>
```json
{
    productId:str(商品id),
	productCount:str(商品数量),
	productPrice:str(商品价格),
	shelvesProductId:str(货架商品id)
}
```
<span id = "BannerComplex">BannerComplex</span>
```json
{
    total(banner展示数量),
    list: [{
        image:str(banner主图),
        url:str(跳转地址)
    }…]
}
```
<span id = "CategoryComplex">CategoryComplex</span>
```json
{
	name:str(类目名称),
	type:str(类目类型)
}
```

> Written with [StackEdit](https://stackedit.io/).