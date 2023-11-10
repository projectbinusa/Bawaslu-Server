package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.repository.JenisInformasiRepository;
import com.Binusa.BawasluServer.repository.JenisKeteranganRepository;
import com.Binusa.BawasluServer.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JenisInformasiService {

    @Autowired
    private JenisInformasiRepository jenisInformasiRepository;

    @Autowired
    private JenisKeteranganRepository jenisKeteranganRepository;

    public List<JenisInformasiDTO> getAllJenisInformasi() {
        List<JenisInformasi> jenisInformasiList = jenisInformasiRepository.findAll();
        List<JenisInformasiDTO> jenisInformasiDTOList = new ArrayList<>();

        for (JenisInformasi jenisInformasi : jenisInformasiList) {
            JenisInformasiDTO jenisInformasiDTO = new JenisInformasiDTO();
            jenisInformasiDTO.setId(jenisInformasi.getId());
            jenisInformasiDTO.setNama_jenis(jenisInformasi.getNama_jenis());
            // Set other properties if needed
            jenisInformasiDTOList.add(jenisInformasiDTO);
        }

        return jenisInformasiDTOList;
    }

    public JenisInformasiDTO save(JenisInformasiDTO jenisInformasiDTO) {
        JenisInformasi jenisInformasi = new JenisInformasi();
        jenisInformasi.setNama_jenis(jenisInformasiDTO.getNama_jenis());

        JenisInformasi savedJenisInformasi = jenisInformasiRepository.save(jenisInformasi);
        JenisInformasiDTO savedDTO = new JenisInformasiDTO();
        savedDTO.setId(savedJenisInformasi.getId());
        savedDTO.setNama_jenis(savedJenisInformasi.getNama_jenis());

        return savedDTO;
    }

    public JenisInformasiDTO findById(Long id) {
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));
        JenisInformasiDTO jenisInformasiDTO = new JenisInformasiDTO();
        jenisInformasiDTO.setId(jenisInformasi.getId());
        jenisInformasiDTO.setNama_jenis(jenisInformasi.getNama_jenis());

        // Isi jenisKeteranganList dengan data yang sesuai
        List<JenisKeteranganDTO> jenisKeteranganDTOList = convertToDTO(jenisInformasi.getJenisKeteranganList());
        jenisInformasiDTO.setJenisKeteranganList(jenisKeteranganDTOList);

        return jenisInformasiDTO;
    }

    public List<JenisKeteranganDTO> convertToDTO(List<JenisKeterangan> jenisKeteranganList) {
        List<JenisKeteranganDTO> jenisKeteranganDTOList = new ArrayList<>();
        for (JenisKeterangan jenisKeterangan : jenisKeteranganList) {
            JenisKeteranganDTO jenisKeteranganDTO = new JenisKeteranganDTO();
            jenisKeteranganDTO.setId(jenisKeterangan.getId());
            jenisKeteranganDTO.setNama_keterangan(jenisKeterangan.getNama_keterangan());

            if (jenisKeterangan.getJenisInformasi() != null) {
                JenisInformasiDTO jenisInformasiDTO = new JenisInformasiDTO();
                jenisInformasiDTO.setId(jenisKeterangan.getJenisInformasi().getId());
                jenisInformasiDTO.setNama_jenis(jenisKeterangan.getJenisInformasi().getNama_jenis());

            }

            jenisKeteranganDTOList.add(jenisKeteranganDTO);
        }
        return jenisKeteranganDTOList;
    }


    public List<JenisKeteranganDTO> getJenisKeteranganByJenisInformasiId(Long jenisInformasiId) {
        List<JenisKeterangan> jenisKeteranganList = jenisKeteranganRepository.findByJenisInformasiId(jenisInformasiId);

        List<JenisKeteranganDTO> jenisKeteranganDTOList = convertToDTO(jenisKeteranganList);

        return jenisKeteranganDTOList;
    }

    public JenisInformasiDTO update(Long id, JenisInformasiDTO jenisInformasiDTO) {
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));

        if (jenisInformasiDTO.getNama_jenis() != null) {
            jenisInformasi.setNama_jenis(jenisInformasiDTO.getNama_jenis());
        }

        JenisInformasi updatedJenisInformasi = jenisInformasiRepository.save(jenisInformasi);

        JenisInformasiDTO updatedDTO = new JenisInformasiDTO();
        updatedDTO.setId(updatedJenisInformasi.getId());
        updatedDTO.setNama_jenis(updatedJenisInformasi.getNama_jenis());

        // Isi jenisKeteranganList dengan data yang sesuai
        List<JenisKeteranganDTO> jenisKeteranganDTOList = convertToDTO(updatedJenisInformasi.getJenisKeteranganList());
        updatedDTO.setJenisKeteranganList(jenisKeteranganDTOList);

        CommonResponse<JenisInformasiDTO> response = new CommonResponse<>();
        response.setStatus("success");
        response.setCode(HttpStatus.OK.value());
        response.setData(updatedDTO);
        response.setMessage("Jenis Informasi updated successfully.");

        return updatedDTO;
    }



    public void delete(Long id) {
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));
        jenisInformasiRepository.delete(jenisInformasi);
    }

}



