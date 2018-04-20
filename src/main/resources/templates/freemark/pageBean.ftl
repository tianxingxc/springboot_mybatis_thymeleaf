package ${packageName}.common;

public class PageBean {

    /** 分页开始 */
    private int start;

    /** 分页截止 */
    private int end;

    /** 当前页，从0开始 */
    private int current;

    /** 每页数量 */
    private int pageSize = 20;

    /** 分页数 */
    private int pageCount;

    /** 分页条目数 */
    private int tabCount = 10;

    /** 总条数 */
    private int total;

    public PageBean(int current, int total) {
        this.current = current;
        this.total = total;
        this.pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        this.total = total;
        this.start = (current - current % tabCount) == 0 ? 1 : current - current % tabCount;
        int endPage = current + (tabCount - current % tabCount  - 1);
        if(endPage > pageCount)
            endPage = pageCount;
        this.end = endPage;
    }

    public PageBean(int current, int tabCount, int pageSize, int total) {
        this.current = current;
        this.pageSize = pageSize;
        this.pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        this.tabCount = tabCount;
        this.total = total;

        this.start = (current - current % tabCount) == 0 ? 1 : current - current % tabCount;
        int endPage = current + (tabCount - current % tabCount  - 1);
        if(endPage > pageCount)
            endPage = pageCount;
        this.end = endPage;

    }

    public PageBean() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTabCount() {
        return tabCount;
    }

    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }
}
