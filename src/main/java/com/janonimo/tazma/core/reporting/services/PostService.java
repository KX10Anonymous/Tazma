package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.reporting.Post;
import com.janonimo.tazma.core.reporting.Reaction;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TokenRepository tokenRepository;
    private final ReactionService reactionService;

    public Post save(MultipartFile file, String jwt) {
        if (tokenRepository.findByToken(jwt).isPresent()) {
            User user = tokenRepository.findByToken(jwt).get().user;
            if (user.getRole() == Role.STYLIST) {
                String folderName = user.getEmail().toLowerCase().replace("@", "_");
                String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/tazma-web/tazma/public/src/posts/";
                File personalDir = new File(PATH + folderName);
                if (!personalDir.exists()) {
                    personalDir.mkdir();
                }
                String resourcePath = PATH.concat(folderName + "/" + file.getOriginalFilename());
                Post post = new Post();
                post.setUrl("/src/posts/" + folderName + "/" + file.getOriginalFilename());
                post.setStylist(user);
                post.setCreated(LocalDateTime.now());
                post = postRepository.save(post);
                assert post != null;
                if (post.getId() != null) {
                    try {
                        file.transferTo(new File(resourcePath));
                    } catch (IOException ex) {
                        return null;
                    }
                }
                return post;
            }

        }
        return null;
    }

    public Post read(Long id) {
        return Objects.requireNonNull(postRepository.findById(id)).get();
    }

    public void delete(String jwt, Post post) {
        post = Objects.requireNonNull(postRepository.findById(post.getId())).get();
        User user = tokenRepository.findByToken(jwt).get().user;
        if (user.getRole() == Role.STYLIST) {
            if (Objects.equals(user.getId(), post.getStylist().getId())) {
                postRepository.delete(post);
                File postFile = new File(post.getUrl());
                postFile.delete();
                List<Reaction> reactions = reactionService.findByPost(post.getId());
                for (Reaction reaction : reactions) {
                    reactionService.deleteReaction(reaction);
                }
            }
        } else if (user.getRole() == Role.ADMIN) {
            postRepository.delete(post);
            File postFile = new File(post.getUrl());
            List<Reaction> reactions = reactionService.findByPost(post.getId());
            for (Reaction reaction : reactions) {
                reactionService.deleteReaction(reaction);
            }
            postFile.delete();
        }
    }

    public List<Post> posts(Long id) {
        return postRepository.findAllByStylist(id);
    }

    public List<Post> recent() {
        return postRepository.findLatest();
    }

    public List<Post> recentMoreReactions() {
        return postRepository.findByReactionsCount();
    }

}
