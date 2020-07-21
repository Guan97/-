package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeptService;
import com.xiaoshu.service.EmpService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("emp")
public class Empcontroller extends LogController{
	
	static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private JedisPool jedisPool;
	
	
	
	@RequestMapping("empIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Dept> deptList = deptService.findDept();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("deptList", deptList);
		return "emp";
	}
	
	
	@RequestMapping(value="empList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,EmpVo empVo) throws Exception{
		try {
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<EmpVo> page= empService.findEmpPage(empVo,pageNum,pageSize,ordername,order);
			
			
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
		@RequestMapping("reserveEmp")
		public void reserveUser(HttpServletRequest request,User user,HttpServletResponse response,Emp emp,MultipartFile photoFile) throws IllegalStateException, IOException{
			Integer eId = emp.getId();
			JSONObject result=new JSONObject();
			//文件上传
			String filename = photoFile.getOriginalFilename();
			if(filename!=null){
				String realPath = request.getServletContext().getRealPath("/");
				String path=realPath+"/img/";
				File pathFile=new File(path);
				if(!pathFile.exists()){
					pathFile.mkdir();
				}
				String newName=UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));
				photoFile.transferTo(new File(path+newName));
				emp.setPhoto("../img/"+newName);
			}
			try {
				Emp ename = empService.existUserWithUserName(emp);
				if (eId != null) {   // userId不为空 说明是修改
					if(ename==null){
						user.setUserid(eId);
						empService.updateEmp(emp);
						result.put("success", true);
					}else{
						result.put("success", true);
						result.put("errorMsg", "该用户名被使用");
					}
					
				}else {   // 添加
					if(ename==null){  // 没有重复可以添加
						empService.addEmp(emp);
						result.put("success", true);
					} else {
						result.put("success", true);
						result.put("errorMsg", "该用户名被使用");
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
		@RequestMapping("deleteEmp")
		public void delUser(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String[] ids=request.getParameter("ids").split(",");
				for (String id : ids) {
					empService.deleteEmp(Integer.parseInt(id));
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
		
		
		@RequestMapping("downEmp")
		public void downEmp(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
				HSSFSheet sheet = wb.createSheet();//第一个sheet
				HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
				String [] handers={"员工编号","员工名","员工性别","年龄","生日","头像","部门"};
				for (int i = 0; i < handers.length; i++) {
					rowFirst.createCell(i).setCellValue(handers[i]); 
				}
				List<EmpVo> list=empService.findEmpAll(new EmpVo());
				for (int i = 0; i < list.size(); i++) {
					EmpVo empVo = list.get(i);
					HSSFRow row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(empVo.getId());
					row.createCell(1).setCellValue(empVo.getName());
					row.createCell(2).setCellValue("1".equals(empVo.getSex())?"男":"女");
					row.createCell(3).setCellValue(empVo.getAge());
					row.createCell(4).setCellValue(TimeUtil.formatTime(empVo.getBirthday(), "yyyy-MM-dd"));
					row.createCell(5).setCellValue(empVo.getPhoto());
					row.createCell(6).setCellValue(empVo.getDname());
					
				}
				//写出文件（path为文件路径含文件名）
				OutputStream os;
				File file = new File("F:\\aaa\\人员管理.xls");
				
				if (!file.exists()){//若此目录不存在，则创建之  
					file.createNewFile();  
					logger.debug("创建文件夹路径为："+ file.getPath());  
	            } 
				os = new FileOutputStream(file);
				wb.write(os);
				os.close();
				
				
				result.put("success", true);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出用户信息错误",e);
				result.put("errorMsg", "对不起，导出失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		@RequestMapping("importEmp")
		public void importEmp(MultipartFile importFile,HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				Workbook wb = WorkbookFactory.create(importFile.getInputStream());
				Sheet sheet = wb.getSheetAt(0);
				int lastRowNum = sheet.getLastRowNum();
				for (int i = 0; i < lastRowNum; i++) {
					Row row = sheet.getRow(i+1);
					String name = row.getCell(1).toString();
					String sex = row.getCell(2).toString();
					String age = row.getCell(3).toString();
					Date birthday = TimeUtil.ParseTime(row.getCell(4).getStringCellValue(), "yyyy-MM-dd");
					String photo = row.getCell(5).toString();
					String dname = row.getCell(6).toString();
					Dept dept=deptService.findByname(dname);
					
					Emp emp=new Emp();
					emp.setName(name);
					emp.setSex(sex);
					emp.setAge(age);
					emp.setBirthday(birthday);
					emp.setPhoto(photo);
					emp.setDid(dept.getDid());
					
					empService.addEmp(emp);
				}
				
				result.put("success", true);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导入用户信息错误",e);
				result.put("errorMsg", "对不起，导入失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		// 新增或修改
		@RequestMapping("reserveDept")
		public void reserveDept(HttpServletRequest request,User user,HttpServletResponse response,Dept dept){
			Integer dId = dept.getDid();
			JSONObject result=new JSONObject();
			try {
				if (dId != null) {   // userId不为空 说明是修改
					/*User userName = userService.existUserWithUserName(user.getUsername());
					if(userName != null && userName.getUserid().compareTo(userId)==0){
						user.setUserid(userId);
						userService.updateUser(user);
						result.put("success", true);
					}else{
						result.put("success", true);
						result.put("errorMsg", "该用户名被使用");
					}*/
					
				}else {   // 添加
					  // 没有重复可以添加
						deptService.addDept(dept);
						
					    Dept dept2=new Dept();
					    dept2=dept;
					    String string = JSON.toJSONString(dept);
					    Jedis jedis = jedisPool.getResource();
					    jedis.hset(dept2.getDid()+"", "内容", string);
					    result.put("success", true);
						
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		@RequestMapping("echartsEmp")
		public void echartsEmp(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				List<EmpVo> list=empService.echartsEmp();
				result.put("success", true);
				result.put("data", list);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		
}
