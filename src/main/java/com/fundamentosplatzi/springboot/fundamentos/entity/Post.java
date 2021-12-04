package com.fundamentosplatzi.springboot.fundamentos.entity;


import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post",nullable = false,unique = true)
    private Long id;
    @Column(name = "descripcion",length = 255)
    private int descripcion;

    @ManyToOne
    private User user;

    public Post() {
    }

    public Post(int descripcion, User user) {
        this.descripcion = descripcion;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(int descripcion) {
        this.descripcion = descripcion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", descripcion=" + descripcion +
                ", user=" + user +
                '}';
    }
}
