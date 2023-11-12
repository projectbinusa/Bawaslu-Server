package com.Binusa.BawasluServer.DTO;

public class JenisKeteranganInformasiDTO {
    private String nama_keterangan;
    private JenisInformasiDTO jenisInformasi;


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
