package com.microservices.service;

import com.microservices.config.JwtProvider;
import com.microservices.exception.UserException;
import com.microservices.modal.User;
import com.microservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email= JwtProvider.getEmailFromJwtToken(jwt);


        User user = userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user not exist with email "+email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String username) throws UserException {

        User user=userRepository.findByEmail(username);

        if(user!=null) {

            return user;
        }

        throw new UserException("user not exist with username "+username);
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);

        if(opt.isEmpty()) {
            throw new UserException("user not found with id "+userId);
        }
        return opt.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
