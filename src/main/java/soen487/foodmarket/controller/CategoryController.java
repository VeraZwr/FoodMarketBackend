package soen487.foodmarket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soen487.foodmarket.dataobject.Category;
import soen487.foodmarket.models.CommonReturnType;
import soen487.foodmarket.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public CommonReturnType findAll() {
        List<Category> categoryList = categoryService.findAll();
        return CommonReturnType.create(categoryList);
    }
}
