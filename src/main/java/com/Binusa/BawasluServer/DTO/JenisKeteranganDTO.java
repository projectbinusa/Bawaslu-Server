package com.Binusa.BawasluServer.DTO;

import java.util.List;

public class JenisKeteranganDTO {
    private Long id;
    private String nama_keterangan;
    private JenisInformasiDTO jenisInformasi;
    private List<IsiInformasiKeteranganDTO> isiInformasiKeteranganList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_keterangan() {
        return nama_keterangan;
    }

    public void setNama_keterangan(String nama_keterangan) {
        this.nama_keterangan = nama_keterangan;
    }

    public JenisInformasiDTO getJenisInformasi() {
        return jenisInformasi;
    }

    public void setJenisInformasi(JenisInformasiDTO jenisInformasi) {
        this.jenisInformasi = jenisInformasi;
    }

    public List<IsiInformasiKeteranganDTO> getIsiInformasiKeteranganList() {
        return isiInformasiKeteranganList;
    }

    public void setIsiInformasiKeteranganList(List<IsiInformasiKeteranganDTO> isiInformasiKeteranganList) {
        this.isiInformasiKeteranganList = isiInformasiKeteranganList;
    }
}
