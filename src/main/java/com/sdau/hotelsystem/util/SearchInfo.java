package com.sdau.hotelsystem.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInfo {

	private boolean canpage=true;

	private int page;
	private int limit;
	private String where;
	private String search;

	public SearchInfo(boolean canpage) {
		super();
		this.canpage = canpage;
	}
}
