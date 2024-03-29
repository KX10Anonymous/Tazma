package com.janonimo.tazma.core.reporting;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posts")
@Data
public class Post {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long Id;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("url")
    private String url;

    @JoinColumn(name="stylist_id")
    @ManyToOne(fetch=FetchType.EAGER)
    @JsonManagedReference
    private User stylist;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Reaction> reactions;
}
