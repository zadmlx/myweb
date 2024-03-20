package individual.me.service;

import individual.me.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
    void insertCategory(Category category);

    void deleteCategory(int id);

    void updateCategory(Category category);

    Category selectCategory(int id);

    List<Category> selectAllCategories();
}
