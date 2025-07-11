package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if (optionalAccount.isPresent()) {
            return null;
        }
        else {
            return accountRepository.save(account);
        }
    }

    public Account login(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if (!optionalAccount.isPresent()) {
            System.out.println("username not found");
            return null;
        }
        System.out.println("optional account password: "+optionalAccount.get().getPassword());
        System.out.println("account password: "+account.getPassword());
        if (optionalAccount.get().getPassword().equals(account.getPassword())) {
            System.out.println("both username and password match");
            return optionalAccount.get();
        }
        System.out.println("password incorrect");
        return null;
    }
}
