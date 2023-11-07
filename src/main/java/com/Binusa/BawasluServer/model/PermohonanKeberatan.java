package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "permohonan_keberatan")
public class PermohonanKeberatan extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nama;
    private String alamat;
    private String noTelp;
    private String nomorIndentitas;
    private String jenisIdentitas;
    private String kasusPosisi;
    private String tujuanPenggunaanInformasi;
    private String alasan;
    @Lob
    private String tandaPengenal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNomorIndentitas() {
        return nomorIndentitas;
    }

    public void setNomorIndentitas(String nomorIndentitas) {
        this.nomorIndentitas = nomorIndentitas;
    }

    public String getJenisIdentitas() {
        return jenisIdentitas;
    }

    public void setJenisIdentitas(String jenisIdentitas) {
        this.jenisIdentitas = jenisIdentitas;
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

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getTandaPengenal() {
        return tandaPengenal;
    }

    public void setTandaPengenal(String tandaPengenal) {
        this.tandaPengenal = tandaPengenal;
    }
}
