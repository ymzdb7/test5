package com.winhands.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpUtils {
	
	/*此处可改成成配置文件*/	
	private static String URL="http://211.149.174.103:8080/sys/oauth";
	
	public static JSONObject getUserJSON(String token) {		
		String result="";
		 try {
            // 根据地址获取请求
			 HttpPost request = new  HttpPost(URL);			 
			 //建立HttpPost对象
             List<NameValuePair> params=new ArrayList<NameValuePair>();
             //建立一个NameValuePair数组，用于存储欲传送的参数
             params.add(new BasicNameValuePair("token",token));
             //添加参数
             request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);            
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
            } 
            JSONObject json=JSONObject.fromObject(result);
            return json;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();             
        }				
		return null;		
	}
		
	  public  Map<String,String> pay(String xml){
			String result="";
			 try {
				String URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
	           // 根据地址获取请求
				HttpPost request = new  HttpPost(URL);				
				StringEntity entity= new StringEntity(xml,"UTF-8");
				request.setEntity(entity);
				request.setHeader("Content-Type", "text/xml;charset=UTF-8");
				
	           // 获取当前客户端对象
	           HttpClient httpClient = new DefaultHttpClient();
	           
	           // 通过请求对象获取响应对象
	           HttpResponse response = httpClient.execute(request);  
	           
	           // 判断网络连接状态码是否正常(0--200都数正常)
	           if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	               result= EntityUtils.toString(response.getEntity(),"utf-8");
	           } 	  		 	
	           Map<String,String> resultMap=WXPayUtil.xmlToMap(result);
	           return resultMap;
	       } catch (Exception e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();             
	       }				
			return null;			
		}
	    
	  
	  /**
		 * 获取openId
		 * */
		public String getWinXiUserJSON(String code,String URL) {
			
			String result="";
			 try {
				 URL=URL.replace("CODE", code);
	            // 根据地址获取请求
	            HttpGet request = new  HttpGet(URL);
	            // 获取当前客户端对象
	            HttpClient httpClient = new DefaultHttpClient();
	            // 通过请求对象获取响应对象
	            HttpResponse response = httpClient.execute(request);            
	            // 判断网络连接状态码是否正常(0--200都数正常)
	            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                result= EntityUtils.toString(response.getEntity(),"utf-8");
	            } 
	            JSONObject json=JSONObject.fromObject(result);
	   		 	String openid=json.getString("openid");
	            return openid;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();             
	        }				
			return null;		
		}
}
