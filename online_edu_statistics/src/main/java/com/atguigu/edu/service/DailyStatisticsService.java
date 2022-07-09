package com.atguigu.edu.service;

import com.atguigu.edu.entity.DailyStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author wang
 * @since 2022-07-08
 */
public interface DailyStatisticsService extends IService<DailyStatistics> {

    void generateData(String day);

    Map<String, Object> showStatistics(String dataType, String beginTime, String endTime);
}
