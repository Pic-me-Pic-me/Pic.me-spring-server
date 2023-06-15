package com.with.picme.dto.user;

import com.with.picme.entity.User;
import lombok.Builder;

@Builder
public record UserInfoGetResponseDto (
        String userName,
        String email
){
    public static UserInfoGetResponseDto of(User user){
        return UserInfoGetResponseDto
                .builder()
                .userName(user.getName())
                .email(user.getEmail())
                .build();

    }

}
