package com.Binusa.BawasluServer.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tags;

    @ManyToOne
    private Berita berita;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Berita getBerita() {
        return berita;
    }

    public void setBerita(Berita berita) {
        this.berita = berita;
    }
}
