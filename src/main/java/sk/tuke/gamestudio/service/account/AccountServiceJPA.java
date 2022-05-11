package sk.tuke.gamestudio.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Account;
import sk.tuke.gamestudio.service.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class AccountServiceJPA implements AccountService {

    @Autowired
    AccountRepository repo;

    @Override
    public void addAccount(Account account) throws AccountException {
        repo.save(account);
    }

    @Override
    public List<Account> getMatchingAccounts(Account account) throws AccountException {
        return repo.getAccountsByUsername(account.getUsername());
    }
}
