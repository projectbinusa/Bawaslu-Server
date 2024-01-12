package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganApiResponseDTO;
import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganByJenisKeteranganApiResponseDTO;
import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import com.Binusa.BawasluServer.repository.JenisInformasiRepository;
import com.Binusa.BawasluServer.repository.JenisKeteranganRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JenisKeteranganService {
    @Autowired
    private JenisKeteranganRepository jenisKeteranganRepository;

    @Autowired
    private JenisInformasiRepository jenisInformasiRepository;

    @Autowired
    private IsiInformasiKeteranganRepository isiInformasiKeteranganRepository;

    public JenisKeteranganDTO createJenisKeterangan(JenisKeteranganDTO jenisKeteranganDTO) {
        // Ubah jenisKeteranganDTO ke entitas JenisKeterangan sebelum menyimpan
        JenisKeterangan jenisKeterangan = new JenisKeterangan();
        jenisKeterangan.setId(jenisKeteranganDTO.getId());
        jenisKeterangan.setKeterangan(jenisKeteranganDTO.getKeterangan());

        // Lakukan validasi jenisInformasi, misalnya dengan mencari entitas jenisInformasi dari repository
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(jenisKeteranganDTO.getJenisInformasi())
                .orElse(null);
        if (jenisInformasi == null) {
            throw new IllegalArgumentException("JenisInformasi not found with id: " + jenisKeteranganDTO.getJenisInformasi());
        }

        jenisKeterangan.setJenisInformasi(jenisInformasi);

        // Simpan entitas JenisKeterangan yang sudah diisi dengan data dari jenisKeteranganDTO
        JenisKeterangan savedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);

        // Konversi entitas JenisKeterangan yang sudah tersimpan kembali ke JenisKeteranganDTO
        JenisKeteranganDTO savedJenisKeteranganDTO = new JenisKeteranganDTO();
        savedJenisKeteranganDTO.setId(savedJenisKeterangan.getId());
        savedJenisKeteranganDTO.setKeterangan(savedJenisKeterangan.getKeterangan());
        savedJenisKeteranganDTO.setJenisInformasi(savedJenisKeterangan.getJenisInformasi().getId());

        return savedJenisKeteranganDTO;
    }


    // Method untuk membaca semua jenis keterangan
    public List<JenisKeteranganDTO> getAllJenisKeterangan() {
        List<JenisKeterangan> jenisKeteranganList = jenisKeteranganRepository.findAll();
        return jenisKeteranganList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Method untuk membaca jenis keterangan berdasarkan ID
    public JenisKeteranganDTO getJenisKeteranganById(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id).orElse(null);
        if (jenisKeterangan != null) {
            return convertToDTO(jenisKeterangan);
        }
        return null;
    }

    // Method untuk memperbarui jenis keterangan
    public JenisKeteranganDTO updateJenisKeterangan(Long id, JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan updatedJenisKeterangan = convertToEntity(jenisKeteranganDTO);
        updatedJenisKeterangan.setId(id);
        updatedJenisKeterangan = jenisKeteranganRepository.save(updatedJenisKeterangan);
        return convertToDTO(updatedJenisKeterangan);
    }

    // Method untuk menghapus jenis keterangan
    public void deleteJenisKeterangan(Long id) {
        jenisKeteranganRepository.deleteById(id);
    }

    // Method untuk mengonversi JenisKeterangan menjadi JenisKeteranganDTO
    private JenisKeteranganDTO convertToDTO(JenisKeterangan jenisKeterangan) {
        JenisKeteranganDTO dto = new JenisKeteranganDTO();
        dto.setId(jenisKeterangan.getId());
        dto.setKeterangan(jenisKeterangan.getKeterangan());
        dto.setJenisInformasi(jenisKeterangan.getJenisInformasi().getId());
        return dto;
    }

    // Method untuk mengonversi JenisKeteranganDTO menjadi JenisKeterangan
    private JenisKeterangan convertToEntity(JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan entity = new JenisKeterangan();
        entity.setId(jenisKeteranganDTO.getId());
        entity.setKeterangan(jenisKeteranganDTO.getKeterangan());

        // Set jenisInformasi menggunakan ID yang diberikan dalam DTO
        JenisInformasi jenisInformasi = new JenisInformasi();
        jenisInformasi.setId(jenisKeteranganDTO.getJenisInformasi());
        entity.setJenisInformasi(jenisInformasi);

        return entity;
    }
    public Page<IsiInformasiKeteranganApiResponseDTO> getByCategoryWithPagination(Long jenisKeteranganId, Pageable pageable) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(jenisKeteranganId)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + jenisKeteranganId));

        Page<IsiInformasiKeterangan> isiInformasiKeteranganPage = isiInformasiKeteranganRepository.findByJenisKeteranganId(jenisKeteranganId, pageable);

        List<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganApiResponseDTOList = isiInformasiKeteranganPage.getContent().stream()
                .map(this::mapToApiResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(isiInformasiKeteranganApiResponseDTOList, pageable, isiInformasiKeteranganPage.getTotalElements());
    }

    private IsiInformasiKeteranganApiResponseDTO mapToApiResponseDTO(IsiInformasiKeterangan isiInformasiKeterangan) {
        IsiInformasiKeteranganApiResponseDTO isiInformasiKeteranganDTO = new IsiInformasiKeteranganApiResponseDTO();
        isiInformasiKeteranganDTO.setId(isiInformasiKeterangan.getId());
        isiInformasiKeteranganDTO.setDokumen(isiInformasiKeterangan.getDokumen());
        isiInformasiKeteranganDTO.setPdfDokumen(isiInformasiKeterangan.getPdfDokumen());

        // Mengisi properti jenisKeterangan dengan nilai yang sesuai
        if (isiInformasiKeterangan.getJenisKeterangan() != null) {
            isiInformasiKeteranganDTO.setJenisKeterangan(isiInformasiKeterangan.getJenisKeterangan().getId());
        }

        return isiInformasiKeteranganDTO;
    }

}
