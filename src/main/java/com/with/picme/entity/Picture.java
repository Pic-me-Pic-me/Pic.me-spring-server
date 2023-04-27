package com.with.picme.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Picture {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @Column(name="url")
    private String url;

    @Column(name="count")
    private int count;

    @Builder
    public Picture(String url) {
        this.url = url;
        this.count = 0;
    }
}
