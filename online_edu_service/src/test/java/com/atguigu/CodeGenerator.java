package com.atguigu;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 逆向工程
 *      :需要自定义 全局配置中的 globalConfig.setOutputDir的内容
 *      ：需要自定义  数据源配置中的 url 和 password
 *      ：需要自定义
 * @author
 * @since 2018/12/13
 */
public class CodeGenerator {

    public static void main(String[] args) {

        // 1、创建代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 2、全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //String projectPath = System.getProperty("user.dir");
        //globalConfig.setOutputDir(projectPath + "/src/main/java");
        //C:\Users\22196\Desktop\qianglaoshi\online_edu_parent\online_edu_service
        globalConfig.setOutputDir("C:\\Users\\22196\\Desktop\\qianglaoshi\\online_edu_parent\\online_edu_service\\src\\main\\java");
        globalConfig.setAuthor("wang");
        //生成后是否打开资源管理器
        globalConfig.setOpen(false);
        //重新生成时文件是否覆盖
        globalConfig.setFileOverride(false);
        //去掉Service接口的首字母I
        globalConfig.setServiceName("%sService");
        //主键策略
        globalConfig.setIdType(IdType.ID_WORKER_STR);
        //定义生成的实体类中日期类型
        globalConfig.setDateType(DateType.ONLY_DATE);
        //开启Swagger2模式
        globalConfig.setSwagger2(true);

        autoGenerator.setGlobalConfig(globalConfig);

        // 3、数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://192.168.2.128:3306/online_edu?serverTimezone=GMT%2B8");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        dataSourceConfig.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dataSourceConfig);

        // 4、包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(""); //模块名
        packageConfig.setParent("com.atguigu.edu");
        packageConfig.setController("controller");
        packageConfig.setEntity("entity");
        packageConfig.setService("service");
        packageConfig.setMapper("mapper");
        autoGenerator.setPackageInfo(packageConfig);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        /**
         * 后期需要逆向生成其它表信息时，修改下面这个 表名
         *  //edu_course  // edu_course_description
         */
        strategy.setInclude("edu_section");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(packageConfig.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        autoGenerator.setStrategy(strategy);


        // 6、执行
        autoGenerator.execute();
    }
}
