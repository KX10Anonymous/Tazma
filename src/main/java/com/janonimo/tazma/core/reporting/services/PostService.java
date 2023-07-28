package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.reporting.Post;
import com.janonimo.tazma.core.reporting.Reaction;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.RolePriority;
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
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if (token != null) {
            User user = token.user;
            if(user.getRoles().size() > 1){
                if (user.getRoles().get(0).getRoleName() == RoleName.STYLIST && user.getRoles().get(0).getPriority() == RolePriority.MAIN) {
                    var folderName = user.getEmail().toLowerCase().replace("@", "at").replace(".", "-");
                    String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/tazma-web/tazma/public/src/posts/";
                    File personalDir = new File(PATH + folderName);
                    boolean dirExist = false;
                    if (!personalDir.exists()) {
                        dirExist = personalDir.mkdir();
                    } else if (personalDir.exists()) {
                        if (personalDir.isDirectory()) {
                            dirExist = true;
                        }
                    }
                    if (dirExist) {
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
                } else if (user.getRoles().get(1).getRoleName() == RoleName.STYLIST && user.getRoles().get(1).getPriority() == RolePriority.MAIN) {
                    String folderName = user.getEmail().toLowerCase().replace("@", "at").replace(".", "-");
                    String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/tazma-web/tazma/public/src/posts/";
                    File personalDir = new File(PATH + folderName);
                    boolean dirExist = false;
                    if (!personalDir.exists()) {
                        dirExist = personalDir.mkdir();
                    } else if (personalDir.exists()) {
                        if (personalDir.isDirectory()) {
                            dirExist = true;
                        }
                    }
                    if (dirExist) {
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
            }


        }
        return null;
    }

    public Post read(Long id) {
        return Objects.requireNonNull(postRepository.findById(id)).orElse(null);
    }

    public void delete(String jwt, Post post) {
        post = Objects.requireNonNull(postRepository.findById(post.getId())).orElse(null);
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if(token != null && post != null){
            User user = token.getUser();
            if(user.getRoles().size() > 1 && post.getStylist() != null){
                if (user.getRoles().get(0).getRoleName() == RoleName.STYLIST) {
                    if (user.getRoles().get(0).getPriority() == RolePriority.MAIN) {
                        if (Objects.equals(user.getId(), post.getStylist().getId())) {
                            File postFile = new File(post.getUrl());

                            boolean isDeleted = postFile.delete();
                            if (isDeleted) {
                                List<Reaction> reactions = reactionService.findByPost(post.getId());
                                for (Reaction reaction : reactions) {
                                    reactionService.deleteReaction(reaction);
                                }
                                postRepository.delete(post);
                            }
                        }
                    } else if (user.getRoles().get(1).getRoleName() == RoleName.STYLIST && user.getRoles().get(1).getPriority() == RolePriority.MAIN) {
                        if (Objects.equals(user.getId(), post.getStylist().getId())) {
                            File postFile = new File(post.getUrl());

                            boolean isDeleted = postFile.delete();
                            if (isDeleted) {
                                List<Reaction> reactions = reactionService.findByPost(post.getId());
                                for (Reaction reaction : reactions) {
                                    reactionService.deleteReaction(reaction);
                                }
                                postRepository.delete(post);
                            }
                        }
                    }
                } else if (user.getRoles().get(1).getRoleName() == RoleName.STYLIST && user.getRoles().get(1).getPriority() == RolePriority.MAIN) {
                    if (Objects.equals(user.getId(), post.getStylist().getId())) {
                        File postFile = new File(post.getUrl());

                        boolean isDeleted = postFile.delete();
                        if (isDeleted) {
                            List<Reaction> reactions = reactionService.findByPost(post.getId());
                            for (Reaction reaction : reactions) {
                                reactionService.deleteReaction(reaction);
                            }
                            postRepository.delete(post);
                        }
                    }
                }
            } else if (user.getRoles().get(0).getRoleName() == RoleName.ADMIN && user.getRoles().get(0).getPriority() == RolePriority.MAIN) {
                File postFile = new File(post.getUrl());
                List<Reaction> reactions = reactionService.findByPost(post.getId());
                boolean isDeleted = postFile.delete();
                if(isDeleted){
                    for (Reaction reaction : reactions) {
                        reactionService.deleteReaction(reaction);
                    }
                    postRepository.delete(post);
                }
            }
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
