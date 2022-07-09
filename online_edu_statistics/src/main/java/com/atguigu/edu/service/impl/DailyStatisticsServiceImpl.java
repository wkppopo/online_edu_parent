package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.DailyStatistics;
import com.atguigu.edu.mapper.DailyStatisticsMapper;
import com.atguigu.edu.response.RetVal;
import com.atguigu.edu.service.DailyStatisticsService;
import com.atguigu.edu.service.UserFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author wang
 * @since 2022-07-08
 */
@Service
public class DailyStatisticsServiceImpl extends ServiceImpl<DailyStatisticsMapper, DailyStatistics> implements DailyStatisticsService {
    @Autowired
    private UserFeignService userFeignService;

    //模拟生成统计数据
    @Override
    public void generateData(String day) {
        RetVal retVal = userFeignService.queryRegisterNum(day);
        Integer registerNum = (Integer)retVal.getData().get("registerNum");
        DailyStatistics dailyStatistics = new DailyStatistics();
        //设置相关的值
        dailyStatistics.setDateCalculated(day); //注册日期
        dailyStatistics.setRegisterNum(registerNum); //注册数量
        dailyStatistics.setLoginNum(RandomUtils.nextInt(200, 300));//登录数量200-300之间
        dailyStatistics.setVideoViewNum(RandomUtils.nextInt(500, 600));
        dailyStatistics.setCourseNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(dailyStatistics);
    }

    //返回数据给echarts
    @Override
    public Map<String, Object> showStatistics(String dataType, String beginTime, String endTime) {
        //创建查询条件
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",beginTime,endTime);
        wrapper.orderByAsc("date_calculated");
        //查询指定时间范围内的所有数据
        List<DailyStatistics> dailyStatistics = baseMapper.selectList(wrapper);
        //构建两个集合XData YData
        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Integer> yData = new ArrayList<>();
        //遍历所有查询到的数据
        for (DailyStatistics dailyStatistic : dailyStatistics) {
            String dateCalculated = dailyStatistic.getDateCalculated();
            xData.add(dateCalculated);
            //判断根据dataType返回指定的YData的值
            switch(dataType){
                case "login_num":
                    Integer loginNum = dailyStatistic.getLoginNum();
                    yData.add(loginNum);
                    break;
                case "register_num":
                    Integer register_num = dailyStatistic.getRegisterNum();
                    yData.add(register_num);
                    break;
                case "video_view_num":
                    Integer video_view_num = dailyStatistic.getVideoViewNum();
                    yData.add(video_view_num);
                    break;
                case "course_num":
                    Integer course_num = dailyStatistic.getCourseNum();
                    yData.add(course_num);
                    break;
                default:
                    break;
            }
        }
        //将calculated的值作为xData
        HashMap<String, Object> map = new HashMap<>();
        map.put("xData",xData);
        map.put("yData",yData);
        return map;
    }
}
