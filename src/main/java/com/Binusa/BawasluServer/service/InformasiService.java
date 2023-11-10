package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.InformasiDTO;
import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.service.IsiInformasiKeteranganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformasiService {
    @Autowired
    private JenisInformasiService jenisInformasiService;

    @Autowired
    private JenisKeteranganService jenisKeteranganService;

    @Autowired
    private IsiInformasiKeteranganService isiInformasiKeteranganService;

    public InformasiDTO getInformasi() {
        InformasiDTO informasiDTO = new InformasiDTO();

        // Ambil data dari JenisInformasiService
        List<JenisInformasiDTO> jenisInformasiList = jenisInformasiService.getAllJenisInformasi();
        informasiDTO.setJenisInformasiList(jenisInformasiList);

        // Ambil data dari JenisKeteranganService
        List<JenisKeteranganDTO> jenisKeteranganList = jenisKeteranganService.getAllJenisKeterangan();
        informasiDTO.setJenisKeteranganList(jenisKeteranganList);

        // Ambil data dari IsiInformasiKeteranganService
        List<IsiInformasiKeteranganDTO> isiInformasiKeteranganList = isiInformasiKeteranganService.getAllIsiInformasiKeterangan();
        informasiDTO.setIsiInformasiKeteranganList(isiInformasiKeteranganList);

        return informasiDTO;
    }
}
