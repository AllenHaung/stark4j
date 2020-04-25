# Java后端脚手架以及工具集

## 模块说明
- stark4j-beans: 定义框架中所需要的bean
- stark4j-core: 核心依赖，使用框架时，必须要依赖此包，定义了框架的绝大部分功能以及一些工具类
- stark4j-mybatis: 对MyBatis的扩展。框架依赖的是MyBatisPlus，如果不需要可以移除，引入MyBatis
- stark4j-web: web层依赖，定义了消息格式以及一些异常处理，Swagger
