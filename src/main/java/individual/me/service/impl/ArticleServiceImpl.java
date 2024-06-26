package individual.me.service.impl;

import individual.me.pojo.Article;
import individual.me.repository.ArticleRepository;
import individual.me.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {


    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void insertArticle(Article article) {
        articleRepository.insertArticle(article);
    }

    @Override
    public void deleteArticle(int id) {
        articleRepository.deleteArticle(id);
    }

    @Override
    public void updateArticle(Article article) {
        articleRepository.updateArticle(article);
    }

    @Override
    public Article getArticleById(int id) {
        return articleRepository.getArticleById(id);
    }

    @Override
    public List<Article> getAllArticleVo(int id) {
        return articleRepository.getAllArticle(id);
    }
}
