package com.Binusa.BawasluServer.DTO;


import java.util.List;

public class JenisInformasiDTO {
    private Long id;
    private String nama_jenis;
    private List<JenisKeteranganDTO> jenisKeteranganList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_jenis() {
        return nama_jenis;
    }

    public void setNama_jenis(String nama_jenis) {
        this.nama_jenis = nama_jenis;
    }

    public List<JenisKeteranganDTO> getJenisKeteranganList() {
        return jenisKeteranganList;
    }

    public void setJenisKeteranganList(List<JenisKeteranganDTO> jenisKeteranganList) {
        this.jenisKeteranganList = jenisKeteranganList;
    }
}
