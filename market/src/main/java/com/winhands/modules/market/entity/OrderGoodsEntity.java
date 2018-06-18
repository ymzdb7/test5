package com.winhands.modules.market.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Timestamp;

public class OrderGoodsEntity implements Serializable{

	/**
	 * 订单商品关联信息
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//id
	private Integer goodsId;//商品id
	private Integer orderId;//订单id
	private Integer count;//数量 
	private BigDecimal goodsPrice;//商品销售当时价格
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	

	
	
	
	
	
	
	
}
