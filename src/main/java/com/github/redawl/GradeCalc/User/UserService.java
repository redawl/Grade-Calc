package com.github.redawl.GradeCalc.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(UserDto userDto){
        // Check if user exists
        if(userRepository.findById(userDto.getUsername()).isPresent()){
            return false;
        }

        if(userDto.getPassword() != null && userDto.getPassword().equals(userDto.getMatchingPassword())) {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));
            user.setEnabled(true);

            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
