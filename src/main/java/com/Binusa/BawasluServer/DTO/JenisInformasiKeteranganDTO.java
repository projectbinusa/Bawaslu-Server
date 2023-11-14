package com.Binusa.BawasluServer.DTO;

import java.util.List;

public class JenisInformasiKeteranganDTO {
    private  Long id;
    private String namaInformasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<JenisKeteranganInformasiDTO> jenisKeteranganInformasiDTOList;

    public String getNamaInformasi() {
        return namaInformasi;
    }

    public void setNamaInformasi(String namaInformasi) {
        this.namaInformasi = namaInformasi;
    }

    public List<JenisKeteranganInformasiDTO> getJenisKeteranganInformasiDTOList() {
        return jenisKeteranganInformasiDTOList;
    }

    public void setJenisKeteranganInformasiDTOList(List<JenisKeteranganInformasiDTO> jenisKeteranganInformasiDTOList) {
        this.jenisKeteranganInformasiDTOList = jenisKeteranganInformasiDTOList;
    }
}
