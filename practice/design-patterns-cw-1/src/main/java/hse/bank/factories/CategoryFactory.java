package hse.bank.factories;

import hse.bank.domains.Category;
import hse.bank.records.CategoryData;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {
    private static int globalId = -1;

    public static Category createCategory(CategoryData categoryData) {
        globalId++;
        return new Category(globalId, categoryData.isPositive(), categoryData.name());
    }
    public static Category createCategoryWithId(CategoryData categoryData, int id) {
        if (id > globalId) {
            globalId = id;
        }
        return new Category(id, categoryData.isPositive(), categoryData.name());
    }
    public static void flush() {
        globalId = -1;
    }
}
