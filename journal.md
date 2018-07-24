#### maven 的 spring-boot 插件 可带参数
使用下列命令行查找
```
mvn help:describe -Dcmd=spring-boot:run -Ddetail
```

#### spring 扫描 jar 中的 `@Configuration` 注解
maven 项目:  
① 在 `src/main/resource` 目录下  
建立目录: `META-INF`  
在该目录下建立文件: `spring.factories`   
在该文件中,添加
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
# class1,\
# class2
#@Configuration 注解的类
```
② 在spring-boot 主类中配置 scanBasePackages,将 jar 包中的所有类全部配置扫到

如果是 ``@Compenent`` 注解,可以配置为 `@Configuration`注解的类,添加 `@Bean`类,再如上面的配置
