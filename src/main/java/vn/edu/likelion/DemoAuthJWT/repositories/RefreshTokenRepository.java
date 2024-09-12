package vn.edu.likelion.DemoAuthJWT.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.DemoAuthJWT.models.RefreshToken;
import vn.edu.likelion.DemoAuthJWT.models.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * RefreshTokenRepository -
 *
 * @param
 * @return
 * @throws
 */
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.expiryDate < :currentTime")
    List<RefreshToken> findAllExpiredTokens(@Param("currentTime") Instant currentTime);
}
