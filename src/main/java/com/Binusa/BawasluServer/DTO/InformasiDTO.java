package com.Binusa.BawasluServer.DTO;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;

import java.util.List;

public class InformasiDTO {
    private List<JenisInformasiDTO> jenisInformasiList;
    private List<JenisKeteranganDTO> jenisKeteranganList;
    private List<IsiInformasiKeteranganDTO> isiInformasiKeteranganList;

    public List<JenisInformasiDTO> getJenisInformasiList() {
        return jenisInformasiList;
    }

    public void setJenisInformasiList(List<JenisInformasiDTO> jenisInformasiList) {
        this.jenisInformasiList = jenisInformasiList;
    }

    public List<JenisKeteranganDTO> getJenisKeteranganList() {
        return jenisKeteranganList;
    }

    public void setJenisKeteranganList(List<JenisKeteranganDTO> jenisKeteranganList) {
        this.jenisKeteranganList = jenisKeteranganList;
    }

    public List<IsiInformasiKeteranganDTO> getIsiInformasiKeteranganList() {
        return isiInformasiKeteranganList;
    }

    public void setIsiInformasiKeteranganList(List<IsiInformasiKeteranganDTO> isiInformasiKeteranganList) {
        this.isiInformasiKeteranganList = isiInformasiKeteranganList;
    }
}
