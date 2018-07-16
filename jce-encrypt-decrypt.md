## spring 配置文件加密
### 说明
本文档仅仅针对 spring-cloud-config 项目  
对 spring-cloud-config 项目的配置文件中涉及到的敏感内容(如密码)进行加密,包括利用 spring-cloud-config 进行远程配置的项目的配置文件  
要求机器上已装 java,且包含 jce

### 版本要求说明
jdk 8 以上的版本  
版本号为 jdk_8u_151 及以上 , jdk 8 低于该版本也不可以  
jdk_8u_151及以上版本已内置 jce加密

### 下载
针对非 jdk_8u_151 版本及以上的 jdk  
##### jdk_6  
http://www.oracle.com/technetwork/java/embedded/embedded-se/downloads/jce-6-download-429243.html  

##### jdk_7  
http://www.oracle.com/technetwork/java/embedded/embedded-se/downloads/jce-7-download-432124.html  

##### jdk_8
http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html  

将下载后的文件放入 java 目录中  
具体位置:(必需)  
$java_home/jre/lib/security  
待定目录:(未测试)  
$java_home/bin/lib/lib/security  
待定目录未测试,官网也未给定,纯属网友建议,建议参考下载后的包里的 README 文件

### 生成对称加密
```
keytool -genkeypair -alias configserver -keyalg RSA \
 -dname "CN=Config Server,OU=Newtouch Cloud,\
 O=Newtouch,L=Shanghai,S=Shanghai,C=CN" \
 -keypass 123 -keystore configserver.jks \
 -storepass 456
```
需要修改的地方(参数后面的内容):
```
-alias 别名
-dname 部分内容根据实际去定义
-keypass
-keystore
-storepass
```
生成密钥后可能会弹出警告:
```
Warning:
JKS 密钥库使用专用格式。建议使用
"keytool -importkeystore -srckeystore configserver.jks \
 -destkeystore configserver.jks -deststoretype pkcs12"
 迁移到行业标准格式 PKCS12。
```
官方建议的密钥格式迁移,将密钥格式迁移到 PKCS12  
可以选择执行,也可以忽视  
如果执行会要求输入密码:  
密码为 -storepass 指定的值

### 配置文件配置
将生成后的 configserver.jks(本例) 文件 复制到 spring-cloud-config 项目中,放在 src/main/resource 目录下即可

bootstrap.yml | bootstrap.properties 文件
```
encrypt:
  key: configOne  # 随便指定
  failOnError: false
  keyStore:
    location: classpath:configserver.jks
    password: 456 # -storepass 指定的值
    alias: configserver # -alias 指定的值
    secret: 123 # -keypass 指定的值
```

### 将密码加密
如要将 spring.cloud.config.server.git.password 加密  
假设密码为 123   
利用 curl 命令,没有可去下载,windows 版本也有  
本仓库内置版本为 windows_x86_64
```
curl http://localhost:80/configserver/encrypt -d 123
```
此时控制台会输出一串数字,复制
将配置改为:
```
spring.cloud.config.server.git.password:{cipher}此处是一串很长很长很长的字符串
```
如果使用的是 .propertie 文件  
**上面配置的值千万不要加 引号(')**

加密某个项目里的配置  
```
curl http://localhost:80/configserver/encrypt/{application}/{profile} -d 123
```

### 其他
项目禁用 /encrypt 和 /decrypt 路径
```
spring.cloud.config.server.encrypt.enabled=false
```
