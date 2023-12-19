package com.Binusa.BawasluServer.DTO;

public class TabelRegulasiDTO {
    private String daftarRegulasi;
    private String namaDokumen;
    private String pdfDokumen;

    public String getDaftarRegulasi() {
        return daftarRegulasi;
    }

    public void setDaftarRegulasi(String daftarRegulasi) {
        this.daftarRegulasi = daftarRegulasi;
    }

    public String getNamaDokumen() {
        return namaDokumen;
    }

    public void setNamaDokumen(String namaDokumen) {
        this.namaDokumen = namaDokumen;
    }

    public String getPdfDokumen() {
        return pdfDokumen;
    }

    public void setPdfDokumen(String pdfDokumen) {
        this.pdfDokumen = pdfDokumen;
    }
}
