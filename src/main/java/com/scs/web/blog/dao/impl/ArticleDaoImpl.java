package com.scs.web.blog.dao.impl;

import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.service.impl.ArticleServiceImpl;
import com.scs.web.blog.util.DataUtil;
import com.scs.web.blog.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xunmi
 * @ClassName ArticleDaoImpl
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public class ArticleDaoImpl implements ArticleDao {
    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Override
    public int[] batchInsert(List<Article> articleList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_article (user_id, title,content,cover,diamond,comments,likes,publish_time) VALUES(?,?,?,?,?,?,?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        articleList.forEach(article -> {
            try {
                pstmt.setInt(1, DataUtil.getUserId());
                pstmt.setString(2, article.getTitle());
                pstmt.setString(3, article.getContent());
                pstmt.setString(4, article.getCover());
                pstmt.setInt(5, article.getDiamond());
                pstmt.setInt(6, article.getComments());
                pstmt.setInt(7, article.getLikes());
                pstmt.setObject(8, article.getPublishTime());
                pstmt.addBatch();
            } catch (SQLException e) {
                logger.error("批量导入文章出错");
            }
        });
        int[] result = pstmt.executeBatch();
        connection.commit();
//        DbUtil.close(null, pstmt, connection);
        return result;
    }

    @Override
    public List<Article> selectAll() throws SQLException {
        List<Article> articleList = new ArrayList<>();
        Connection connection = DbUtil.getConnection();
        String sql = "SELECT * FROM t_article ORDER BY id DESC ";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setUserId(rs.getLong("user_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCover(rs.getString("cover"));
            article.setDiamond(rs.getInt("diamond"));
            article.setComments(rs.getInt("comments"));
            article.setLikes(rs.getInt("likes"));
            article.setPublishTime(rs.getTimestamp("publish_time").toLocalDateTime());
            articleList.add(article);
        }
//        DbUtil.close(rs, stmt, connection);
        return articleList;
    }
}