package com.atguigu.atcrowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AtcrowdfundingBootManagerApplication {

	@Bean
	@LoadBalanced  //采用负载均衡方式连接远程服务器
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	
	public static void main(String[] args) {
		SpringApplication.run(AtcrowdfundingBootManagerApplication.class, args);
	}
}
