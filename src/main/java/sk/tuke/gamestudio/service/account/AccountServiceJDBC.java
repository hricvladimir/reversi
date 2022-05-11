package sk.tuke.gamestudio.service.account;

import sk.tuke.gamestudio.entity.Account;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.comment.CommentException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceJDBC implements AccountService {
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "global";
    public static final String SELECT_STATEMENT = "SELECT username, hashedPassword FROM account WHERE username = ?";
    public static final String INSERT_STATEMENT = "INSERT INTO account (username, hashedPassword) VALUES (?, ?)";

    @Override
    public void addAccount(Account account) throws AccountException {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(INSERT_STATEMENT)
        )
        {
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getHashedPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Could not add account!", e);
        }
    }

    @Override
    public List<Account> getMatchingAccounts(Account account) throws AccountException {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(SELECT_STATEMENT)
        )
        {
            statement.setString(1, account.getUsername());
            try(var rs = statement.executeQuery()) {
                var accounts = new ArrayList<Account>();
                while (rs.next()) {
                    accounts.add(new Account(rs.getString(1), rs.getString(2)));
                }
                return accounts;
            }
        } catch (SQLException e) {
            throw new CommentException("Could not get accounts!", e);
        }
    }
}
