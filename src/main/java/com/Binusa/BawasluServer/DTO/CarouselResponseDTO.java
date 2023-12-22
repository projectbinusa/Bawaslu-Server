package com.Binusa.BawasluServer.DTO;

public class CarouselResponseDTO {

    private Long id;

    private String namaCarousel;

    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

