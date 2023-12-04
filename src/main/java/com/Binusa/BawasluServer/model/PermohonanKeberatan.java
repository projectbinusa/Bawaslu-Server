package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "permohonan_keberatan")
public class PermohonanKeberatan extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaPemohon;
    private String alamatPemohon;
    private String nomorIdentitasPemohon;
    private String jenisIdentitas;
    private String noTlp;
    private String email;
    private String kasusPosisi;
    private String tujuanPenggunaanInformasi;
    private String alasanPengajuanKeberatan;
    @Lob
    private String fotoIdentitas;

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

    public String getKasusPosisi() {
        return kasusPosisi;
    }

    public void setKasusPosisi(String kasusPosisi) {
        this.kasusPosisi = kasusPosisi;
    }

    public String getTujuanPenggunaanInformasi() {
        return tujuanPenggunaanInformasi;
    }

    public void setTujuanPenggunaanInformasi(String tujuanPenggunaanInformasi) {
        this.tujuanPenggunaanInformasi = tujuanPenggunaanInformasi;
    }

    public String getAlasanPengajuanKeberatan() {
        return alasanPengajuanKeberatan;
    }

    public void setAlasanPengajuanKeberatan(String alasanPengajuanKeberatan) {
        this.alasanPengajuanKeberatan = alasanPengajuanKeberatan;
    }

    public String getFotoIdentitas() {
        return fotoIdentitas;
    }

    public void setFotoIdentitas(String fotoIdentitas) {
        this.fotoIdentitas = fotoIdentitas;
    }
}
