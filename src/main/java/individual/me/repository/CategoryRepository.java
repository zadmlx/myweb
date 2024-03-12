package individual.me.repository;

import individual.me.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryRepository {
    void insertCategory(@Param("category")Category category);

    void deleteCategory(@Param("id") int id);

    void updateCategory(@Param("category") Category category);

    Category selectCategory(@Param("id") int id);

    List<Category> selectAllCategories();
}
