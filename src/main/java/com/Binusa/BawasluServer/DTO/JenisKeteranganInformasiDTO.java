package com.Binusa.BawasluServer.DTO;

public class JenisKeteranganInformasiDTO {
    private Long id;
    private String nama_keterangan;
    private JenisInformasiDTO jenisInformasi;

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
}
