package com.fengyunjie.railway.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengyunjie.railway.model.Train;
import com.fengyunjie.railway.service.TicketService;
import com.fengyunjie.railway.service.TrainService;

@Controller
@RequestMapping("/admin/train")
public class AdminTrainController {
	@Autowired
	private TrainService trainService;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private TicketService ticketService;

	@RequestMapping("/index")
	public String index(){
		return "admin/train/train";
	}
	
	@RequestMapping("/trainAdjust")
	public String trainAdjust(){
		return "admin/train/trainAdjust";
	}
	
	@RequestMapping("/get/trainInfo")
	public @ResponseBody Object getTrainInfo(int length){
		List<List<String>> list = trainService.getTrainInfo(length);
		return list;
	}
	
	@RequestMapping("/get/allTrain")
	public @ResponseBody Object getAllTrain(){
		List<Train> list = trainService.getAllTrain();
		return list;
	}
	
	@RequestMapping("/add/ticket")
	public @ResponseBody Object addTicket(String trainTag, String trainNo){
		trainService.addTicketByTrainTag(trainTag, trainNo);
		return "success";
	}
	
	@RequestMapping("/add/train")
	public @ResponseBody Object addTrain(String json){
		try {
			Train[] list = mapper.readValue(json, Train[].class);
			for(Train train: list){
				trainService.addTrain(train);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	@RequestMapping("/modify/train")
	public @ResponseBody Object modifyTrain(String json){
		try{
			Train[] list = mapper.readValue(json, Train[].class);
			for(Train train : list){
				trainService.modifyTrain(train);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "success";
	}
	
	@RequestMapping("/del/train")
	public @ResponseBody Object delTrain(String trainNo){
		trainService.delTrain(trainNo);
		
		return "success";
	}
	
	@RequestMapping("/download/train/template")
	public void downloadTrainTemplate(HttpServletResponse response, HttpServletRequest request) throws IOException{
		Resource resource = new ClassPathResource("static/files/车次导入模板.xlsx");
		File file = resource.getFile();
		
		try(OutputStream out = response.getOutputStream(); InputStream in = new FileInputStream(file)) {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			response.setCharacterEncoding("UTF-8"); 
			response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			
			int bytes;
			while((bytes = in.read()) != -1){
				out.write(bytes);
			}
		} catch (Exception e) {
		    e.printStackTrace();;
		}
	}
	
	@RequestMapping("/test")
	public @ResponseBody Object test(){
		ticketService.generateTicket();
		return "success";
	}
	
	@RequestMapping(value="/upload/trainInfo", method=RequestMethod.POST)
	public @ResponseBody Object uploadTrainInfo(@RequestParam("file") MultipartFile file){
		
		String msg = "success";
		List<Train> list = new ArrayList<Train>();
		try {
			InputStream is = file.getInputStream();
			String filename = file.getOriginalFilename();
			if(filename.endsWith(".xlsx") || filename.endsWith(".xls")){
				Workbook wb;
				if(filename.endsWith(".xlsx")){
					wb = new XSSFWorkbook(is);
				}else{
					wb = new HSSFWorkbook(is);
				}
				
				int number = wb.getNumberOfSheets();
				
				for(int i = 0; i < number; i++){
					Sheet sheet = wb.getSheetAt(i);
					int rowNum = sheet.getLastRowNum();
					for(int j=1; j<=rowNum; j++){
						Row row = sheet.getRow(j);	
						Train train = getContract(row);
						list.add(train);
					}
				}
				wb.close();
			}else{
				msg = "文件必须是合法的EXCEL!";
			}
		} catch (IOException e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		trainService.saveTrainList(list);
		
		return msg;
	}
	
	private Train getContract(Row row) {
		Cell cell = row.getCell(0);
		if(cell == null){
			return null;
		}
		Train train = new Train();

		//顺序
		String str = getCellValue(cell);
		train.setStatOrder(Integer.parseInt(str.substring(0, str.indexOf("."))) - 1);
		//车次
		String trainNo = getCellValue(row.getCell(1));
		train.setTrainNo(trainNo);
		//站点
		train.setStationName(getCellValue(row.getCell(2)));
		//开车时间
		train.setStartTime(getCellValue(row.getCell(3)));
		//到达时间
		train.setDeptTime(getCellValue(row.getCell(4)));
		
		//动车
		if(trainNo.startsWith("D")){
			//一等座价格
			train.setPriceFirstClass(Double.parseDouble(getCellValue(row.getCell(9))));
			//一等座票数
			str = getCellValue(row.getCell(10));
			train.setTicketFirstClass(Integer.parseInt(str.substring(0, str.indexOf("."))));
			//二等座价格
			train.setPriceSecondClass(Double.parseDouble(getCellValue(row.getCell(11))));
			//二等座票数
			str = getCellValue(row.getCell(12));
			train.setTicketSecondClass(Integer.parseInt(str.substring(0, str.indexOf("."))));
			//站票价格
			train.setPriceStand(Double.parseDouble(getCellValue(row.getCell(23))));
			//站票票数
			str = getCellValue(row.getCell(24));
			train.setTicketStand(Integer.parseInt(str.substring(0, str.indexOf("."))));
		}  //高铁 
		else if (trainNo.startsWith("G")){
			//商务座价格
			train.setPriceBusiness(Double.parseDouble(getCellValue(row.getCell(5))));
			//商务座票数
			str = getCellValue(row.getCell(6));
			train.setTicketBusiness(Integer.parseInt(str.substring(0, str.indexOf("."))));
			//特等座价格
			train.setPriceSpecial(Double.parseDouble(getCellValue(row.getCell(7))));
			//特等座票数
			str = getCellValue(row.getCell(8));
			train.setTicketBusiness(Integer.parseInt(str.substring(0, str.indexOf("."))));
			//一等座价格
			train.setPriceFirstClass(Double.parseDouble(getCellValue(row.getCell(9))));
			//一等座票数
			str = getCellValue(row.getCell(10));
			train.setTicketFirstClass(Integer.parseInt(str.substring(0, str.indexOf("."))));
			//二等座价格
			train.setPriceSecondClass(Double.parseDouble(getCellValue(row.getCell(11))));
			//二等座票数
			str = getCellValue(row.getCell(12));
			train.setTicketSecondClass(Integer.parseInt(str.substring(0, str.indexOf("."))));
			//站票价格
			train.setPriceStand(Double.parseDouble(getCellValue(row.getCell(23))));
			//站票票数
			str = getCellValue(row.getCell(24));
			train.setTicketStand(Integer.parseInt(str.substring(0, str.indexOf("."))));
		}
		
		return train;
	}
	
	private String getCellValue(Cell cell) {  
        String cellValue = "";
        
        if(cell == null){
        	return null;
        }
        
        switch (cell.getCellType()) {
	        case HSSFCell.CELL_TYPE_STRING:
	            cellValue = cell.getRichStringCellValue().getString();
	            break;  
	        case HSSFCell.CELL_TYPE_NUMERIC:
	            cellValue = String.valueOf(cell.getNumericCellValue());
	            break;  
	        case HSSFCell.CELL_TYPE_BOOLEAN:
	            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
	            break;  
	        case HSSFCell.CELL_TYPE_FORMULA:
	            cellValue = cell.getCellFormula();
	            break;
	        default:
	            cellValue = cell.getStringCellValue();
        }
        return cellValue;
    } 
}
