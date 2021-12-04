package com.fundamentosplatzi.springboot.fundamentos.service;

import com.fundamentosplatzi.springboot.fundamentos.FundamentosApplication;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final Log LOGGER = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTransaccional(List<User> users){
        /*users.stream()
                .peek(user-> LOGGER.info("Usuario insert --> " + user))
                .forEach( user-> userRepository.save(user));*/

        users.stream()
                .peek(user-> LOGGER.info("Usuario insert --> " + user))
                .forEach(userRepository::save);

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }




    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        userRepository.delete(new User(id));
    }

    public User update(User newUser, long id) {
        return userRepository.findById(id)
                .map(
                        user->{
                            user.setEmail(newUser.getEmail());
                            user.setBirthDate(newUser.getBirthDate());
                            user.setName(newUser.getName());
                            return userRepository.save(user);
                        }
                ).get();
    }
}
