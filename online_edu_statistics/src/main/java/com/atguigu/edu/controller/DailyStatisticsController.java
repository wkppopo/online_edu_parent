package com.atguigu.edu.controller;


import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.DailyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author wang
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/daily/statistics")
@CrossOrigin
public class DailyStatisticsController {
    @Autowired
    private DailyStatisticsService dailyStatisticsService;

    //前端传递日期 后端生成该日期对应的 注册人数 登录人数 等数据信息
    @GetMapping("/generateData/{day}")
    public RetVal generateData(@PathVariable String day){
        dailyStatisticsService.generateData(day);
        return RetVal.success();
    }

    //返回数据给echarts
    @GetMapping("showStatistics/{dataType}/{beginTime}/{endTime}")
    public RetVal showStatistics(@PathVariable String dataType,@PathVariable String beginTime,@PathVariable String endTime){
        Map<String,Object> map=dailyStatisticsService.showStatistics(dataType,beginTime,endTime);
        return RetVal.success().data(map);
    }
}

