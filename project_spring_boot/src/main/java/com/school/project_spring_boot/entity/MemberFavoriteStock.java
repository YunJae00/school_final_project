package com.school.project_spring_boot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class MemberFavoriteStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @JsonManagedReference
    private Stock stock;

    public MemberFavoriteStock() {
    }

    public MemberFavoriteStock(Member member, Stock stock) {
        this.member = member;
        this.stock = stock;
    }

    public MemberFavoriteStock(Long id, Member member, Stock stock) {
        this.id = id;
        this.member = member;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
