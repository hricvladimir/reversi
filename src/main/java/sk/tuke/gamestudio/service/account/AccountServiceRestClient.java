package sk.tuke.gamestudio.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Account;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AccountServiceRestClient implements AccountService {
    private final String url = "http://localhost:8080/api/account";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addAccount(Account account) throws AccountException {
        restTemplate.postForEntity(url, account, Account.class);
    }

    @Override
    public List<Account> getMatchingAccounts(Account account) throws AccountException {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url, Account[].class).getBody()));
    }
}
