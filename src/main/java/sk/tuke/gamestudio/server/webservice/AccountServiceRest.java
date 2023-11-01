package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Account;
import sk.tuke.gamestudio.service.account.AccountService;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountServiceRest {
    @Autowired
    private AccountService accountService;

    @PutMapping
    public List<Account> getMatchingAccounts(@RequestBody Account account) { return accountService.getMatchingAccounts(account); }

    @PostMapping
    public void addAccount(@RequestBody Account account) { accountService.addAccount(account); }
}
