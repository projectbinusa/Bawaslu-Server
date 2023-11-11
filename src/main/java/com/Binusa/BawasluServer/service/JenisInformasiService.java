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

    // Method untuk membuat jenis informasi baru
    public JenisInformasi createJenisInformasi(JenisInformasi jenisInformasi) {
        return jenisInformasiRepository.save(jenisInformasi);
    }

    // Method untuk membaca semua jenis informasi
    public List<JenisInformasi> getAllJenisInformasi() {
        return jenisInformasiRepository.findAll();
    }

    // Method untuk membaca jenis informasi berdasarkan ID
    public JenisInformasi getJenisInformasiById(Long id) {
        return jenisInformasiRepository.findById(id)
                .orElse(null);
    }

    // Method untuk memperbarui jenis informasi
    public JenisInformasi updateJenisInformasi(Long id, JenisInformasi jenisInformasi) {
        JenisInformasi existingJenisInformasi = jenisInformasiRepository.findById(id)
                .orElse(null);
        if (existingJenisInformasi != null) {
            existingJenisInformasi.setNamaInformasi(jenisInformasi.getNamaInformasi());
            return jenisInformasiRepository.save(existingJenisInformasi);
        }
        return null;
    }

    // Method untuk menghapus jenis informasi
    public void deleteJenisInformasi(Long id) {
        jenisInformasiRepository.deleteById(id);
    }
}




