package spring101.cloudstorage.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import spring101.cloudstorage.mapper.UserMapper;
import spring101.cloudstorage.model.User;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private HashService hashService;
    private UserMapper userMapper;

    public AuthenticationService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //plain input string
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User result = userMapper.selectByName(username);

        if(result!=null){
            String encodedSalt = result.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if(result.getPassword().equals(hashedPassword)){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
