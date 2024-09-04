package vn.edu.likelion.DemoAuthJWT.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.DemoAuthJWT.models.User;

import java.util.Optional;

/**
 * UserRepository -
 *
 * @param
 * @return
 * @throws
 */
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
