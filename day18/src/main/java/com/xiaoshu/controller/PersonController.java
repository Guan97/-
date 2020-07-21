package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

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
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.CompanyService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("person")
public class PersonController extends LogController{
	static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("personIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Company> companyList = companyService.findCompany();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("companyList", companyList);
		return "person";
	}
	
	
	@RequestMapping(value="personList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,PersonVo personVo) throws Exception{
		try {
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<PersonVo> page= personService.findUserPage(personVo,pageNum,pageSize,ordername,order);
			
			
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
		@RequestMapping("reservePerson")
		public void reserveUser(HttpServletRequest request,User user,HttpServletResponse response,Person person){
			Integer id = person.getId();
			JSONObject result=new JSONObject();
			person.setCreatetime(new Date());
			try {
				if (id != null) {   // userId不为空 说明是修改
					
					
						person.setId(id);
						personService.updatePerson(person);
						result.put("success", true);
					
					
				}else {   // 添加
					// 没有重复可以添加
						personService.addPerson(person);
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
		

		@RequestMapping("deletePerson")
		public void delUser(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String[] ids=request.getParameter("ids").split(",");
				for (String id : ids) {
					personService.deleteUser(Integer.parseInt(id));
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
		
		@RequestMapping("echartsPerson")
		public void echartsPerson(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				List<PersonVo> list=personService.echartsPerson();
				result.put("success", true);
				result.put("data", list);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		
		@RequestMapping("downPerson")
		public void downPerson(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
				HSSFSheet sheet = wb.createSheet();//第一个sheet
				HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
				String [] handers={"人员编号","人员名","性别","特点","入职时间","创建时间","公司"};
				for (int i = 0; i < handers.length; i++) {
					rowFirst.createCell(i).setCellValue(handers[i]);
				}
				List<PersonVo> list=personService.findAll(new PersonVo());
				for (int i = 0; i < list.size(); i++) {
					PersonVo personVo = list.get(i);
					HSSFRow row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(personVo.getId());
					row.createCell(1).setCellValue(personVo.getName());
					row.createCell(2).setCellValue("0".equals(personVo.getSex())?"男":"女");
					row.createCell(3).setCellValue(personVo.getTrait());
					row.createCell(4).setCellValue(TimeUtil.formatTime(personVo.getEntrytime(), "yyyy-MM-dd"));
					row.createCell(5).setCellValue(TimeUtil.formatTime(personVo.getCreatetime(), "yyyy-MM-dd"));
					row.createCell(6).setCellValue(personVo.getCname());
				}
				OutputStream os;
				File file = new File("F:\\aaa\\快递人员导出.xls");
				
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
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		
		@RequestMapping("importPerson")
		public void importPerson(HttpServletRequest request,HttpServletResponse response,MultipartFile importFile){
			JSONObject result=new JSONObject();
			try {
				Workbook workbook = WorkbookFactory.create(importFile.getInputStream());
				Sheet sheet = workbook.getSheetAt(0);
				int lastRowNum = sheet.getLastRowNum();
				for (int i = 0; i < lastRowNum; i++) {
					Row row = sheet.getRow(i+1);
					String name = row.getCell(0).toString();
					String sex = row.getCell(1).toString();
					String trait = row.getCell(2).toString();
					Date entrytime = row.getCell(3).getDateCellValue();
					
					String cname = row.getCell(5).toString();
					if(sex.equals("男")&&cname.equals("京东快递")){
						Company company=companyService.findByName(cname);
						
						Person person=new Person();
						person.setName(name);
						person.setSex(sex.equals("男")?"0":"1");
						person.setTrait(trait);
						person.setEntrytime(entrytime);
						person.setCreatetime(new Date());
						person.setCid(company.getCid());
						personService.addPerson(person);
					}
					
					
				}
				
				
				result.put("success", true);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		
		
		
}
