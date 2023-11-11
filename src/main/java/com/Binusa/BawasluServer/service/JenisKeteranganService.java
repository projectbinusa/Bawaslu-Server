package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import com.Binusa.BawasluServer.repository.JenisKeteranganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JenisKeteranganService {
    @Autowired
    private JenisKeteranganRepository jenisKeteranganRepository;

    // Method untuk membuat jenis keterangan baru
    public JenisKeterangan createJenisKeterangan(JenisKeterangan jenisKeterangan) {
        return jenisKeteranganRepository.save(jenisKeterangan);
    }

    // Method untuk membaca semua jenis keterangan
    public List<JenisKeterangan> getAllJenisKeterangan() {
        return jenisKeteranganRepository.findAll();
    }

    // Method untuk membaca jenis keterangan berdasarkan ID
    public JenisKeterangan getJenisKeteranganById(Long id) {
        return jenisKeteranganRepository.findById(id)
                .orElse(null);
    }

    // Method untuk memperbarui jenis keterangan
    public JenisKeterangan updateJenisKeterangan(Long id, JenisKeterangan jenisKeterangan) {
        JenisKeterangan existingJenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElse(null);
        if (existingJenisKeterangan != null) {
            existingJenisKeterangan.setKeterangan(jenisKeterangan.getKeterangan());
            return jenisKeteranganRepository.save(existingJenisKeterangan);
        }
        return null;
    }

    // Method untuk menghapus jenis keterangan
    public void deleteJenisKeterangan(Long id) {
        jenisKeteranganRepository.deleteById(id);
    }
}

