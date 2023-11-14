package com.Binusa.BawasluServer.DTO;

public class IsiInformasiKeteranganApiResponseDTO {
    private Long id;
    private String dokumen;
    private String pdfDokumen;
    private Long jenisKeterangan;

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

    public Long getJenisKeterangan() {
        return jenisKeterangan;
    }

    public void setJenisKeterangan(Long jenisKeterangan) {
        this.jenisKeterangan = jenisKeterangan;
    }
}
