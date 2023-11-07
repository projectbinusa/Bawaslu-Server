package com.Binusa.BawasluServer.DTO;

import javax.persistence.Lob;

public class PermohonanInformasiDTO {
    private long id;
    private String email;
    private String nama;
    private String pendidikan;
    private String pekerjaan;
    private String noHp;
    @Lob
    private String alamat;
    private String ditujukanKepada;
    private String rincianInformasi;
    private String tujuanInformasi;
    private String caraMemperolehInformasi;
    private String caraMendapatInformasi;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDitujukanKepada() {
        return ditujukanKepada;
    }

    public void setDitujukanKepada(String ditujukanKepada) {
        this.ditujukanKepada = ditujukanKepada;
    }

    public String getRincianInformasi() {
        return rincianInformasi;
    }

    public void setRincianInformasi(String rincianInformasi) {
        this.rincianInformasi = rincianInformasi;
    }

    public String getTujuanInformasi() {
        return tujuanInformasi;
    }

    public void setTujuanInformasi(String tujuanInformasi) {
        this.tujuanInformasi = tujuanInformasi;
    }

    public String getCaraMemperolehInformasi() {
        return caraMemperolehInformasi;
    }

    public void setCaraMemperolehInformasi(String caraMemperolehInformasi) {
        this.caraMemperolehInformasi = caraMemperolehInformasi;
    }

    public String getCaraMendapatInformasi() {
        return caraMendapatInformasi;
    }

    public void setCaraMendapatInformasi(String caraMendapatInformasi) {
        this.caraMendapatInformasi = caraMendapatInformasi;
    }
}
