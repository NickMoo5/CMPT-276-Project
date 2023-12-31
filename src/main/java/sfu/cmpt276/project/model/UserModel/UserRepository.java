package sfu.cmpt276.project.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findByUsername(String username);
    List<User> findByEmail(String email);
    List<User> findByUid(int uid);
}
