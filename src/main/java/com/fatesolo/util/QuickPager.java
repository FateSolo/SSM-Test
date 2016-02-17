package com.fatesolo.util;

import java.util.List;

//分页工具类
public class QuickPager<T> {

    private int currPage = 1;

    private int pageSize = 10;

    private int totalRows = 0;

    private int totalPages = 0;

    private List<T> list = null;

    public QuickPager(int currPage, int pageSize) {
        this.currPage = currPage;
        this.pageSize = pageSize;
    }

    public QuickPager(String currPage, String pageSize) {
        if (currPage != null && !currPage.equals("")) {
            this.currPage = Integer.parseInt(currPage);
        }

        if (pageSize != null && !pageSize.equals("")) {
            this.pageSize = Integer.parseInt(pageSize);
        }
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        this.setTotalPages(this.totalRows == 0 ? 0 : (this.totalRows % this.pageSize != 0 ? this.totalRows / this.pageSize + 1 : this.totalRows / this.pageSize));
    }

    public int getTotalPages() {
        return totalPages;
    }

    private void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
