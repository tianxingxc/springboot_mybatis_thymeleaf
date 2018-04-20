package ${packageName}.common;

public class OffsetBean {
    private int offset = 0;
    private int limit = 10;

    public OffsetBean() {
    }

    public OffsetBean(int offset) {
        if(offset <= 0)
            throw new RuntimeException("OffsetBean offset 必须大于1");

        this.offset = offset;
    }

    public OffsetBean(int offset, int limit) {

        if(offset <= 0)
            throw new RuntimeException("OffsetBean offset 必须大于1");

        this.offset = (offset - 1) * limit;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        if(offset <= 0)
            throw new RuntimeException("OffsetBean offset 必须大于1");
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
