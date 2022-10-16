package catalog.renamrgb.github.com.catalog.repositories;

import catalog.renamrgb.github.com.catalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
