package sk.tuke.gamestudio.service.account;

import sk.tuke.gamestudio.entity.Account;

import java.util.List;

public interface AccountService {
    void addAccount(Account account) throws AccountException;
    List<Account> getMatchingAccounts(Account account) throws AccountException;
}
