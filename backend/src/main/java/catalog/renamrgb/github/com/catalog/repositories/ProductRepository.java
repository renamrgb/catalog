package catalog.renamrgb.github.com.catalog.repositories;

import catalog.renamrgb.github.com.catalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
