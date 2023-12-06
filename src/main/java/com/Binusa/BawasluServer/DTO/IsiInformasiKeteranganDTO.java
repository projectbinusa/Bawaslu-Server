package com.Binusa.BawasluServer.DTO;

public class IsiInformasiKeteranganDTO {

    private Long id;
    private String dokumen;
    private String pdfDocument;
    private Long jenisKeteranganId;

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

    public String getPdfDocument() {
        return pdfDocument;
    }

    public void setPdfDocument(String pdfDocument) {
        this.pdfDocument = pdfDocument;
    }

    public Long getJenisKeteranganId() {
        return jenisKeteranganId;
    }

    public void setJenisKeteranganId(Long jenisKeteranganId) {
        this.jenisKeteranganId = jenisKeteranganId;
    }
}
