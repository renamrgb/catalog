package catalog.renamrgb.github.com.catalog.repositories;

import catalog.renamrgb.github.com.catalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
