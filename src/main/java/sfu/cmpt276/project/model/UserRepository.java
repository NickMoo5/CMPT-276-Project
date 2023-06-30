package sfu.cmpt276.project.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserAndPass(String username, String password);
    List<User> findByUser(String username);
}
