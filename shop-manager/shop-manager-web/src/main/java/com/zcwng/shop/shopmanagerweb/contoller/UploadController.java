package com.zcwng.shop.shopmanagerweb.contoller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itshidu.common.ftp.config.FtpPoolConfig;
import com.itshidu.common.ftp.core.FTPClientFactory;
import com.itshidu.common.ftp.core.FTPClientPool;
import com.itshidu.common.ftp.core.FtpClientUtils;
import com.itshidu.web.tools.Result;

@Controller
public class UploadController {

	@Autowired FtpClientUtils ftp;
	
	@Value("${ftp.host}")
	String ftpHost;
	
	@ResponseBody
	@RequestMapping("/pic/upload")
	public Object imageUpload(MultipartFile uploadFile) {
		
		String name = UUID.randomUUID().toString()+".jpg";
		try {
			Date date = new Date();
			String year = new SimpleDateFormat("yyyy").format(date); 
			String month = new SimpleDateFormat("MM").format(date); 
			String savepath = "/image/"+year+"/"+month+"/";
			ftp.store(uploadFile.getInputStream(), savepath, name);
			return Result.of().put("error", 0).put("url", "http://"+ftpHost+savepath+name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
