package com.winhands.modules.market.service.Impl;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winhands.modules.market.dao.OrderGoodsDao;
import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.OrderGoodsEntity;
import com.winhands.modules.market.entity.OrderGoodsHeEntity;
import com.winhands.modules.market.service.OrderGoodsService;

/**
 * 商品管理
 */
@Service("OrderGoodsService")
public class OrderGoodsServiceImpl implements OrderGoodsService {
	@Autowired
	private OrderGoodsDao orderGoodsDao;

	@Override
	public List<OrderGoodsEntity> queryList(Map<String, Object> map) {
		return orderGoodsDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return orderGoodsDao.queryTotal();
	}

	@Override
	@Transactional
	public void save(OrderGoodsEntity orderGoods) {
		orderGoodsDao.save(orderGoods);
	}

	@Override
	public void update(OrderGoodsEntity goods) {
		orderGoodsDao.update(goods);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		orderGoodsDao.deleteBatch(ids);
	}

	@Override
	public OrderGoodsEntity queryObject(Long id) {
		return orderGoodsDao.queryObject(id);
	}

	@Override
	public List<OrderGoodsEntity> querySelList(Map<String, Object> map) {
		return orderGoodsDao.querySelList(map);
	}

	@Override
	public int querySelTotal(Map<String, Object> map) {
		return orderGoodsDao.querySelTotal(map);
	}

	@Override
	public List<OrderGoodsHeEntity> queryListO(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return orderGoodsDao.queryListO(map);
	}

	@Override
	public List<GoodsEntity> queryListG(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return orderGoodsDao.queryListG(map);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		orderGoodsDao.delete(id);
	}

	
}
