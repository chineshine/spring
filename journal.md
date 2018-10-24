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
`RestTemplate` 是 `spring` 用于远程调用的接口,相当于 `httpCLlient`
