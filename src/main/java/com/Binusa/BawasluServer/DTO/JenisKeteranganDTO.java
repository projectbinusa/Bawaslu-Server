package com.Binusa.BawasluServer.DTO;

public class JenisKeteranganDTO {
    private Long id;
    private String keterangan;
    private Long jenisInformasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Long getJenisInformasi() {
        return jenisInformasi;
    }

    public void setJenisInformasi(Long jenisInformasi) {
        this.jenisInformasi = jenisInformasi;
    }
}
