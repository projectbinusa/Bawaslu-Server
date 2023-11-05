package com.Binusa.BawasluServer.model;


import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "pengumuman")
public class Pengumuman extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String judul_pengumuman;
    private String author;
    private String tags;
    private String isi_pengumuman;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudul_pengumuman() {
        return judul_pengumuman;
    }

    public void setJudul_pengumuman(String judul_pengumuman) {
        this.judul_pengumuman = judul_pengumuman;
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

    public String getIsi_pengumuman() {
        return isi_pengumuman;
    }

    public void setIsi_pengumuman(String isi_pengumuman) {
        this.isi_pengumuman = isi_pengumuman;
    }
}
