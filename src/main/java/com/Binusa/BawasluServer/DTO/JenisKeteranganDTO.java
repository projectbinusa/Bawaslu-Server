package com.Binusa.BawasluServer.DTO;

import java.util.List;

public class JenisKeteranganDTO {
    private Long id;
    private String nama_keterangan;
    private List<IsiInformasiKeteranganDTO> isiInformasiKeteranganDTOList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_keterangan() {
        return nama_keterangan;
    }

    public void setNama_keterangan(String nama_keterangan) {
        this.nama_keterangan = nama_keterangan;
    }

    public List<IsiInformasiKeteranganDTO> getIsiInformasiKeteranganDTOList() {
        return isiInformasiKeteranganDTOList;
    }

    public void setIsiInformasiKeteranganDTOList(List<IsiInformasiKeteranganDTO> isiInformasiKeteranganDTOList) {
        this.isiInformasiKeteranganDTOList = isiInformasiKeteranganDTOList;
    }
}
