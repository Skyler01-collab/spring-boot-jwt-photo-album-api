package com.example.springDemoWithRest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.springDemoWithRest.util.constants.Authority;
import com.example.springDemoWithRest.Model.Account;
import com.example.springDemoWithRest.Service.AccountService;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private AccountService accountService;
    
    @Override
    public void run(String... args) throws Exception {
        Account account01=new Account();
        account01.setEmail("skyler@gmail.com");
        account01.setPassword("password");
        account01.setAuthorities(Authority.USER.toString());
        accountService.save(account01);
                                        
         Account account02=new Account();
        account02.setEmail("julian@gmail.com");
        account02.setPassword("password");
        account02.setAuthorities(Authority.ADMIN.toString()+" "+Authority.USER.toString());
        accountService.save(account02);
        
        System.out.println("Seed data executed");
    }
    
}
