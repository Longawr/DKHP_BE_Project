package api.DAO;

import java.util.List;

import api.POJO.Account;

public interface AccountDAO {

    List<Account> getAccountList();

    Account getAccountByID(String accountID);

    boolean removeAccountByID(String accountID);

    boolean addAccount(Account acc);

    boolean updateAccount(Account acc);


}
