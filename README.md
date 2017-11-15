## 格格家

### assembly

* 业务打包层，可按照层级分为api、service
* 该层只负责打包，不负责具体的业务
* 由于更好的兼容业务，在该层中，添加了部分拦截器、统一异常处理

### biz

* 业务层，可按照业务细分api、service、任务调度、消息
* 该层用于描述具体的业务，负责对外提供接口（api提供servlet接口、servie提供rpc接口）
* 业务间的调用，可通过rpc、消息等方式调用，尽量避免代码耦合

### core

* DO层，负责封装对数据库的调用
* 同时封装本地事务
* 如考虑到后续的服务化，可按照预先的服务化，细分成不同的module

### common
* 定义service接口类
* 通用的工具类

* 提供rpc调用的依赖包

## 代码结构细分规则

* 垂直拆分：按照功能层级拆分，该层级的调用通常使用rpc实现
* 水平拆分：按照业务层级拆分，尽量避免该层级的直接调用，可以使用A业务通过RPC调用B业务的service实现
* 避免过度封装，产生高耦合jar包

## api接口的版本控制

* 采用注解@Apiversion(1.0f)控制，版本接口支持向下兼容

## api的输入输出

* 输入参数，采用对象封装，且对象需要继承ProtocolPojo，后缀为Param，放置在param包下
* 输出参数，采用对象封装，且对象需要继承Result, 后缀为View, 放置在view包下
* 这样定义的目的是在于输入、输出参数变更时，无需调整接口

## service
* Contoller中直接调用的service，格式按照*Service定义（不建议在Service上加一层接口）
* 如果是通过api包调用的service实现类，格式按照*ServiceImpl定义，接口按照*ServiceApi定义
* 如支持主从，在service中如果需要从库查询，添加注解：@Transactional(readOnly = true)
