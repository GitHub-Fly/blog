package com.scs.web.blog.dao;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;

import java.sql.SQLException;
import java.util.List;

/**
 * @author xunmi
 * @ClassName ArticleDao
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public interface ArticleDao {

    /**
     * 批量插入文章信息
     * @param articleList
     * @return
     * @throws SQLException
     */
    int[] batchInsert(List<Article> articleList) throws SQLException;

    /**
     * 初始化文章信息
     * @return
     * @throws SQLException
     */
    List<Article> selectAll() throws SQLException;

    /**
     * 获取热门文章
     * @return
     * @throws SQLException
     */
    List<ArticleVo> getHotArticle() throws SQLException;

    /**
     * 通过指定id查找文章详细信息
     * @param id 指定文章id
     * @return
     * @throws SQLException
     */
    Article getArticleById(Long id) throws SQLException;

    /**
     * 通过指定博主id查找自己表发的文章篇数（用于在t_user表中计算articles的值
     * @param id
     * @return
     * @throws SQLException
     */
    int getCountByUserId(Long id) throws SQLException;
}
