###### Elasticsearch Study 

# 安装
MacOS安装：
`brew update
 brew install elasticsearch@6
 brew services start elasticsearch@6
 brew services restart elasticsearch@6
`

# 项目结构说明
+ config 配置模块，存放elasticsearch连接配置等文件
+ controller 控制器模块，提供api接口
+ exception 异常类模块，存放业务封装的异常类文件
+ models 数据模型
+ utils 工具类模块
+ resources 应用启动配置模块


# 注意事项
elasticsearch分为服务端和客户端，使用的时候要注意版本一致；


# 学习资料
```
官方文档【中文版】：https://www.elastic.co/guide/cn/elasticsearch/guide/2.x/_who_should_read_this_book.html

GitBook：https://endymecy.gitbooks.io/elasticsearch-guide-chinese/content/java-api/README.html

ES基础查询：https://blog.csdn.net/qq_38620956/article/details/102757513
```