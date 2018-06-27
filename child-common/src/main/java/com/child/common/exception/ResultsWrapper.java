package com.child.common.exception;

import lombok.Data;

import java.util.List;

@Data
public class ResultsWrapper<T> {
	private Long total;
	private List<T> results;
	private int pages;

	public ResultsWrapper() {
	}

	public ResultsWrapper(Long total, int pages, List<T> results) {
		this.total = total;
		this.pages = pages;
		this.results = results;
	}


}
