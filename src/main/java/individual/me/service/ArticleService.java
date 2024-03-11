package individual.me.service;

import individual.me.pojo.Article;

public interface ArticleService {

    void insertArticle(Article article);

    void deleteArticle(int id);

    void updateArticle(Article article);

    Article getArticleById(int id);
}
