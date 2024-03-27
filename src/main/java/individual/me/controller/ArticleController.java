package individual.me.controller;

import individual.me.config.security.jwt.JwtUtil;
import individual.me.log.Log;
import individual.me.pojo.Article;
import individual.me.pojo.Result;
import individual.me.pojo.user.AuthUser;
import individual.me.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import individual.me.config.security.aspect.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @Autowired
    UserDetailsService userDetailsService;


    @Log
    @PostMapping()
    @PreAuthorize("@ac.check('admin')")
    public Result insertArticle(@RequestBody Article article){
        log.info("article：{}",article);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser userDetails = (AuthUser) userDetailsService.loadUserByUsername(user.getUsername());
        article.setAuthor(userDetails.getUser().getId());

        articleService.insertArticle(article);

        return Result.ok("添加成功");
    }


    @Log
    @PreAuthorize("@ac.check('admin')")
    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable("id") int id){
        try {
            articleService.deleteArticle(id);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
        return Result.ok("删除成功");
    }

    @Any
    @Log
    @GetMapping("/{id}")
    public Result getArticle(@PathVariable("id") int id){
        return Result.ok(articleService.getArticleById(id));
    }

    @Log
    @PreAuthorize("@ac.check('admin')")
    @PutMapping()
    public Result updateArticle(@RequestBody Article article){
        articleService.updateArticle(article);
        return Result.ok("更新成功");

    }

    @Any
    @GetMapping()
    public Result getAllArticleVo(HttpServletRequest request){
        String token = JwtUtil.getToken(request);
        Integer id = JwtUtil.getId(token);
        List<Article> articleVo = articleService.getAllArticleVo(id);
        return Result.ok(articleVo);
    }


}
