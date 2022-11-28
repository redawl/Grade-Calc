package com.github.redawl.GradeCalc.User;

import com.github.redawl.GradeCalc.exceptions.InvalidUsernameException;
import com.github.redawl.GradeCalc.exceptions.PasswordsDoNotMatchException;
import com.github.redawl.GradeCalc.exceptions.UsernameAlreadyTakenException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserDto userDto) throws UsernameAlreadyTakenException, PasswordsDoNotMatchException, InvalidUsernameException {
        // Check if username is null
        // TODO: Verify username is not "    " and is alphanumeric
        if(userDto.getUsername() == null || "".equals(userDto.getUsername())){
            throw new InvalidUsernameException();
        }

        // Check if user exists
        if(userRepository.findById(userDto.getUsername()).isPresent()){
            throw new UsernameAlreadyTakenException(userDto.getUsername());
        }

        // Check that password check matches
        if(userDto.getPassword() != null && checkPassword(userDto.getPassword(), userDto.getMatchingPassword())) {
            User user = new User();
            user.setUsername(userDto.getUsername());

            user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));
            user.setEnabled(true);

            userRepository.save(user);
        } else {
            throw new PasswordsDoNotMatchException();
        }
    }

    /**
     * Confirm that password is valid
     * @param password Password to check
     * @param confirm Same password as above (Hopefully)
     * @return True if a match, false if not a match
     */
    private boolean checkPassword(String password, String confirm){
        String checkSalt = BCrypt.gensalt();

        String passwordHash = BCrypt.hashpw(password, checkSalt);
        String confirmHash = BCrypt.hashpw(confirm, checkSalt);

        return passwordHash.equals(confirmHash);
    }
}
