package com.Binusa.BawasluServer.DTO;

public class IsiInformasiKeteranganDTO {

    private Long id;
    private String dokumen;
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


    public Long getJenisKeteranganId() {
        return jenisKeteranganId;
    }

    public void setJenisKeteranganId(Long jenisKeteranganId) {
        this.jenisKeteranganId = jenisKeteranganId;
    }
}
