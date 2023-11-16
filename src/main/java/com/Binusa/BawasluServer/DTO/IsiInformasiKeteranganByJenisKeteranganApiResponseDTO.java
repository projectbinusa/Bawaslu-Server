package com.Binusa.BawasluServer.DTO;

import java.util.List;

public class IsiInformasiKeteranganByJenisKeteranganApiResponseDTO {
    private Long id;
    private String keterangan;
    private List<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganDTOList;

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

    public List<IsiInformasiKeteranganApiResponseDTO> getIsiInformasiKeteranganDTOList() {
        return isiInformasiKeteranganDTOList;
    }

    public void setIsiInformasiKeteranganDTOList(List<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganDTOList) {
        this.isiInformasiKeteranganDTOList = isiInformasiKeteranganDTOList;
    }
}
