package com.atguigu.atcrowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@ServletComponentScan //扫描@WebListener  @WebFilter  @WebServlet
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AtcrowdfundingBootPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtcrowdfundingBootPortalApplication.class, args);
	}
}
