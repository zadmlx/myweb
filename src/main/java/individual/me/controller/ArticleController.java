package individual.me.controller;

import individual.me.pojo.Article;
import individual.me.pojo.Result;
import individual.me.pojo.user.AuthUser;
import individual.me.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping()
    public Result insertArticle(@RequestBody Article article){
        log.info("article：{}",article);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser userDetails = (AuthUser) userDetailsService.loadUserByUsername(user.getUsername());
        article.setAuthor(userDetails.getUser().getId());
        log.info("article：{}",article);
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

    @PutMapping()
    public Result updateArticle(@RequestBody Article article){
        try {
            articleService.updateArticle(article);
            return Result.ok("更新成功");
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }
}
