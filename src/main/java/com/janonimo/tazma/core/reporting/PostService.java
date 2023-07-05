package com.janonimo.tazma.core.reporting;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;
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

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TokenRepository tokenRepository;
    private final String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/src/main/resources/posts/";
    public Post save(MultipartFile file, String jwt) {
        User user = tokenRepository.findByToken(jwt).get().user;
        if(user.getRole() == Role.STYLIST){
            String path = PATH.concat(file.getName());
            Post post = new Post();
            post.setUrl(path);
            post.setStylist(user);
            post.setCreated(LocalDateTime.now());
            post = postRepository.save(post);
            if (post.getId() != null) {
                try {
                    file.transferTo(new File(path));
                } catch (IOException ex) {
                    return null;
                }
            }
            return post;
        }
       return null;
    }

    public void delete(String jwt, Post post){
        post = postRepository.findById(post.getId()).get();
        User user = tokenRepository.findByToken(jwt).get().user;
        if(user.getRole() == Role.STYLIST){
            if(user.getId() == post.getStylist().getId()){
                postRepository.delete(post);
            }
        }else if(user.getRole() == Role.ADMIN){
            postRepository.delete(post);
        }
    }
    public List<Post> posts(Long id){
        return postRepository.findAllByStylist(id);
    }


}
