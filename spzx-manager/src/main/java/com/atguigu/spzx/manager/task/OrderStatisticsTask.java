package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

// com.atguigu.spzx.manager.task
@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
//DateUtil.offsetDay获取时间 ,1代表明天,-1为昨天,并转换为字符串
        String createTime = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
//        调用orderInfoMapper统计订单的方法,查询的是orderinfo表,但是返回的要是统计结果的OrderStatistics表(这里和之前不太一样)
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        if(orderStatistics != null) {
//            把统计的结果存入表中
            orderStatisticsMapper.insert(orderStatistics) ;
        }
    }

}