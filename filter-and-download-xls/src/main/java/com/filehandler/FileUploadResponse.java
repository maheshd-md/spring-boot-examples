package com.filehandler;

import java.util.List;

public class FileUploadResponse {

	private long rows;
	private String filename;
	private List<String> columns;
	
	public FileUploadResponse(long rows, String filename, List<String> columns) {
		super();
		this.rows = rows;
		this.filename = filename;
		this.columns = columns;
	}
	public long getRows() {
		return rows;
	}
	public void setRows(long rows) {
		this.rows = rows;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List getColumns() {
		return columns;
	}
	public void setColumns(List columns) {
		this.columns = columns;
	}
}
