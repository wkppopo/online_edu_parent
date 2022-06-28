# online_edu_parent
1：第一天，初始化在线教育项目环境 使用了逆向工程

？测试发现：
    -- 使用逆向工程创建项目没有主启动类
        1.需要自己手动创建主启动类，且需要在启动类上加注解 @MapperScan("com.atguigu.edu.mapper")
    -- 配置文件中的url需要加上serverTimezone=GMT 
    