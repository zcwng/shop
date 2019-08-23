package com.zcwng.shop.common;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Paging<T> implements Serializable {

	private int prev;		//上一页
	private int curr;		//当前页
	private int next;		//下一页
	private int pageSize;	//每页记录数
	private int totalPage;	//总页数
	private long totalCount;	//总记录数
	private List<T> list;		//数据集合
	private boolean isFirst;	//是否第一页
	private boolean isLast;		//是否最后页

	public Paging(List<T> list,int nowPage,long count,int pageSize) {
		this.list = list;
		this.curr = nowPage;
		this.totalCount = count;
		this.pageSize = pageSize;
		this.totalPage = (int) ((count + pageSize - 1) / pageSize);
		this.prev = nowPage-1>1?nowPage-1:1;
		this.next = nowPage >=totalPage ? totalPage:nowPage + 1;
		this.isFirst = nowPage == 1;
		this.isLast = nowPage == totalPage;
	}

	public int getPrev() {
		return prev;
	}

	public int getCurr() {
		return curr;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}

	public int getNext() {
		return next;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}


	public long getTotalCount() {
		return totalCount;
	}

	public List<T> getList() {
		return list;
	}

	
	
	public boolean isFirst() {
		return isFirst;
	}

	public boolean isLast() {
		return isLast;
	}
	
	
}