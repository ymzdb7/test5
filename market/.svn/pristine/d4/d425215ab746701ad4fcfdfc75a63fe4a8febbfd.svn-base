package com.winhands.modules.market.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.winhands.common.annotation.SysLog;
import com.winhands.common.utils.Constant;
import com.winhands.common.utils.DateUtil;
import com.winhands.common.utils.PageUtils;
import com.winhands.common.utils.Query;
import com.winhands.common.utils.R;
import com.winhands.common.utils.StringUtil;
import com.winhands.common.validator.ValidatorUtils;
import com.winhands.common.validator.group.AddGroup;
import com.winhands.modules.market.entity.CalculateEntity;
import com.winhands.modules.market.entity.GoodsEntity;
import com.winhands.modules.market.entity.OrderEntity;
import com.winhands.modules.market.entity.OrderGoodsEntity;
import com.winhands.modules.market.entity.TypeEntity;
import com.winhands.modules.market.service.CalculateService;
import com.winhands.modules.market.service.GoodsService;
import com.winhands.modules.market.service.OrderGoodsService;
import com.winhands.modules.market.service.OrderService;
import com.winhands.modules.market.service.TypeService;
import com.winhands.modules.sys.controller.AbstractController;
import com.winhands.modules.sys.entity.SysUserEntity;

/**
 * 商品管理
 * 
 */



@RestController
@RequestMapping("/market/goods")
public class GoodsController extends AbstractController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderGoodsService orderGoodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private CalculateService calculateService;
	
	/**
	 * 所有商品列表
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("market:goods:list")*/
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		/*String a=(String)getUser().getAddress();
		
		if(null!=getUser().getAddress()&&!getUser().getAddress().trim().equals("")) {
		}else {
			params.put("address", getUser().getAddress());
		}*/
		if(getUser()!=null&&getUser().getAddress()!=null&&!getUser().getAddress().trim().equals("")) {
			params.put("address", getUser().getAddress());
		}
		params.put("deleteState", "0");
		Query query = new Query(params);
		List<GoodsEntity> goodsList = goodsService.queryList(query);
		int total = goodsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(goodsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	
	
	/**
	 * 保存商品
	 */
	@SysLog("保存商品")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public R save(GoodsEntity goods,@RequestParam("pic") MultipartFile pic,HttpServletRequest request){

		ValidatorUtils.validateEntity(goods, AddGroup.class);
		if(getUser().getAddress()!=null&&!StringUtil.isNull(getUser().getAddress().trim())&&!getUser().getAddress().equals(goods.getAddress())) {
			return R.error("请选择您的属地："+getUser().getAddress());
		}else {
		
		goods.setGoodsPrice(new BigDecimal(goods.getGoodsPrice1()));
		/*SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); */ 
		/*String goodsSaleStarttime1="";
		String goodsSaleEndtime1="";
		if(goods.getGoodsSaleStarttime1().length()==10) {
			
			goodsSaleStarttime1=goods.getGoodsSaleStarttime1()+" 00:00:00";
			
		}else {
			 goodsSaleStarttime1=goods.getGoodsSaleStarttime1().replace("T", " ")+":00";
		}
		if(goods.getGoodsSaleEndtime1().length()==10) {
			
			goodsSaleEndtime1=goods.getGoodsSaleEndtime1()+" 00:00:00";
		}else {
			goodsSaleEndtime1=goods.getGoodsSaleEndtime1().replace("T", " ")+":00";
		}*/
		
		

		
		
/*		goods.setGoodsSaleStarttime(Timestamp.valueOf(goodsSaleStarttime1));
		goods.setGoodsSaleEndtime(Timestamp.valueOf(goodsSaleEndtime1));*/
		
		goods.setGoodsCreatetime(new Date());
		if(goods.getGoodsSaleStarttime1()!=null&&goods.getGoodsSaleEndtime1()!=null) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			goods.setGoodsSaleStarttime(sdf.parse(goods.getGoodsSaleStarttime1()));
			goods.setGoodsSaleEndtime(sdf.parse(goods.getGoodsSaleEndtime1()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		int cc=	Integer.parseInt(goods.getTypeId1());
		goods.setTypeId(cc);
		String filePath = "";
		String uuid = StringUtil.getUUIDString();
		String type = "";
		File file = null;
		String result = "";
		String htmlPath = Constant.HTMLUploadPath+ DateUtil.getCurrentMonth() + "/goods/";
		goods.setGoodsUrl(Constant.HTMLUploadPath+ DateUtil.getCurrentMonth() + "/goods/");
		String fileName = pic.getOriginalFilename();
		if (pic!= null &&!StringUtil.isNull(fileName)) { 
			filePath = Constant.IMAGEUPLOADPATH+ DateUtil.getCurrentMonth() + "/goodsPic/";//菜品图片
			type = pic.getOriginalFilename().substring(fileName.indexOf(".") + 1, fileName.length()); 
			fileName = uuid+"." +type;
			goods.setGoodsPicName(pic.getOriginalFilename());
			goods.setGoodsPicPath(Constant.IMAGEUPLOADPATH+ DateUtil.getCurrentMonth() + "/goodsPic/"+fileName);
		    result = copyFile(request, filePath, fileName, pic);
      	} 
		goodsService.save(goods);
		saveAsHtmlAndTxt(request, goods.getId(), htmlPath, goods);
		return R.ok();
		}
	}
	
	
	/**
	 * 修改商品
	 */
	@SysLog("修改商品")
	@RequestMapping(value="/update" , method=RequestMethod.POST)
	public R update(GoodsEntity goods, @RequestParam("pic") MultipartFile pic,HttpServletRequest request){
		ValidatorUtils.validateEntity(goods, AddGroup.class);
		/*if(goods.getGoodsSaleStarttime1().length()==16) {
		String goodsSaleStarttime1=goods.getGoodsSaleStarttime1().replace("T", " ")+":00";
		goods.setGoodsSaleStarttime(Timestamp.valueOf(goodsSaleStarttime1));
		}
		if(goods.getGoodsSaleEndtime1().length()==16) {
		String goodsSaleEndtime1=goods.getGoodsSaleEndtime1().replace("T", " ")+":00";
		goods.setGoodsSaleEndtime(Timestamp.valueOf(goodsSaleEndtime1));
		}*/
		
		if(getUser().getAddress()!=null&&!StringUtil.isNull(getUser().getAddress().trim())&&!getUser().getAddress().equals(goods.getAddress())) {
			return R.error("请选择您的属地："+getUser().getAddress());
		}else {
			if(goods.getGoodsSaleStarttime1()!=null&&goods.getGoodsSaleEndtime1()!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				goods.setGoodsSaleStarttime(sdf.parse(goods.getGoodsSaleStarttime1()));
				goods.setGoodsSaleEndtime(sdf.parse(goods.getGoodsSaleEndtime1()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		goods.setTypeId1(Integer.toString(goods.getTypeId()));
		
		//判断是否有新图片
		String fileName = pic.getOriginalFilename();
		String filePath = "";
		String uuid = StringUtil.getUUIDString();
		String type = "";
		File file = null;
		String result = "";
		//
		GoodsEntity goodsTemp = goodsService.queryObject((long)goods.getId());
		if (pic!= null &&!StringUtil.isNull(fileName)) { 
			//先删除原照片文件
		if(goodsTemp.getGoodsPicPath()!=null) {
				File f = new File(goodsTemp.getGoodsPicPath());
			
			
			if(f.exists())  f.delete();
		}
			//保存新图片
			filePath = Constant.IMAGEUPLOADPATH+ DateUtil.getCurrentMonth() + "/goodsPic/";//菜品图片
			type = pic.getOriginalFilename().substring(fileName.indexOf(".") + 1, fileName.length()); 
			fileName = uuid+"." +type;
			goods.setGoodsPicName(pic.getOriginalFilename());
			goods.setGoodsPicPath(Constant.IMAGEUPLOADPATH+ DateUtil.getCurrentMonth() + "/goodsPic/"+fileName);
		    result = copyFile(request, filePath, fileName, pic);
      	}else {
      		
      	}
		
		//删除原先html文件
		File f = new File(goodsTemp.getGoodsUrl()+goods.getId()+".html");
		if(f.exists()) f.delete();
		goods.setGoodsPrice(new BigDecimal(goods.getGoodsPrice1()));
		String cc=	goods.getTypeName().trim();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", cc);
		TypeEntity type1=typeService.queryObject1(map);
		goods.setTypeId(type1.getTypeId());
		goodsService.update(goods);
		saveAsHtmlAndTxt(request, goods.getId(), goodsTemp.getGoodsUrl(), goods);
		
		return R.ok();
		}
	}
	
	
	/**
	 * 商品信息
	 */
	@RequestMapping("/info/{id}")
	/*@RequiresPermissions("sys:user:info")*/
	public R info(@PathVariable("id")String id){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		GoodsEntity goods = goodsService.queryObject(Long.parseLong(id));
		goods.setGoodsPrice1(goods.getGoodsPrice().toString());
		if(goods.getGoodsSaleStarttime()!=null) {
		goods.setGoodsSaleStarttime1(sdf.format(goods.getGoodsSaleStarttime()));
		}
				if(goods.getGoodsSaleEndtime()!=null) {
		goods.setGoodsSaleEndtime1(sdf.format(goods.getGoodsSaleEndtime()));
				}
		/*DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		goods.setGoodsSaleStarttime1(sdf.format(goods.getGoodsSaleStarttime()).replace(" ", "T"));
		goods.setGoodsSaleEndtime1(sdf.format(goods.getGoodsSaleEndtime()).replace(" ", "T"));*/
	return R.ok().put("goods", goods);
	}
	
	
	
	
	
	
	

	
	private String copyFile(HttpServletRequest request, String realpath, String saveName, MultipartFile file) {
		// 数据流方式上传文件
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			logger.debug("上传路径.."+realpath);
			File dirFile = new File(realpath);
			if (!dirFile.isDirectory()) {// 目录月份目录不存在
				dirFile.mkdirs();// 创建目录
			}
			// 建立文件输出流 
			fos = new FileOutputStream(realpath + "/" + saveName);
			// 建立文件上传流
			try {
				fis =(FileInputStream) file.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.error("文件上传失败");
			e.printStackTrace();
			return "error";
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("FileInputStream关闭失败");
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("FileOutputStream关闭失败");
					e.printStackTrace();
				}
			}
		}
		return "success";
	}
	
	private String saveAsHtmlAndTxt(HttpServletRequest request, long news_id,
			String htmlPath, GoodsEntity goods) {
		String path = request.getContextPath();
		String jsPath = request.getServerName();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File dirFile = new File(htmlPath);
		if (!dirFile.isDirectory()) {// 目录月份目录不存在
			dirFile.mkdirs();// 创建目录
		}

		// 生成静态网页 区分两种，第一种html展示
		StringBuffer newContentStr = new StringBuffer();
		newContentStr
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
				.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").append("<head>")
				.append("<meta http-equiv=Content-Type content=\'text/html;charset=utf-8;\'/>")
				.append("<meta id='viewport' name='viewport' content='width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1;\'/>")
				.append("<script src='" + basePath + "js/jquery-1.10.2.min.js'></script>").append("<title>")
				.append(goods.getGoodsName()).append("</title>").append("</head>").append("<style>")
				.append("img{width='100%';height=''}").append("</style>")
				.append("<body  style='padding-left:10px;padding-right:10px;>'");
		// 拼接正文
		newContentStr.append("<div id='content'>");
		newContentStr.append(goods.getGoodsIntroduce()).append("</div>");
		// $('video').click(function(){
		newContentStr.append("<div class=\"clear\"></div>").append("</body>").append("<script type='text/javascript'>")
				.append("window.onload=function(){ ")
				.append("var imgs=document.getElementsByTagName('img');").append("for(var i=0;i<imgs.length;i++){")
				.append("var imgTh=imgs[i];").append("imgTh.style.width='100%';").append("imgTh.style.height='100%';")
				.append("} ").append("} ")

				.append("</script>").append("</html>");
		byte[] buff = new byte[] {};
		String htmlName = news_id + ".html";
		FileOutputStream outputStream = null;
		try {
			buff = newContentStr.toString().getBytes("UTF-8");
			outputStream = new FileOutputStream(htmlPath + "/" + htmlName);
			outputStream.write(buff, 0, buff.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					System.out.println("outputStream关闭失败");
					e.printStackTrace();
				}
			}
		}
		return "success";
	}
	
	

	
	
	
	
	
	/**
	 * 删除商品
	 */
	@SysLog("删除商品")
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		/*goodsService.deleteBatch(ids);*/
		goodsService.updateBatch(ids);
		
		return R.ok();
	}
	
	/**
	 * 客户端获取商品列表
	 */
	@RequestMapping(value="/goodsList" , method = RequestMethod.POST)
	public R goodsList(String address){
	Map<String,Object> goodsMap=new LinkedHashMap<String, Object>();
	List<Map<String,Object>>  mapm=new ArrayList<Map<String,Object>>();	
	Map<String, Object> map=new HashMap<String,Object>();
	
	map.put("address", address);
	List<TypeEntity> typeList=typeService.queryListYes(map);
	
if(typeList!=null&&typeList.size()!=0) {
	for (int j = 0; j < typeList.size(); j++) {
	Map<String, Object> map1=new LinkedHashMap<String, Object>();	
		map1.put("typeId", typeList.get(j).getTypeId());
		map1.put("text",  typeList.get(j).getTypeName());
		map.put("typeId", typeList.get(j).getTypeId());
		map.put("goodsStatus", 2);
		map.put("deleteState", "0");
		List<GoodsEntity> detail=goodsService.queryList(map);
		if(null!=detail||detail.size()!=0) { 
			map1.put("detail", detail);
			mapm.add(map1);
			}
		goodsMap.put("mapm", mapm);	
}
	}

	return R.ok().put("goodsMap", goodsMap);
	}
	
	/**
	 * 客户端提交订单接口,其中ids是多个商品id,counts是对应的数量
	 */
	@RequestMapping(value="/goodsListSave" , method = RequestMethod.POST)
	public R goodsListSave(Long[] ids,Long[] counts,String[] goodsPrice){//参数依次为：对应商品id,数量，单价
		
		//判断在客户下单的时候，后台管理员是否改动商品价格，如改动，则客户不可下单
			boolean cg=false;
			boolean ymz=false;
			boolean db7=false;
			boolean  db8=false;
			boolean db9=false;
			
			
			int totalCounts=0;//初始化总数量，后面作判断用
			int counts1=0;//初始化下单数量，后面与总数量作比较
			String goodsName=null;
			Date st=null;
			Date et=null;
			for (int i = 0; i < ids.length; i++) {
				GoodsEntity goods=goodsService.queryObject(ids[i]);
				 st=goods.getGoodsSaleStarttime();
				 et=goods.getGoodsSaleEndtime();	
				int cccc=goods.getGoodsStatus();
				String  dddd=goods.getDeleteState();
				double gg=new BigDecimal(goodsPrice[i]).doubleValue();
				double g1g=goods.getGoodsPrice().doubleValue();
				 totalCounts=goods.getCounts();
				 counts1=new Long(counts[i]).intValue();
				 goodsName=goods.getGoodsName();
				 
				/*new BigDecimal(goodsPrice[i])!=goods.getGoodsPrice()*/
				//判断商品价格是否有变动
				 if(gg!=g1g) {
					cg=true;
					break;
					}
				 
				//判断下单商品数量是否大于库存数量
				if(counts1>totalCounts) {
					ymz=true;
					break;
				}
				
				if(cccc==1) {
					db7=true;
					break;
				}
				
				if(dddd.equals("1")) {
					db8=true;
					break;
				}
				
				Date date=new Date();
				if(date.before(st)||date.after(et)) {
					db9=true;
					break;
				}
				
				}
		
			if(db7){
				return  R.error(goodsName+"该商品已下架！");
			}else if(db9) {
				return  R.error(goodsName+"该商品不在销售时间内！");
			}else if(db8) {
				return  R.error(goodsName+"该商品已删除！");
			} else if(cg) {
			return  R.error("商品价格有变动，请您重新下订单！");
			}else if(ymz){
			return  R.error(goodsName+"下单数量大于库存数量，请您重新下订单！");
			}else{
			int id=0;
				try {
				
				SysUserEntity user=getUser();
				String uuid=getOrderIdByUUId();
			
				id=orderService.saveOo(ids, counts, goodsPrice, user,uuid, id);
				
				} catch (Exception e) {
					// TODO: handle exception
					return R.error("下单失败！");
				}
				return R.ok("下单成功!").put("orderId", id);
				}
	}	
	

	
	  public static String getOrderIdByUUId() {
		  
       /*  
  		String uuid=UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
  		String ranEight=String.format("%08d", new Random().nextInt(99999999));
  		String ccbc=dateTime+uuid+ranEight;*/
		  
		  
		  /*  
		  int machineId = 1;//最大支持1-9个集群机器部署
          int hashCodeV = UUID.randomUUID().toString().hashCode();
          if(hashCodeV < 0) {//有可能是负数
              hashCodeV = - hashCodeV;
          }
          // 0 代表前面补充0     
          // 4 代表长度为4     
          // d 代表参数为正数型
           * 
          return machineId + String.format("%015d", hashCodeV);
          */
		  
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	  		String dateTime	=sdf.format(new Date());
		  return dateTime;
  	     
      }
	
	
		/**
		 * 商品信息
		 */
		@RequestMapping("/infoType")
		/*@RequiresPermissions("sys:user:info")*/
		public R infoType(){
		Map<String,Object> map=new HashMap<String,Object>();
			List<TypeEntity> type=typeService.queryList(map);
			return R.ok().put("type", type);
		}
	
		/**
		 * 后台获取所有计量单位
		 */
		@RequestMapping("/infoCalculate")
		/*@RequiresPermissions("sys:user:info")*/
		public R infoCalculate(){
		
		Map<String,Object> map=new HashMap<String,Object>();
			List<CalculateEntity> calculate=calculateService.queryList(map);
			return R.ok().put("calculate", calculate);
		}
		
}
	

