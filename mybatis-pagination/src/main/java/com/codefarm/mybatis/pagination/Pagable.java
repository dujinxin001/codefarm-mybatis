package com.codefarm.mybatis.pagination;

public abstract class Pagable implements IPagable
{
    private int showCount = 10; // 每页显示记录数
    
    private int totalPage; // 总页数
    
    private int totalResult; // 总记录数
    
    private int currentPage = 1; // 当前页
    
    private boolean pagable = false;
    
    // private int currentResult; // 当前记录起始索引
    
    @Override
    public int getShowCount()
    {
        return showCount;
    }
    
    @Override
    public void setShowCount(int showCount)
    {
        this.showCount = showCount;
    }
    
    @Override
    public int getTotalPage()
    {
        return totalPage;
    }
    
    @Override
    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }
    
    @Override
    public int getTotalResult()
    {
        return totalResult;
    }
    
    @Override
    public void setTotalResult(int totalResult)
    {
        this.totalResult = totalResult;
    }
    
    @Override
    public int getCurrentPage()
    {
        return currentPage;
    }
    
    @Override
    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }
    
    @Override
    public boolean isPagable()
    {
        return pagable;
    }
    
    @Override
    public void setPagable(boolean pagable)
    {
        this.pagable = pagable;
    }
    
    public void setPage(Pagable pagable)
    {
        this.currentPage = pagable.getCurrentPage();
        this.pagable = pagable.isPagable();
        this.showCount = pagable.getShowCount();
        this.totalPage = pagable.getTotalPage();
        this.totalResult = pagable.getTotalResult();
        
    }
    
}
