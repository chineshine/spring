# spring-boot test
## `@SpringBootTest` 四种模式
```
  MOCK(Default)
  RANDOM_PORT
  DEFINED_PORT
  NONE
```
### `Mock`
`@SpringBootTest` 或 `@SpringBootTest(webEnvironment = WebEnvironment.Mock)`  
不会启动 server  
提供一个模拟的 WEB 环境,加载一个 web ApplicationContext  
如果 classpath 不适用 web 环境,只会加载一个非 web ApplicationContext  
可使用注解  `@AutoConfigureMockMvc`  `@AutoConfigureWebTestClient`   
可在测试类中用 `@Autowire` 注入 `MockMvc`

### `RANDOM_PORT`
`@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`  
会启动 server   
提供一个真实 web 环境  
加载 WebServerApplicationContext  
同时 server 会监听一个随机端口
可在测试类中注入 `TestRestTemplate`

### `DEFINED_PORT`
同 `RANDOM_PORT`  
server 会监听一个实际端口或默认端口(8080)

### `NONE`
不会提供任何 web 环境
通过 SpringApplication 加载一个ApplicationContext  

### 注意事项
`MOCK` 和 `NONE` 模式不会启动 server,如果 maven 项目中添加了 `websocket` 包,跑 `mvn test` 会产生冲突报错,建议使用注解 `@WebMvcTest` 代替 `@SpringBootTest`  
