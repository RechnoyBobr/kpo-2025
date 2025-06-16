package hse.bank.storage;

import hse.bank.domains.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryStorage {
    @Getter
    @Setter
    private static List<Category> categories = new ArrayList<>();

    public static void addCategory(Category category) {
        categories.add(category);
    }

    public static Category getCategoryById(int id) {
        return categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public static void deleteCategoryById(int id) {
        categories.removeIf(c -> c.getId() == id);
    }
}
