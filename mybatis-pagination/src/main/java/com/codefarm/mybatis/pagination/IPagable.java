package com.codefarm.mybatis.pagination;

public interface IPagable
{
    
    /**
     * 每页显示条数
     * @return
     */
    public abstract int getShowCount();
    
    public abstract void setShowCount(int showCount);
    
    /**
     * 分页总数
     * @return
     */
    public abstract int getTotalPage();
    
    public abstract void setTotalPage(int totalPage);
    
    /**
     * 总条数
     * @return
     */
    public abstract int getTotalResult();
    
    public abstract void setTotalResult(int totalResult);
    
    /**
     * 当前页码
     * @return
     */
    public abstract int getCurrentPage();
    
    public abstract void setCurrentPage(int currentPage);
    
    public abstract boolean isPagable();
    
    public abstract void setPagable(boolean pagable);
    
}