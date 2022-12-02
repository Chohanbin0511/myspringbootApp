package com.basic.myspringboot.service;

import com.basic.myspringboot.entity.Account;
import com.basic.myspringboot.property.MyBootProperties;
import com.basic.myspringboot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
//    @Autowired
    private final AccountRepository accountRepository;
    private final MyBootProperties myBootProperties;
    private final PasswordEncoder passwordEncoder;
    //Account 레코드 추가
    public Account createAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountRepository.save(account);
    }

    @PostConstruct
    public void init() {
        Optional<Account> byUsername = accountRepository.findByUsername(myBootProperties.getUsername());
        if(!byUsername.isPresent()) {
            Account newAccount = this.createAccount(myBootProperties.getUsername(), myBootProperties.getPassword());
            System.out.println(newAccount);
        }
    }

    //login 할때 사용자가 입력한 정보가 유효한지를 체크한다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        Account account = byUsername.orElseThrow(() -> new UsernameNotFoundException(username + " User Not Found"));
        return new User(account.getUsername(), account.getPassword(), authorities());


    }
    //User 객체의 세번째 인자 USER라는 ROLE을 가진 사용자이다 라고 설정하는 부분
    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
