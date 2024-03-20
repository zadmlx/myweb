package individual.me.service.impl;

import individual.me.pojo.Category;
import individual.me.repository.CategoryRepository;
import individual.me.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void insertCategory(Category category) {
        categoryRepository.insertCategory(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteCategory(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    @Override
    public Category selectCategory(int id) {
        return categoryRepository.selectCategory(id);
    }

    @Override
    public List<Category> selectAllCategories() {
        return categoryRepository.selectAllCategories();
    }
}
