package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "tabel_dip")
public class TabelDip extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String daftarDip;
    private String namadokumen;
    private String pdfDokumen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDaftarDip() {
        return daftarDip;
    }

    public void setDaftarDip(String daftarDip) {
        this.daftarDip = daftarDip;
    }

    public String getNamadokumen() {
        return namadokumen;
    }

    public void setNamadokumen(String namadokumen) {
        this.namadokumen = namadokumen;
    }

    public String getPdfDokumen() {
        return pdfDokumen;
    }

    public void setPdfDokumen(String pdfDokumen) {
        this.pdfDokumen = pdfDokumen;
    }
}
