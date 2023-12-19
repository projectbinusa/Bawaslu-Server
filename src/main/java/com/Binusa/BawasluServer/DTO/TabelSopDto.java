package com.Binusa.BawasluServer.DTO;

public class TabelSopDto {
    private String daftarSop;
    private String namaDokumen;
    private String pdfDokumen;

    public String getDaftarSop() {
        return daftarSop;
    }

    public void setDaftarSop(String daftarSop) {
        this.daftarSop = daftarSop;
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
