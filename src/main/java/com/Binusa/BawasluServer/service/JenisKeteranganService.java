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

        // Menambahkan pengisian properti jenisInformasi
        if (jenisKeteranganDTO.getJenisInformasi() != null) {
            Long jenisInformasiId = jenisKeteranganDTO.getJenisInformasi().getId();
            JenisInformasi jenisInformasi = jenisInformasiRepository.findById(jenisInformasiId)
                    .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + jenisInformasiId));
            jenisKeterangan.setJenisInformasi(jenisInformasi);
        }


        JenisKeterangan savedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);
        JenisKeteranganDTO savedDTO = new JenisKeteranganDTO();
        savedDTO.setId(savedJenisKeterangan.getId());
        savedDTO.setNama_keterangan(savedJenisKeterangan.getNama_keterangan());

        return savedDTO;
    }


    public JenisKeteranganDTO findById(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        JenisKeteranganDTO jenisKeteranganDTO = new JenisKeteranganDTO();
        jenisKeteranganDTO.setId(jenisKeterangan.getId());
        jenisKeteranganDTO.setNama_keterangan(jenisKeterangan.getNama_keterangan());

        return jenisKeteranganDTO;
    }

    public JenisKeteranganDTO update(Long id, JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        jenisKeterangan.setNama_keterangan(jenisKeteranganDTO.getNama_keterangan());

        JenisKeterangan updatedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);
        JenisKeteranganDTO updatedDTO = new JenisKeteranganDTO();
        updatedDTO.setId(updatedJenisKeterangan.getId());
        updatedDTO.setNama_keterangan(updatedJenisKeterangan.getNama_keterangan());

        return updatedDTO;
    }

    public void delete(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        jenisKeteranganRepository.delete(jenisKeterangan);
    }

    // Tambahkan metode lain jika diperlukan, seperti mendapatkan semua JenisKeterangan
}
