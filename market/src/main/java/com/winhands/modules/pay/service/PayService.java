package com.winhands.modules.pay.service;

import java.util.Map;
import com.winhands.common.utils.R;
import com.winhands.modules.pay.entity.PayEntity;
import com.winhands.modules.sys.entity.SysUserEntity;


/**
 * shiro相关接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-06 8:49
 */
public interface PayService {
    /**
     * 获取支付id
     */
	R getPrepayId(Map<String, Object> map,SysUserEntity user);
	
	PayEntity getPay(PayEntity entity);

	R getOpenId(String code, SysUserEntity user);

	String callback(String notityXml);

	R upState(Long orderId, SysUserEntity user);



}
