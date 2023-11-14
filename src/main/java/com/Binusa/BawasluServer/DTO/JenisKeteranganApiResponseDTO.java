package com.Binusa.BawasluServer.DTO;

import java.util.List;

public class JenisKeteranganApiResponseDTO {
    private Long id;
    private String keterangan;
    private List<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public List<IsiInformasiKeteranganApiResponseDTO> getIsiInformasiKeteranganList() {
        return isiInformasiKeteranganList;
    }

    public void setIsiInformasiKeteranganList(List<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganList) {
        this.isiInformasiKeteranganList = isiInformasiKeteranganList;
    }
}
