package sk.tuke.gamestudio.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> getAccountsByUsername(String username);
}
