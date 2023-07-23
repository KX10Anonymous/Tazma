package com.janonimo.tazma.core.reporting;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.janonimo.tazma.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="reactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Reaction {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime created;
    private React react;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    @JsonManagedReference
    private Post post;

}
