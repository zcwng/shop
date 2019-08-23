package com.zcwng.shop.shopmanagerweb;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itshidu.common.ftp.config.FtpPoolConfig;
import com.itshidu.common.ftp.core.FTPClientFactory;
import com.itshidu.common.ftp.core.FTPClientPool;
import com.itshidu.common.ftp.core.FtpClientUtils;
import com.zcwng.shop.manager.mapper.TbItemMapper;
import com.zcwng.shop.manager.mapper.TbUserMapper;
import com.zcwng.shop.manager.pojo.TbItem;
import com.zcwng.shop.manager.pojo.TbItemExample;
import com.zcwng.shop.manager.pojo.TbUser;

@SpringBootApplication
@MapperScan("com.zcwng.shop.manager.mapper")
@Controller
public class ShopManagerWebApplication {

	@Autowired TbUserMapper userMapper;
	@Autowired TbItemMapper itemMapper;
	
	public static void main(String[] args) {
		SpringApplication.run(ShopManagerWebApplication.class, args);
	}
	
	@ResponseBody				//http://localhost:8080/hello?text=%E6%96%B9%E9%9B%AF%E6%96%90
	@RequestMapping("/hello")
	public String hello(String text) {
	
		PageHelper.startPage(0, 10);
		List<TbItem> list = itemMapper.selectByExample(new TbItemExample());
		System.out.println("当前记录数"+list.size());
		
		PageInfo<TbItem> info = new PageInfo(list);
		System.out.println("总页数"+info.getPages());
		System.out.println("总记录数"+info.getTotal());
		
		
//		Date date = new Date();
//		TbUser user = new TbUser();
//		user.setUsername("地瓜4");
//		user.setCreated(date);
//		user.setUpdated(date);
//		user.setPassword("1231");
//		userMapper.insertSelective(user);
//		
		return "HelloWorld"+text;
	}
	
	@Value("${ftp.host}")
	private String FTP_HOST;
	@Value("${ftp.port}")
	private int FTP_PORT;
	
	@Value("${ftp.username}")
	private String FTP_USERNAME;
	@Value("${ftp.password}")
	private String FTP_PASSWORD;
	
	@Bean
	public FtpClientUtils initeFtp() {
		FtpPoolConfig config=new FtpPoolConfig();
		config.setHost(FTP_HOST);
		config.setPort(FTP_PORT);
		config.setUsername(FTP_USERNAME);
		config.setPassword(FTP_PASSWORD);
		FTPClientFactory clientFactory=new FTPClientFactory(config);
		FTPClientPool pool = new FTPClientPool(clientFactory);
		FtpClientUtils ftp = new FtpClientUtils(pool);
		return ftp;
	}
	
	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
}
