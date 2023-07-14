package com.janonimo.tazma.core.reporting;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

}
