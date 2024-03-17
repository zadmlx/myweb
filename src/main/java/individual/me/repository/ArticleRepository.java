package individual.me.repository;

import individual.me.pojo.Article;
import individual.me.pojo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleRepository {

    void insertArticle(@Param("article")Article article);

    void deleteArticle(@Param("id")int id);

    void updateArticle(@Param("article")Article article);

    Article getArticleById(@Param("id") int id);

    List<ArticleVo> getAllArticleVo(@Param("id")int id);
}
