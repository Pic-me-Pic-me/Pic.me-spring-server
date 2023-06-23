package com.with.picme.service;

import com.with.picme.common.message.ErrorMessage;
import com.with.picme.dto.auth.AuthSignUpResponseDto;
import com.with.picme.dto.user.UserInfoGetResponseDto;
import com.with.picme.entity.User;
import com.with.picme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public UserInfoGetResponseDto getUserInfo(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CANT_GET_USERINFO.getMessage()));
        return UserInfoGetResponseDto.of(user);
    };

}
