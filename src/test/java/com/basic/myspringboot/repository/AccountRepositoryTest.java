package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Account;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    @Rollback(value = false)
    public void account_update() throws Exception {
        Optional<Account> optional = accountRepository.findById(1L);
        if(optional.isPresent()) {
            Account account = optional.get();
            account.setPassword("boot1234a");
        }
        //public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X extends Throwable
        //Supplier의 추상 메서드 T get()
        Account existAccount = accountRepository.findByUsername("boot2")
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
        System.out.println(existAccount.getUsername());

    }

    @Test
    @Rollback(value = false)
    @Disabled
    public void account() throws Exception {
        Account account = new Account();
        account.setUsername("boot");
        account.setPassword("1234");

        Account save = accountRepository.save(account);
        System.out.println("save.getId() = " + save.getId());
        System.out.println("save.getUsername() = " + save.getUsername());
    }
}