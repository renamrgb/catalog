package catalog.renamrgb.github.com.catalog.repositories;

import catalog.renamrgb.github.com.catalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
