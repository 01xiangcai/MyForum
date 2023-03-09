package com.yao.Demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: logDemo
 * @Description: TODO
 * @author: long
 * @date: 2023/3/8 17:01
 */
public class logDemo {


    private final static Logger logger = LoggerFactory.getLogger(logDemo.class);

    public static void main(String[] args) {
        logger.info("logback 成功了");
        logger.info("logback22 成功了");
        logger.error("logback 失败了");
        logger.debug("logback 成功了");
    }


}
