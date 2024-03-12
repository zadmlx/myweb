package individual.me.controller;

import individual.me.pojo.Category;
import individual.me.pojo.Result;
import individual.me.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public Result insertCategory(@RequestBody Category category){
        categoryService.insertCategory(category);
        return Result.ok("添加成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
        return Result.ok("删除成功");
    }

    @GetMapping("/{id}")
    public Result selectCategory(@PathVariable("id") int id){
        log.info("请求进入");
        Category category = categoryService.selectCategory(id);
        return Result.ok(category);
    }

    @GetMapping()
    public Result selectAllCategory(){
        List<Category> categories = categoryService.selectAllCategories();
        return Result.ok(categories);
    }
}
