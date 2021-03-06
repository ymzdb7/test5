package com.winhands.modules.pay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winhands.common.utils.HttpUtils;
import com.winhands.common.utils.R;
import com.winhands.common.utils.WXPayConstants.SignType;
import com.winhands.common.utils.WXPayUtil;
import com.winhands.modules.market.dao.OrderDao;
import com.winhands.modules.market.dao.OrderGoodsDao;
import com.winhands.modules.market.entity.OrderEntity;
import com.winhands.modules.market.entity.OrderGoodsHeEntity;
import com.winhands.modules.pay.dao.PayDao;
import com.winhands.modules.pay.entity.PayEntity;
import com.winhands.modules.pay.entity.PayRecordEntity;
import com.winhands.modules.pay.service.PayService;
import com.winhands.modules.sys.entity.SysUserEntity;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private PayDao payDao;
	
	@Autowired
	private OrderGoodsDao orderGoodsDao;
	
	@Override
	public R getPrepayId(Map<String, Object> map,SysUserEntity user) {
		try {	// TODO Auto-generated method stub
		Long orderId=Long.valueOf(map.get("orderId").toString());
		OrderEntity order=orderDao.queryObjectW(orderId);
		if(order==null||order.getOrderState()==null||!"1".equals(order.getOrderState().trim())) {
			return R.error(02111,"该订单无法支付！");
		}		
		List<OrderGoodsHeEntity> list=orderGoodsDao.queryListO(map);
		Double totalPrice=0.00;
		for(OrderGoodsHeEntity entity:list) {
			totalPrice=totalPrice+entity.getTotalPrice().doubleValue();
		}
		totalPrice=totalPrice*100;
		
		//此处要验证用户		
		PayEntity entity=new PayEntity();
		entity.setAddress(order.getAddress());
		entity=payDao.queryObject(entity);
		if(entity==null||entity.getAppId()==null||(long)order.getUserId()!=(long)user.getUserId()) {
			return R.error("无法支付！");
		}
		
		String mch_id=entity.getMchId();
		String appid=entity.getAppId();
		String key=entity.getKey();
		String total_fee="1000";
		if(totalPrice<1000) {
			total_fee=totalPrice+"";
		}
						
		String ip=map.get("ip").toString();
		
		String notify_url="http://www.winhands.com:8880/sys/pay/callback";
		
		String openId=(String) map.get("openId");
		
		String nonce_str=WXPayUtil.generateNonceStr();
		
		String outTradeNo=order.getOrderNumber()+(System.currentTimeMillis()%1000000);
		
		Map<String,String> xmlMap=new HashMap<String,String>();
		//标题名称
		xmlMap.put("body", user.getAddress()+"市场-商品");
		//支付类型
		xmlMap.put("trade_type", "JSAPI");
		//商户号
		xmlMap.put("mch_id", mch_id);
		//JSAPI必选传openId
		xmlMap.put("openid", openId);
		//商户ID
		xmlMap.put("appid", appid);
		//签名类型
		xmlMap.put("sign_type", "MD5");
		//随机数
		xmlMap.put("nonce_str", nonce_str);
		//详细信息
		xmlMap.put("detail", "");
		//类别
		xmlMap.put("device_info", "WEB");
		//订单号
		xmlMap.put("out_trade_no", outTradeNo);
		//支付类型
		xmlMap.put("fee_type", "CNY");
		//支付金额
		xmlMap.put("total_fee", total_fee);
		//支付地址ip
		xmlMap.put("spbill_create_ip",ip );
		//回掉地址
		xmlMap.put("notify_url", notify_url);				
			String xml=WXPayUtil.generateSignedXml(xmlMap, key, SignType.MD5);
			HttpUtils h=  new HttpUtils();
			xmlMap=h.pay(xml);
			if(xmlMap.get("return_code")!=null
					&&xmlMap.get("return_code").trim().compareTo("SUCCESS")==0){
				//保存记录
				PayRecordEntity record=new PayRecordEntity();
				record.setOrderId(orderId);
				record.setOrderNum(order.getOrderNumber());
				record.setPrepayId(xmlMap.get("prepay_id"));
				record.setPayOrderNum(outTradeNo);
				record.setState(1);
				record.setTotalFee(totalPrice.intValue()+"");
				record.setUserId(user.getUserId());
				record.setUserIp(ip);
				record.setUsername(user.getUsername());
				record.setAddress(order.getAddress());
				record.setMchId(mch_id);
				payDao.saveRecord(record);
				orderDao.updateOpenId(orderId,openId);
				
				//返回签名信息
				String timeStamp=System.currentTimeMillis()+"";
				Map<String,String> signMap=new HashMap<String,String>();
				signMap.put("appId", appid);
				signMap.put("timeStamp", timeStamp);
				signMap.put("signType", "MD5");
				signMap.put("package","prepay_id="+xmlMap.get("prepay_id"));
				signMap.put("nonceStr", xmlMap.get("nonce_str")); 
				xmlMap.put("sign",WXPayUtil.generateSignature(signMap, key, SignType.MD5));
				xmlMap.putAll(signMap);				
			
			}
											
			return R.ok().put("result", xmlMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return R.error("支付调用失败！");
	}

	@Override
	public PayEntity getPay(PayEntity entity) {
		
		return payDao.queryObject(entity);
	}

	@Override
	public R getOpenId(String code, SysUserEntity user) {
		
		PayEntity entity=new PayEntity();
		entity.setAddress(user.getAddress());
		entity=payDao.queryObject(entity);
		if(entity==null||entity.getAppId()==null||"".compareTo(entity.getAppId().trim())==0) {
			return R.error("属地不正确！");
		}
		String url="https://api.weixin.qq.com/sns/oauth2/access_token"
			+ "?appid="+entity.getAppId()
			+ "&secret="+entity.getAppSecret()
			+ "&code="+code
			+ "&grant_type=authorization_code";
		HttpUtils http=new HttpUtils();
		String openId=http.getWinXiUserJSON(code,url);		
		return R.ok().put("openid", openId);
	}

	@Override
	@Transactional
	public String callback(String notityXml) {
		
		String result=WXPayUtil.setXml("fail","xml为空");
		   try {
		        Map map = WXPayUtil.xmlToMap(notityXml); 
		        		        
		        // 解析各种数据  
		        String appid = (String) map.get("appid");//应用ID  
		        String attach = (String) map.get("attach");//商家数据包  
		        String bank_type = (String) map.get("bank_type");//付款银行  
		        String cash_fee = (String) map.get("cash_fee");//现金支付金额  
		        String fee_type = (String) map.get("fee_type");//货币种类  
		        String is_subscribe = (String) map.get("is_subscribe");//是否关注公众账号  
		        String mch_id = (String) map.get("mch_id");//商户号  
		        String nonce_str = (String) map.get("nonce_str");//随机字符串  
		        String openid = (String) map.get("openid");//用户标识  
		        String out_trade_no = (String) map.get("out_trade_no");// 获取商户订单号  
		        String result_code = (String) map.get("result_code");// 业务结果  
		        String return_code = (String) map.get("return_code");// SUCCESS/FAIL  
		        String sign = (String) map.get("sign");// 获取签名  
		        String time_end = (String) map.get("time_end");//支付完成时间  
		        String total_fee = (String) map.get("total_fee");// 获取订单金额  
		        String trade_type = (String) map.get("trade_type");//交易类型  
		        String transaction_id = (String) map.get("transaction_id");//微信支付订单号  
		        
		        Map<String,Object> params=new HashMap<String,Object>();
		        params.put("mchId", mch_id);
		        params.put("payOrderNum", out_trade_no);
		        PayRecordEntity record=payDao.queryRecord(params);
		        if(record==null||record.getAddress()==null) {
		        	result=WXPayUtil.setXml("fail","订单号不存在!");
		        	return result;
		        }
		        if(record.getState()==2) {
		        	result=WXPayUtil.setXml("SUCCESS","OK");
		        	return result;
		        }		        	
		        PayEntity entity=new PayEntity();		        
		        entity.setAppId(appid);
		        entity.setAddress(record.getAddress());
		        entity= payDao.queryObject(entity);
		        if(entity==null||entity.getKey()==null) {
		        	result=WXPayUtil.setXml("fail","appid不存在!");
		        	return result;
		        }
		        String key=entity.getKey();		        
		        boolean flag=WXPayUtil.isSignatureValid(notityXml, key);		  
		        // 验证签名  
		        if (flag) {  
		            result = WXPayUtil.setXml("SUCCESS", "OK");  
		        } else {  
		            result = WXPayUtil.setXml("fail", "签名不一致！");  
		        }  
		        if (!return_code.equals("SUCCESS")) {  		              
		            result =  WXPayUtil.setXml("fail", "微信返回的交易状态不正确（result_code=" + return_code + "）");  
		        }  
		        //如果成功写入数据库  
		        if (return_code.equals("SUCCESS")) {// 如果微信返回的结果是success，则修改订单状态  
		            //判断订单号是否重复 
		        	OrderEntity order=new OrderEntity();
		        	order.setOrderState("2");
		        	order.setOrderNumber(record.getOrderNum());
		        	orderDao.updateState(order);
		        	record.setState(2);
		            payDao.updateRecord(record); 
		         }  
		        }catch(Exception e) {
		        	result =WXPayUtil.setXml("fail", "业务处理错误！"); 
		        }
		   
		   return result;
	}

	@Override
	public R upState(Long orderId, SysUserEntity user) {
		// TODO Auto-generated method stub
	
		OrderEntity order=orderDao.queryObjectW(orderId);
	
		if(order==null||order.getUserId()==null||order.getUserId().compareTo(user.getUserId().intValue())!=0) {
			return R.error(02112,"无法修改");
		}
		if("2".equals(order.getOrderState())) {
			return R.ok("支付已完成！");
		}
		OrderEntity entity=new OrderEntity();
		entity.setId(orderId.intValue());
		entity.setOrderState("5");
		orderDao.updateState(entity);
		return R.ok();
	}
	
	
	
	
	
}
