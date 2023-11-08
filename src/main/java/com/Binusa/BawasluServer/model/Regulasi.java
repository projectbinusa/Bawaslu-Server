package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "regulasi")
public class Regulasi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dokumen;
    @Lob
    private String pdfDokumen;
    @ManyToOne
    @JoinColumn(name = "menu_regulasi")
    private MenuRegulasi menuRegulasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumen() {
        return dokumen;
    }

    public void setDokumen(String dokumen) {
        this.dokumen = dokumen;
    }

    public String getPdfDokumen() {
        return pdfDokumen;
    }

    public void setPdfDokumen(String pdfDokumen) {
        this.pdfDokumen = pdfDokumen;
    }

    public MenuRegulasi getMenuRegulasi() {
        return menuRegulasi;
    }

    public void setMenuRegulasi(MenuRegulasi menuRegulasi) {
        this.menuRegulasi = menuRegulasi;
    }
}
