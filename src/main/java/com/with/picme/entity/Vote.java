package com.with.picme.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static javax.persistence.GenerationType.IDENTITY;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="status")
    private boolean status;

    @Column(name="title")
    private String title;

    @Column(name="count")
    private int count;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @Column(name="date")
    private int date;

    @Column(name="type")
    private int type;

    @Builder
    public Vote(boolean status, String title, int date, int type) {
        this.status = status;
        this.title = title;
        this.count = 0;
        this.date = date;
        this.type = type;
    }
}
