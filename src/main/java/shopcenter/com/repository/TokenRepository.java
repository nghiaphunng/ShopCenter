package shopcenter.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{
	Optional<Token> findByUsername(String username);
}
