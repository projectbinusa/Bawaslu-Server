package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.repository.JenisInformasiRepository;
import com.Binusa.BawasluServer.repository.JenisKeteranganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class JenisKeteranganService {

    @Autowired
    private JenisKeteranganRepository jenisKeteranganRepository;

    @Autowired
    private JenisInformasiRepository jenisInformasiRepository;


    public JenisKeteranganDTO save(JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan jenisKeterangan = new JenisKeterangan();
        jenisKeterangan.setNama_keterangan(jenisKeteranganDTO.getNama_keterangan());

        if (jenisKeteranganDTO.getJenisInformasi() != null) {
            Long jenisInformasiId = jenisKeteranganDTO.getJenisInformasi().getId();
            JenisInformasi jenisInformasi = jenisInformasiRepository.findById(jenisInformasiId)
                    .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + jenisInformasiId));
            jenisKeterangan.setJenisInformasi(jenisInformasi);

            jenisKeteranganDTO.getJenisInformasi().setNama_jenis(jenisInformasi.getNama_jenis());
        }

        JenisKeterangan savedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);
        JenisKeteranganDTO savedDTO = new JenisKeteranganDTO();
        savedDTO.setId(savedJenisKeterangan.getId());
        savedDTO.setNama_keterangan(savedJenisKeterangan.getNama_keterangan());
        savedDTO.setJenisInformasi(jenisKeteranganDTO.getJenisInformasi()); // Tetapkan jenis informasi dari DTO

        return savedDTO;
    }

    public JenisKeteranganDTO findById(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        JenisKeteranganDTO jenisKeteranganDTO = new JenisKeteranganDTO();
        jenisKeteranganDTO.setId(jenisKeterangan.getId());
        jenisKeteranganDTO.setNama_keterangan(jenisKeterangan.getNama_keterangan());

        if (jenisKeterangan.getJenisInformasi() != null) {
            JenisInformasiDTO jenisInformasiDTO = new JenisInformasiDTO();
            jenisInformasiDTO.setId(jenisKeterangan.getJenisInformasi().getId());
            // Set atribut lain jika perlu
            jenisKeteranganDTO.setJenisInformasi(jenisInformasiDTO);
        }

        return jenisKeteranganDTO;
    }

    public JenisKeteranganDTO update(Long id, JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        jenisKeterangan.setNama_keterangan(jenisKeteranganDTO.getNama_keterangan());

        if (jenisKeteranganDTO.getJenisInformasi() != null) {
            Long jenisInformasiId = jenisKeteranganDTO.getJenisInformasi().getId();
            JenisInformasi jenisInformasi = jenisInformasiRepository.findById(jenisInformasiId)
                    .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + jenisInformasiId));
            jenisKeterangan.setJenisInformasi(jenisInformasi);
        }

        JenisKeterangan updatedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);
        JenisKeteranganDTO updatedDTO = new JenisKeteranganDTO();
        updatedDTO.setId(updatedJenisKeterangan.getId());
        updatedDTO.setNama_keterangan(updatedJenisKeterangan.getNama_keterangan());

        if (updatedJenisKeterangan.getJenisInformasi() != null) {
            JenisInformasiDTO jenisInformasiDTO = new JenisInformasiDTO();
            jenisInformasiDTO.setId(updatedJenisKeterangan.getJenisInformasi().getId());
            updatedDTO.setJenisInformasi(jenisInformasiDTO);
        }

        return updatedDTO;
    }

    public void delete(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        jenisKeteranganRepository.delete(jenisKeterangan);
    }

}
