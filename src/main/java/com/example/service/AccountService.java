package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;

import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account findAccount(Integer posted_by){
        return accountRepository.findById(posted_by).orElse(null);
    }

    public Account findAccountByUsernameAndPassword(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    public Account register(Account newAccount){
        if(!newAccount.getUsername().equals("") && newAccount.getPassword().length() >= 4){
            return accountRepository.save(newAccount);
        }
        else{
            return null;
        }
    }

    public Account login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password).orElse(null);
    }


}
