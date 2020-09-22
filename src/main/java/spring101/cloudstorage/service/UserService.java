package spring101.cloudstorage.service;

import org.springframework.stereotype.Service;
import spring101.cloudstorage.mapper.UserMapper;
import spring101.cloudstorage.model.User;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private HashService hashService;
    private UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public Integer createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
    }

    public boolean isUserExist(String username) {
        if (userMapper.selectByName(username) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Integer getCurUserId(String username) {
        Integer userId = userMapper.selectByName(username).getUserid();
        if (userId >= 0) {
            return userId;
        } else {
            return -1;
        }
    }
}
