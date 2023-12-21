package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "carousel")
public class Carousel extends DateConfig {
    @Id
    @GeneratedValue
    private long id;
    private String namaCarousel;
    private String foto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamaCarousel() {
        return namaCarousel;
    }

    public void setNamaCarousel(String namaCarousel) {
        this.namaCarousel = namaCarousel;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
