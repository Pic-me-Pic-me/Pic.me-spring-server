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
public class Keyword {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticker_id")
    private Sticker sticker;

    @Column(name="keyword")
    private int keyword;

    @Column(name="count")
    private int count;

    @Column(name="type")
    private int type;

    @Builder
    public Keyword(int keyword, int type) {
        this.keyword = keyword;
        this.type = type;
        this.count = 0;
    }
}
