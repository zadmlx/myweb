package individual.me.controller;

import individual.me.pojo.Article;
import individual.me.pojo.Result;
import individual.me.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class BookController {

    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public Result insertArticle(@RequestBody Article article){
        try {
            articleService.insertArticle(article);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
        return Result.ok("添加成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable("id") int id){
        try {
            articleService.deleteArticle(id);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
        return Result.ok("删除成功");
    }

    @GetMapping("/{id}")
    public Result getArticle(@PathVariable("id") int id){
        try {
            Article article = articleService.getArticleById(id);
            return Result.ok(article);
        }
        catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }
}
