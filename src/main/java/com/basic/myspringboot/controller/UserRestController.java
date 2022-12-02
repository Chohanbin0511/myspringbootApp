package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.entity.Users;
import com.basic.myspringboot.exception.ResourceNotFoundException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
//@RequiredArgsConstructor => 생성자를 안만들어도 final 로 선언한 변수를 생성자로 사용가능함
public class UserRestController {
    private final UserRepository userRepository;

    // Autowired 안쓰고 생성자 만들어서 사용 가능
    // constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
        return user;
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        // Not Found
        if(!optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(email + " User Not Found");
        }
        User user = optionalUser.get();
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PatchMapping("/{email}")
    public ResponseEntity<?> modifyUser(@PathVariable String email, @RequestBody User userDetail){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(email + " User Not Found");
        }
        User existUser = optionalUser.get();
        existUser.setName(userDetail.getName());
        User updatedUser = userRepository.save(existUser);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
//return ResponseEntity.ok(user);
        return ResponseEntity.ok().body(id + " 삭제처리 되었씁니다.");
    }

//    @GetMapping(value="/xml", produces = {"application/xml"})
//    public List<User> getUsersXml() {
//        return userRepository.findAll();
//    }
    @GetMapping(value = "/xml", produces = {"application/xml"})
    public Users getUsersXml() {
        Users users = new Users();
        users.setUsers(userRepository.findAll());
        return users;
    }
}
