#### 查看 `maven` 的 `spring-boot` 插件 可带参数
使用下列命令行查找
```
  mvn help:describe -Dcmd=spring-boot:run -Ddetail
```

#### `spring` 扫描 `jar` 中的 `@Configuration` 注解
maven 项目:  
① 在 `src/main/resource` 目录下  
建立目录: `META-INF`  
在该目录下建立文件: `spring.factories`   
在该文件中,添加
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
# class1,\
# class2
# class1, class2 都是 @Configuration 注解的类
```
② 在 `spring-boot` 主类中配置 `scanBasePackages`,将 `jar` 包中的所有类全部配置扫到

如果是 ``@Compenent`` 注解,可以配置为 `@Configuration`注解的类,添加 `@Bean` 类,再如上面的配置

#### `spring-boot` 前后台传参
`post` 请求: 如果传的是对象,参数对象前必须加上 `@RequestBody` 注解,不然对象接收不到值  
`get` 请求: 不要加 `@RequestBody` 注解,否则参数接收不到

#### 关于 `TestRestTemplate`,`RestTemplate`
`TestRestTemplate` 是 `spring-test` 包中的内容,实现了 `RestTemplate` 所有功能  
`RestTemplate` 是 `spring` 用于远程调用的接口,相当于 `httpClient`

#### 让 `spring cloud config` 远程配置文件不覆盖本地配置
场景: 假如某个测试类,使用的配置中包含 `h2` 数据库,而实际生产和测试环境不会去使用 `h2` 数据库,所以要用不同的配置文件.测试类使用`@ActiveProfiles`引用本地配置文件去跑测试,然而`spring cloud config` 远程配置文件会覆盖本地配置.  
解决: 在 `spring cloud config` 远程配置文件中加入配置:
```
spring:
  cloud:
    config:
      overrideNone: true
```
注意: 一定要是 **远程配置文件**  
此时,再在测试类中使用注解`@ActiveProfiles(profiles="profilename")`引入本地配置,远程配置就不会覆盖本地配置  
参考类:
```
  org.springframework.cloud.bootstrap.config.PropertySourceBootstrapProperties
```
