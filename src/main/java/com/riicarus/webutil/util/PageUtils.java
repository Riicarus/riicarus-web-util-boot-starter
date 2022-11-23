package com.riicarus.webutil.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * [FEATURE INFO]<br/>
 * MybatisPlus 分页工具
 *
 * @author Riicarus
 * @create 2022-10-28 16:59
 * @since 1.0.0
 */
public class PageUtils<T, E> {

    /**
     * 根据 sourcePage 的参数, 将 records 集合转换为分页
     *
     * @param sourcePage 理想的分页
     * @param records 需要转换为分页的集合
     * @return 转换后的分页
     */
    public IPage<E> castPage(IPage<T> sourcePage, List<E> records) {
        if (sourcePage == null) {
            return new Page<>();
        }

        IPage<E> iPage = new Page<>();
        iPage.setTotal(sourcePage.getTotal());
        iPage.setPages(sourcePage.getPages());
        iPage.setSize(sourcePage.getSize());
        iPage.setCurrent(sourcePage.getCurrent());
        iPage.setRecords(records);

        return iPage;
    }

    /**
     * 将 records 集合转换为分页
     *
     * @param pageNum 页数
     * @param pageSize 每页元素数量
     * @param records 需要转换为分页的集合
     * @return 转换后的分页
     */
    public IPage<E> castList2Page(Long pageNum, Long pageSize, List<E> records) {

        List<E> pageList = new ArrayList<>();

        long index = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (long i = 0; i < pageSize && (index + i) < records.size(); i++) {
            pageList.add(records.get((int)(index + i)));
        }

        IPage<E> iPage = new Page<>();
        iPage.setRecords(pageList);
        iPage.setTotal(records.size());
        iPage.setCurrent(pageNum);
        iPage.setSize(pageSize);
        long pages = records.size() / pageSize + (records.size() % pageSize == 0 ? 0 : 1);
        iPage.setPages(pages);

        return iPage;
    }

    /**
     * 将 records 集合转换为分页
     *
     * @param pageParam 理想的分页参数
     * @param records 需要转换为分页的集合
     * @return 转换后的分页
     */
    public IPage<E> castList2Page(IPage<T> pageParam, List<E> records) {
        long pageNum = pageParam.getCurrent();
        long pageSize = pageParam.getSize();

        return castList2Page(pageNum, pageSize, records);
    }

}
