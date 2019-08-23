package com.zcwng.shop.portal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itshidu.web.tools.CookieUtil;
import com.itshidu.web.tools.Result;
import com.zcwng.shop.common.HttpClientUtil;
import com.zcwng.shop.common.JsonUtils;
import com.zcwng.shop.common.Paging;
import com.zcwng.shop.portal.service.SearchService;
import com.zcwng.shop.portal.vo.ItemInfo;
import com.zcwng.shop.portal.vo.TbUser;

@SpringBootApplication
@Controller
public class PortalApplication<TbItem> {
	
	@Autowired SearchService searchService;
	
	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}
	
	ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("/index")
	public Object index(HttpServletRequest request) {
		String adJson = HttpClientUtil.doGet("http://localhost:8083/rest/content/category/89");
		try {
			ArrayNode array = mapper.createArrayNode();
			
			//JsonNode root =mapper.readTree(adJson);			//json反序列化成对象
			//JsonNode listNode =root.get("data");
			//String text = mapper.writeValueAsString(listNode);
			List<HashMap<String,Object>>list = mapper.readValue(adJson,new TypeReference<List<HashMap<String,Object>>>() {});
			list.forEach(map->{
				ObjectNode node = mapper.createObjectNode();
				node.put("width",670);
				node.put("height", 240);
				node.put("widthB",550);
				node.put("heightB", 240);
				node.put("alt", "");
				node.put("href",map.get("url").toString());
				node.put("src",map.get("pic").toString());
				node.put("srcB",map.get("pic").toString());
				array.add(node);
			});
			
			request.setAttribute("ad1",mapper.writeValueAsString(array));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping("/search.html")
	public Object search(String q,@RequestParam(defaultValue = "1")int page,HttpServletRequest request) throws JsonProcessingException, IOException {
		Paging<ItemInfo> rs=searchService.search(q,page);
		request.setAttribute("data", rs);
		return "search";
	}
	
	@RequestMapping("/login.html")
	public Object login(ModelAndView mv) {
		mv.setViewName("login");
		return mv;
	}
	
	@RequestMapping("/register.html")
	public Object register(ModelAndView mv) {
		mv.setViewName("register");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/user/register")
	public Object register(String username,String password,String phone) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("username",username);
		param.put("password",password);
		param.put("phone", phone);		
		String text = HttpClientUtil.doPost("http://localhost:8085/user/register",param);
		return text;
	}
	
	@ResponseBody
	@RequestMapping("/user/login")
	public Object login(String username,String password,HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("username",username);
		param.put("password",password);	
		String text = HttpClientUtil.doPost("http://localhost:8085/user/login",param);
		try {
			Result result = JsonUtils.jsonToObject(text, Result.class);
			if((result.get("code")+"").equals("200")) {//如果登陆成功
				String token = (String) result.get("token");
				CookieUtil.setCookie("SHOP_TOKEN", token,3600*24*30, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	@ResponseBody
	@RequestMapping("/order/test")
	public Object orderTest(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		return "用户名："+user.getUsername();
	}
}
