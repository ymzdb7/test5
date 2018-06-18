package com.winhands.modules.market.controller;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winhands.common.utils.PageUtils;
import com.winhands.common.utils.Query;
import com.winhands.common.utils.R;
import com.winhands.common.validator.ValidatorUtils;
import com.winhands.common.validator.group.AddGroup;

import com.winhands.modules.market.entity.TypeEntity;

import com.winhands.modules.market.service.TypeService;
import com.winhands.modules.sys.controller.AbstractController;


/**
 * 商品类型管理
 * 
 */
@RestController
@RequestMapping("/market/type")
public class TypeController extends AbstractController {
	@Autowired
	private TypeService typeService;

	/**
	 * 所有商品列表
	 */

	@RequestMapping("/list")
	/*@RequiresPermissions("market:goods:list")*/
	public R list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<TypeEntity> typeList = typeService.queryList(query);
		int total = typeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(typeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	
	
	

	/**
	 * 保存商品类型
	 */
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public R save(TypeEntity type){
		
		ValidatorUtils.validateEntity(type, AddGroup.class);
	
		typeService.save(type);
		
		
		return R.ok();
	}
	
	
	
	/**
	 * 修改商品类型
	 */
	@ResponseBody
	@RequestMapping(value="/update" , method=RequestMethod.POST)
	public R update(TypeEntity type){
		ValidatorUtils.validateEntity(type, AddGroup.class);
		typeService.update(type);
		return R.ok();
	}
	
	/**
	 * 商品信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id")String id){
		TypeEntity type = typeService.queryObject(Long.parseLong(id));
	return R.ok().put("type", type);
	}
	
	
	
	
	/**
	 * 删除商品 类型
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		typeService.deleteBatch(ids);
		
		return R.ok();
	}
	
	
}
	

