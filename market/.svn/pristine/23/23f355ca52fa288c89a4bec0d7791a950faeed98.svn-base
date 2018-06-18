package com.winhands.modules.pay.controller;



import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winhands.common.utils.IPUtils;
import com.winhands.common.utils.R;
import com.winhands.common.utils.WXPayUtil;
import com.winhands.modules.pay.service.PayService;
import com.winhands.modules.sys.controller.AbstractController;



@RestController
@RequestMapping("/sys/pay")
public class PayController extends AbstractController{
	@Autowired
	private PayService payService;
	
	/**
	 * 支付获取parper_id
	 * */
	@RequestMapping(value="/ge",method=RequestMethod.POST)
	public R get(Long orderId,String openId,HttpServletRequest request){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("openId", openId);
		params.put("ip", IPUtils.getIpAddr(request));
		return payService.getPrepayId(params, getUser());
	}
	
	
	/**
	 * 支付回调
	 * */
	@RequestMapping("callback")
	public String callback(HttpServletRequest request,
            HttpServletResponse response) throws Exception {  
		
		
        String result;//返回给微信的处理结果  
        String inputLine;  
        String notityXml = "";  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html;charset=UTF-8");  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        //微信给返回的东西  
        try {  
            while ((inputLine = request.getReader().readLine()) != null) {  
                notityXml += inputLine;  
            }  
            request.getReader().close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            result = WXPayUtil.setXml("fail","xml获取失败"); 
            return result;
        }  
        if (StringUtils.isEmpty(notityXml)) {  
            result = WXPayUtil.setXml("fail","xml为空"); 
            return result;
        } 
        
        result=payService.callback(notityXml);
        return result; 
        
	}
	
	/**
	 * 获取OpenId
	 * */
	@RequestMapping("oauth")
	public R getOpenId(String code) {		
		return payService.getOpenId(code,getUser());		
	}
	
	@RequestMapping("up")
	public R getOpenId(Long orderId) {		
		return payService.upState(orderId,getUser());		
	}
	
	
}
