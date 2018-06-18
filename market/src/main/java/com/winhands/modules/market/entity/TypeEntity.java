package com.winhands.modules.market.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.winhands.common.validator.group.AddGroup;
import com.winhands.common.validator.group.UpdateGroup;
public class TypeEntity implements Serializable{
	/**
	 * 商品类型管理
	 */
	private static final long serialVersionUID = 1L;
	private Integer typeId;//商品id
	
	@NotBlank(message="商品名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String typeName;//商品名称
	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
