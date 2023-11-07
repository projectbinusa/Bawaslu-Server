package com.Binusa.BawasluServer.model;


import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "berita")
public class Berita extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String judulBerita;
    private String author;
    private String tags;
    private String isiBerita;
    @Lob
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudulBerita() {
        return judulBerita;
    }

    public void setJudulBerita(String judulBerita) {
        this.judulBerita = judulBerita;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
