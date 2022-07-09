# online_edu_parent
1：第一天，初始化在线教育项目环境 使用了逆向工程 

？测试发现：
    -- 使用逆向工程创建项目没有主启动类
        1.需要自己手动创建主启动类，且需要在启动类上加注解 @MapperScan("com.atguigu.edu.mapper")
    -- 配置文件中的url需要加上serverTimezone=GMT
    
2: 第二天
    -- 1.0 查询edutearcher 表中的所有数据，返回到前端时，数据出现时区问题
    -- 原因:后端@response注解将对象转化为json数据时出现时区丢失，且时间格式不对
    -- 解决方式 ： 在配置文件中添加配置：spring.jackson.time-zone=GMT+8
                                      spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
    -- 1.2 逻辑删除一个讲师
    
    -- 1.3 分页查询时遇到的异常：当if判断条件写错了，前端没有条件传过来，但是却执行了wrapper()方法导致sql语句生成了错误的参数
    Preparing: SELECT COUNT(1) FROM edu_teacher WHERE is_deleted = 0 AND name LIKE ? 
    ==> Parameters: %null%(String)
    <==    Columns: COUNT(1)
    
    -- MyMetaObjectHandler :元数据处理类中自动填充方法的参数fileName指的是 java实体类的字段而不是数据库的字段
    
    -- 1.4 Java中的各种O
        po ：持久层对象 简单理解为一个PO对应数据库中的一个对象
        VO ：视图层数据 通常用于封装页面要展示的数据 或者 从视图层传递到后台的数据
        DTO: 分装服务与服务之间 层与层之间 传输的数据
        BO ：通常用于业务模型 用于封装业务数据
        POJO：普通的java类 带有字段和get set方法的类
                                      
    --2.0 swagger 作用
          1.0 帮助开发人员生成jie接口文档
          访问地址: http://127.0.0.1:8000/swagger-ui.html
          
    --3.0 @Accessors(chain = true) : 在使用lombok生成get set 方法时，加上这个注解可以自动生成链式编程
    
    --8.0 全局异常捕获 ：定义一个类myException 在其上加注解@controllerAdvice 在里面定义方法：在其上加注解@ExceptionHandler(Exception.class)

3.第三天
    -- 跨域问题 ：一个域名访问另外一个域名时，协议，ip，端口号，请求方式不一致,请求路径中的小写的L 写成了数字1，项目代码修改后没有重启，重启nginx。任意一个不同都会出现跨域问题
        在controller上加注解@CrossOrigin
  
4.第八天
    使用逆向工程生成course /course_description表
    
    
    2.出现的问题
    		a.情况
    			Invalid bound statement (not found): com.atguigu.edu.mapper.EduCourseMapper
    		b.分析原因
    			class编译目录下 没有找到mapper与之对于的xml文件
    		c.解决办法
    			第一种方案
    				让src/main/java目录下要有xml文件
    				<build>
    					<resources>
    						<resource>
    							<directory>src/main/java</directory>
    							<includes>
    								<include>**/*.xml</include>
    							</includes>
    							<filtering>false</filtering>
    						</resource>
    					</resources>
    				</build>
    				为了让其生效 必须要rebuild一下项目
    				还得告诉mybatis xml路径在那个地方
    					mybatis-plus.mapper-locations=classpath:com/atguigu/edu/mapper/xml/*.xml
    			第二种方案
    				把xml文件放到resources目录下 需要同mapper所在的包同一级
    				如果不在同一级 还是需要配置 mybatis-plus.mapper-locations
// 搭建springCloud微服务，+ 远程调用步骤
    1.在父pom中加上springCloud的父依赖
    2.搭建eureka 服务端 ：需要加入服务端依赖
        2.1 需要加入服务端依赖
        2.2 编写配置文件
        2.3 启动类上加注解 @EnableEurekaServer
        
    3.搭建服务提供端
        3.1 加入eureka客户端依赖
        3.2 编写配置类
        3.3 主启动类加服务发现注解 @EnableDiscoveryClient //开启服务发现
        
    4.搭建服务消费端
        4.1 加入eureka客户端依赖
        4.2 编写配置类
        4.3 主启动类加服务发现注解 @EnableDiscoveryClient //开启服务发现
        4.4 多一步：
                - 需要加openFeign依赖
                - 主启动类加注解 EnableFeignClients //开启远程调用
                - 编写远程调用接口 加注解 @FeignClient(value = "EDU-VIDEO",fallback = VideoServiceHandler.class)
                - 编写 VideoServiceHandler 类中的兜底方法
                
