package com.xiaoshu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Type;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.GoodsService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.TypeService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("goods")
public class GoodsController {
	static Logger logger = Logger.getLogger(GoodsController.class);

	@Autowired
	private TypeService ts;
	
	@Autowired
	private GoodsService gs;
	
	@Autowired
	private OperationService operationService;
	
	@RequestMapping("goodsIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Type> typeList = ts.selectType();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("typeList", typeList);
		return "goods";
	}
	
	@RequestMapping(value="goodsList",method=RequestMethod.POST)
	public void userList(Goods goods,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Goods> page= gs.selectGoods(goods,pageNum,pageSize);
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",page.getTotal() );
			jsonObj.put("rows", page.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	// 新增或修改
		@RequestMapping("reserveGoods")
		public void reserveUser(HttpServletRequest request,Goods goods,HttpServletResponse response){
			Integer gid = goods.getGid();
			JSONObject result=new JSONObject();
			try {
				if (gid != null) {   // userId不为空 说明是修改
					gs.updateGoods(goods);
					result.put("success", true);
				}else {   // 添加
					if(gs.existGoodsWithUserGname(goods.getGname())==null){  // 没有重复可以添加
						gs.addGoods(goods);
						result.put("success", true);
					} else {
						result.put("success", true);
						result.put("errorMsg", "该学生姓名被使用");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		@RequestMapping("deleteGoods")
		public void delUser(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String[] ids=request.getParameter("ids").split(",");
				for (String id : ids) {
					gs.deleteGoods(Integer.parseInt(id));
				}
				result.put("success", true);
				result.put("delNums", ids.length);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		@RequestMapping("reserveType")
		public void reserveType(Type type,HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				ts.addType(type);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("添加社区信息错误",e);
				result.put("errorMsg", "对不起，添加失败");
			}
			WriterUtil.write(response, result.toString());
		}
}
