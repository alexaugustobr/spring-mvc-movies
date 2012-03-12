/*
Copyright [2012] [Carlo P. Micieli]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.github.carlomicieli.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents a result set paginated.
 * 
 * @author Carlo P. Micieli
 *
 */
public class PaginatedResult<T> {
	static int	DEFAULT_MAX_LINKED_PAGES = 10;
	static int	DEFAULT_PAGE_SIZE = 25;
	
	public PaginatedResult(List<T> data, long nrOfElements, int page) {
		this(data, nrOfElements, page, DEFAULT_PAGE_SIZE);
	}
	
	public PaginatedResult(List<T> data, long nrOfElements, int page, int pageSize) {
		if (pageSize<=0)
			throw new IllegalArgumentException();
		
		this.data = data;
		this.page = page;
		this.pageSize = pageSize;
		this.nrOfElements = nrOfElements;
		
		this.pageCount = calculateNumberOfPages(nrOfElements, pageSize);
		this.links = initLinks();
	}
	
	private List<Integer> links;
	private List<T> data;
	private int page;
	private int pageSize;
	private int pageCount;
	private long nrOfElements;

	private int calculateNumberOfPages(long nrOfElements, int pageSize) {
		double d = Math.ceil((double)nrOfElements / pageSize);
		return (int)d;
	}
	
	private List<Integer> initLinks() {
		List<Integer> l = new ArrayList<Integer>();
		
		for (int i=1; i<=getPageCount(); i++)
			l.add(i);
		
		return l;
	}
	
	public List<T> getData() {
		return data;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getNrOfElements() {
		return nrOfElements;
	}

	public boolean isLastPage() {
		return getPage()==getPageCount();
	}
	
	public boolean isFirstPage() {
		return getPage()==1;
	}
	
	public int getFirstPageLink() {		
		return Math.max(1, getPage() - (DEFAULT_MAX_LINKED_PAGES / 2) - 1);
	}
	
	public int getLastPageLink() {
		return Math.min(getFirstPageLink() + DEFAULT_MAX_LINKED_PAGES - 1, getPageCount());
	}
	
	public List<Integer> getLinkedPage() {
		return links.subList(getFirstPageLink() - 1, getLastPageLink());
	}
}
