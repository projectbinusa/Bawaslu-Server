package com.Binusa.BawasluServer.model;


import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "berita")
public class Berita extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String judulBerita;
    private String author;
    @Lob
    private String isiBerita;
    @Lob
    private String image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, targetEntity = Tags.class)
    @JoinTable(name = "tags_berita",
            joinColumns = @JoinColumn(name = "tags_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "berita_id", nullable = false, updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Set<Tags> tagsBerita = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private CategoryBerita categoryBerita;

    public Berita() {
    }

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

    public Set<Tags> getTagsBerita() {
        return tagsBerita;
    }

    public void setTagsBerita(Set<Tags> tagsBerita) {
        this.tagsBerita = tagsBerita;
    }

    public CategoryBerita getCategoryBerita() {
        return categoryBerita;
    }

    public void setCategoryBerita(CategoryBerita categoryBerita) {
        this.categoryBerita = categoryBerita;
    }
}
