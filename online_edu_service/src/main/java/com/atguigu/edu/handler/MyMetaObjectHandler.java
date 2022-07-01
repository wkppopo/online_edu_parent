package com.atguigu.edu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisPlus 的自动填充插件配置
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("进入mybatisPlus元数据处理类 --> 执行插入自动填充");
        this.setFieldValByName("isDeleted",0,metaObject);
        //参数fieldName 指的是Java实体类的字段名而不是数据库的字段名
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("进入mybatisPlus元数据处理类 --> 执行更新自动填充");
        //参数fieldName 指的是Java实体类的字段名而不是数据库的字段名
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
