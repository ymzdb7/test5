package com.winhands.modules.market.entity;

import java.io.Serializable;
import java.math.BigDecimal;
public class OrderGoodsHeEntity implements Serializable{
	/**
	 * 订单商品关联信息
	 */
	private static final long serialVersionUID = 1L;
	private String goodsName;//商品名
	private Integer goodsCount;//数量 
	private BigDecimal goodsPrice;//商品销售当时价格
	private BigDecimal totalPrice;
	
	public void setTotalPrice() {
		if(goodsPrice!=null) {
		this.totalPrice = goodsPrice.multiply(new BigDecimal(goodsCount));
		}
	}

	public BigDecimal getTotalPrice() {
		BigDecimal aa=null;
		if(goodsPrice!=null) {
			aa=goodsPrice.multiply(new BigDecimal(goodsCount));
		}
		return  aa;
		
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	
	
	
}
