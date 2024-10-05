package com.qifei.service.Impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.qifei.service.LogService;

public class LogServiceImpl implements LogService{

     @RabbitListener(queues = "#{logQueue.name}")
     @Override
     public void recordLog(String message) {
          
     }

}
