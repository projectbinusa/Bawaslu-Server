package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.repository.JenisInformasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class JenisInformasiService {

    @Autowired
    private JenisInformasiRepository jenisInformasiRepository;

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

        return jenisInformasiDTO;
    }

    public JenisInformasiDTO update(Long id, JenisInformasiDTO jenisInformasiDTO) {
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));
        jenisInformasi.setNama_jenis(jenisInformasiDTO.getNama_jenis());

        JenisInformasi updatedJenisInformasi = jenisInformasiRepository.save(jenisInformasi);
        JenisInformasiDTO updatedDTO = new JenisInformasiDTO();
        updatedDTO.setId(updatedJenisInformasi.getId());
        updatedDTO.setNama_jenis(updatedJenisInformasi.getNama_jenis());

        return updatedDTO;
    }

    public void delete(Long id) {
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));
        jenisInformasiRepository.delete(jenisInformasi);
    }

    // Tambahkan metode lain jika diperlukan, seperti mendapatkan semua JenisInformasi
}



