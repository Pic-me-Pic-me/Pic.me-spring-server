package com.with.picme.config;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SaltEncrypt{
    private String createSalt(){
      return BCrypt.gensalt(10);
    }

    public String createPasswordWithSalt(String password){
        return BCrypt.hashpw(password, createSalt());
    }

    public boolean isMatch(String password, String hashed){
        return BCrypt.checkpw(password, hashed);
    }
}
