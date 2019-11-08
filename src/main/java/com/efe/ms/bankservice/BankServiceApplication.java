package com.efe.ms.bankservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * bank服务应用启动入口
 * @author TianLong Liu
 * @date 2019年11月6日 下午4:21:09
 */

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.efe.ms.**.dao")
@EnableScheduling
@ServletComponentScan
public class BankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServiceApplication.class, args);
	}

}
