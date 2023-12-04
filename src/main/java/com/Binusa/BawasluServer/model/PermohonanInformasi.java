package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table (name = "permohonan_informasi")
public class PermohonanInformasi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaPemohon;
    @Lob
    private String alamatPemohon;
    private String nomorIdentitasPemohon;
    private String jenisIdentitas;
    private String noTlp;
    private String email;
    @Lob
    private String rincianYangDibutuhkan;
    @Lob
    private String tujuanPenggunaanInformasi;
    private String caraMemperolehInformasi;
    @Lob
    private String fotoIdentitas;
    private String caraMendapatSalinanInformasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaPemohon() {
        return namaPemohon;
    }

    public void setNamaPemohon(String namaPemohon) {
        this.namaPemohon = namaPemohon;
    }

    public String getAlamatPemohon() {
        return alamatPemohon;
    }

    public void setAlamatPemohon(String alamatPemohon) {
        this.alamatPemohon = alamatPemohon;
    }

    public String getNomorIdentitasPemohon() {
        return nomorIdentitasPemohon;
    }

    public void setNomorIdentitasPemohon(String nomorIdentitasPemohon) {
        this.nomorIdentitasPemohon = nomorIdentitasPemohon;
    }

    public String getJenisIdentitas() {
        return jenisIdentitas;
    }

    public void setJenisIdentitas(String jenisIdentitas) {
        this.jenisIdentitas = jenisIdentitas;
    }

    public String getNoTlp() {
        return noTlp;
    }

    public void setNoTlp(String noTlp) {
        this.noTlp = noTlp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRincianYangDibutuhkan() {
        return rincianYangDibutuhkan;
    }

    public void setRincianYangDibutuhkan(String rincianYangDibutuhkan) {
        this.rincianYangDibutuhkan = rincianYangDibutuhkan;
    }

    public String getTujuanPenggunaanInformasi() {
        return tujuanPenggunaanInformasi;
    }

    public void setTujuanPenggunaanInformasi(String tujuanPenggunaanInformasi) {
        this.tujuanPenggunaanInformasi = tujuanPenggunaanInformasi;
    }

    public String getCaraMemperolehInformasi() {
        return caraMemperolehInformasi;
    }

    public void setCaraMemperolehInformasi(String caraMemperolehInformasi) {
        this.caraMemperolehInformasi = caraMemperolehInformasi;
    }

    public String getFotoIdentitas() {
        return fotoIdentitas;
    }

    public void setFotoIdentitas(String fotoIdentitas) {
        this.fotoIdentitas = fotoIdentitas;
    }

    public String getCaraMendapatSalinanInformasi() {
        return caraMendapatSalinanInformasi;
    }

    public void setCaraMendapatSalinanInformasi(String caraMendapatSalinanInformasi) {
        this.caraMendapatSalinanInformasi = caraMendapatSalinanInformasi;
    }
}
