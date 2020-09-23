package data;

import objects.Account;

public class Data {
    public static Account standardUser() {
        Account account = new Account();
        account.setEmail("hien200296@gmail.com");
        account.setPassword("secret_pwd");
        return account;
    }
}
