package com.winhands.modules.pay.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.winhands.modules.pay.entity.PayEntity;
import com.winhands.modules.pay.entity.PayRecordEntity;
import com.winhands.modules.sys.dao.BaseDao;

@Mapper
public interface PayDao extends BaseDao<PayEntity>{
	
	PayEntity queryObject(PayEntity entity);
	
	void saveRecord(PayRecordEntity entity);
	
	PayRecordEntity queryRecord(Map<String,Object> map);
	
	void updateRecord(PayRecordEntity entity);
}
