  package edu.wtbu.pojo;

  public class Page {
    int total;
    int startPage;
    int pageSize;

    public Page() {}

    public Page(int total, int startPage, int pageSize) {
      this.total = total;
      this.startPage = startPage;
      this.pageSize = pageSize;
    }

    public int getTotal() {
      return this.total;
    }

    public void setTotal(int total) {
      this.total = total;
    }

    public int getStartPage() {
      return this.startPage;
    }

    public void setStartPage(int startPage) {
      this.startPage = startPage;
    }

    public int getPageSize() {
      return this.pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }
  }


