package com.baofeng.utils;

import java.util.List;

/**
 * 分页数据对象
 * 
 * @author RENLIANGRONG
 * 
 * @param <T>
 */
public class PageResult<T> {
	private int currentPage;
	private int totalPages;
	private int total;
	private List<T> rows;

	public PageResult(List<T> inRows, int inCurrentPage, int inTotalPages, int inTotalRecords) {
		this.setRows(inRows);
		this.setCurrentPage(inCurrentPage);
		this.setTotalPages(inTotalPages);
		this.setTotal(inTotalRecords);
	}

	public PageResult() {
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
