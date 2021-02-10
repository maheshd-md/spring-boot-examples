package com.filehandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
public class FileController<T> {

	public List<?> dataList = null;
	public Field[] fields = null;
	public Map<String, List<Object>> xlsMap = null;
	public Class cls = null;

	@PostMapping(value = "/upload/{category}", consumes = "multipart/form-data")
	public ResponseEntity<FileUploadResponse> uploadMultipart(@PathVariable("category") String category,
			@RequestParam("file") MultipartFile file) {

		List<String> columns = new ArrayList<>();
		xlsMap = new HashedMap();
		try {
			dataList = new ArrayList<>();
			if ("canalData".equals(category)) {
				dataList = (List<CanalDataDto>) CsvUtils.read(CanalDataDto.class, file.getInputStream());
				cls = CanalDataDto.class;
			}
			if ("rainData".equals(category)) {
				dataList = (List<RainDataDto>) CsvUtils.read(RainDataDto.class, file.getInputStream());
				cls = RainDataDto.class;
			}
			fields = cls.getDeclaredFields();
			for (Field field : fields) {
				if (Double.class.equals(field.getType()) || Long.class.equals(field.getType())
						|| Boolean.class.equals(field.getType())) {
					columns.add(field.getName());
				}

				// Put field name as key and values of that field in map
				List<Object> lst = new ArrayList<>();
				xlsMap.put(field.getName(), lst);
				dataList.forEach(data -> {
					try {
						lst.add(field.get(data));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
			new ResponseEntity<FileUploadResponse>(new FileUploadResponse(0, "Error", columns), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FileUploadResponse>(
				new FileUploadResponse(dataList.size(), file.getOriginalFilename(), columns), HttpStatus.OK);
	}

//	@GetMapping(value = "/filter/{columnName}/{value}")
	@SuppressWarnings("unchecked")
	public ResponseEntity<List> filter(@PathVariable("columnName") String columnName,
			@PathVariable("value") String value) {

		List<Object> filteredList = null;
		try {
			for (Field field : fields) {
				if (columnName.equals(field.getName())) {
					Class fieldType = field.getType();
					if (Boolean.class.equals(fieldType)) {
						Boolean filterValue = "1".equals(value);
						filteredList = dataList.stream().filter(data -> {
							try {
								return field.get(data).equals(filterValue);
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}
						}).collect(Collectors.toList());
						continue;
					} else if (Long.class.equals(fieldType)) {
						Long filterValue = Long.valueOf(value);
						filteredList = dataList.stream().filter(data -> {
							try {
								return ((Long) field.get(data)) >= filterValue;
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}
						}).collect(Collectors.toList());
						continue;
					} else if (Double.class.equals(fieldType)) {
						Double filterValue = Double.valueOf(value);
						filteredList = dataList.stream().filter(data -> {
							try {
								return ((Double) field.get(data)) >= filterValue;
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}
						}).collect(Collectors.toList());
						continue;
					} /*
						 * else if(Date.class.equals(fieldType)) { String[] date = value.split("-");
						 * Date filterValue = new Date(Integer.valueOf(date[2]),
						 * Integer.valueOf(date[1]), Integer.valueOf(date[0])); filteredList =
						 * canalDataList.stream().filter(canalDataDto -> { try { return
						 * ((Date)field.get(canalDataDto)).after(filterValue); } catch (Exception e) {
						 * e.printStackTrace(); return false; } }).collect(Collectors.toList());
						 * continue; }
						 */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List>(filteredList, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List>(filteredList, HttpStatus.OK);
	}

	@GetMapping(value = "/filter")
	public void downloadFiltereCsv(@RequestParam(value = "column") String columnName, @RequestParam("filterValue") String value,
			HttpServletRequest request, HttpServletResponse response) {

		List<Object> filteredList = null;
		try {
			for (Field field : fields) {
				if (columnName.equals(field.getName())) {
					Class fieldType = field.getType();
					if (Double.class.equals(fieldType)) {
						Double filterValue = Double.valueOf(value);
						filteredList = dataList.stream().filter(data -> {
							try {
								return ((Double) field.get(data)) >= filterValue;
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}
						}).collect(Collectors.toList());
						continue;
					} else if (Long.class.equals(fieldType)) {
						Long filterValue = Long.valueOf(value);
						filteredList = dataList.stream().filter(data -> {
							try {
								return ((Long) field.get(data)) >= filterValue;
							} catch (Exception e) {
								e.printStackTrace();
								return false;
							}
						}).collect(Collectors.toList());
						continue;
					} /*
						 * else if (Boolean.class.equals(fieldType)) { Boolean filterValue =
						 * "1".equals(value); filteredList = dataList.stream().filter(canalDataDto -> {
						 * try { return field.get(canalDataDto).equals(filterValue); } catch (Exception
						 * e) { e.printStackTrace(); return false; } }).collect(Collectors.toList());
						 * continue; } else if (Date.class.equals(fieldType)) { String[] date =
						 * value.split("-"); Date filterValue = new Date(Integer.valueOf(date[2]),
						 * Integer.valueOf(date[1]), Integer.valueOf(date[0])); filteredList =
						 * canalDataList.stream().filter(canalDataDto -> { try { return ((Date)
						 * field.get(canalDataDto)).after(filterValue); } catch (Exception e) {
						 * e.printStackTrace(); return false; } }).collect(Collectors.toList());
						 * continue; }
						 */

				}
			}

			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=\"filtered.xls\"");

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet
			int rownum = 0;
			try {
				Row row = sheet.createRow(rownum++);
				int cellnum = 0;
				for (Field field : fields) {
					Cell cell = row.createCell(cellnum++);
					Annotation annotation = field.getAnnotation(JsonProperty.class);
					if (annotation instanceof JsonProperty) {
						JsonProperty jsonProperty = (JsonProperty) annotation;
						cell.setCellValue(jsonProperty.value());
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			for (Object obj : filteredList) {
				Row row = sheet.createRow(rownum++);
				try {
					int cellnum = 0;
					for (Field field : fields) {
						Cell cell = row.createCell(cellnum++);
						cell.setCellValue(String.valueOf(field.get(obj)));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
