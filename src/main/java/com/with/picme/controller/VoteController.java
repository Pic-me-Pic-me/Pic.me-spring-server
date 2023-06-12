package com.with.picme.controller;

import com.with.picme.common.ApiResponse;
import com.with.picme.common.message.ErrorMessage;
import com.with.picme.common.message.ResponseMessage;
import com.with.picme.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PatchMapping("/{voteId}")
    public ResponseEntity<ApiResponse> closeVote(@Valid @PathVariable Long voteId, Principal principal) {
        Long userId = getUser(principal);
        voteService.closeVote(userId, voteId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CLOSE_VOTE.getMessage()));
    }

    private Long getUser(Principal principal) {
        if (isNull(principal.getName()))
            throw new IllegalArgumentException(ErrorMessage.EMPTY_TOKEN.getMessage());
        return Long.valueOf(principal.getName());
    }
}
