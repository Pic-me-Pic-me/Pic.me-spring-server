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
public class Sticker {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="sticker_location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Column(name="emoji")
    private int emoji;

    @Column(name="count")
    private int count;

    @Column(name="type")
    private int type;

    @Builder
    public Sticker(String location, int emoji, int type) {
        this.location = location;
        this.emoji = emoji;
        this.type = type;
        this.count = 0;
    }
}
