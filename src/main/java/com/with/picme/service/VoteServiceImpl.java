package com.with.picme.service;

import com.with.picme.common.message.ErrorMessage;
import com.with.picme.entity.Vote;
import com.with.picme.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class VoteServiceImpl implements VoteService {
    private VoteRepository voteRepository;

    @Override
    public void closeVote(Long userId, Long voteId) {
        Vote vote = getVote(voteId);
        if (!validateUser(userId, vote))
            throw new IllegalArgumentException(ErrorMessage.NOT_ADMIN_VOTE.getMessage());
        vote.setStatus();
    }

    private Vote getVote(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.NOT_EXIST_VOTE.getMessage()));
    }

    private boolean validateUser(Long userId, Vote vote) {
        return vote.getUser().getId().equals(userId);
    }
}
