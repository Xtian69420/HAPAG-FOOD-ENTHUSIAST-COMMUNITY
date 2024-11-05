package com.example.hapag;

public class AccountManager {
    private static AccountManager instance;
    private AccountHandle accountHandle;

    private AccountManager() {}

    public static synchronized AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public void setAccountHandle(AccountHandle accountHandle) {
        this.accountHandle = accountHandle;
    }

    public AccountHandle getAccountHandle() {
        return accountHandle;
    }
}
