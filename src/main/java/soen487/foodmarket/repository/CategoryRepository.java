package soen487.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soen487.foodmarket.dataobject.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
