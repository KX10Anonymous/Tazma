package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.reporting.Post;
import com.janonimo.tazma.core.reporting.React;
import com.janonimo.tazma.core.reporting.Reaction;
import com.janonimo.tazma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;

    public Reaction saveReaction(User user, Post post, React react) {
        Reaction reaction = Reaction.builder()
                .created(LocalDateTime.now())
                .user(user)
                .post(post)
                .react(react)
                .build();
        return reactionRepository.save(reaction);
    }

    public Optional<Reaction> getReactionById(Long id) {
        return reactionRepository.findById(id);
    }

    public List<Reaction> findByPost(Long id){
        return reactionRepository.findByPost(id);
    }


    public void deleteReaction(Reaction reaction) {
        reactionRepository.delete(reaction);
    }

}