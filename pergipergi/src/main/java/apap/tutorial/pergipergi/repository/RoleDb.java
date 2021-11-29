package apap.tutorial.pergipergi.repository;

import apap.tutorial.pergipergi.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findById(Long id);
}
