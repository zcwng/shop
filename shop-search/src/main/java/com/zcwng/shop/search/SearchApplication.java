package com.zcwng.shop.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.zcwng.shop.search.mapper")
@RestController
public class SearchApplication<TbItem> {

	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}
	
}
