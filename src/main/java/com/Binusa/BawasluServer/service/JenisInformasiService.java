package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisInformasiKeteranganDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganInformasiDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import com.Binusa.BawasluServer.repository.JenisInformasiRepository;
import com.Binusa.BawasluServer.repository.JenisKeteranganRepository;
import com.Binusa.BawasluServer.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JenisInformasiService {
    @Autowired
    private JenisInformasiRepository jenisInformasiRepository;

    public JenisInformasi createJenisInformasi(JenisInformasiDTO jenisInformasiDTO) {
        JenisInformasi jenisInformasi = new JenisInformasi();
        jenisInformasi.setNamaInformasi(jenisInformasiDTO.getNamaInformasi());
        return jenisInformasiRepository.save(jenisInformasi);
    }

    public Page<JenisInformasi> getAllJenisInformasi(Pageable pageable) {
        return jenisInformasiRepository.findAll(pageable);
    }

    public JenisInformasi getJenisInformasiById(Long id) {
        Optional<JenisInformasi> jenisInformasi = jenisInformasiRepository.findById(id);
        return jenisInformasi.orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));
    }

    public JenisInformasi updateJenisInformasi(Long id, JenisInformasiDTO jenisInformasiDTO) {
        Optional<JenisInformasi> existingJenisInformasi = jenisInformasiRepository.findById(id);
        if (existingJenisInformasi.isPresent()) {
            JenisInformasi jenisInformasi = existingJenisInformasi.get();
            if (jenisInformasiDTO.getNamaInformasi() != null) {
                jenisInformasi.setNamaInformasi(jenisInformasiDTO.getNamaInformasi());
            }
            return jenisInformasiRepository.save(jenisInformasi);
        }
        return null;
    }

    public void deleteJenisInformasi(Long id) {
        try {
            jenisInformasiRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Jenis informasi dengan ID " + id + " tidak dapat ditemukan.");
        } catch (DataIntegrityViolationException e) {
        }
    }


    public Page<JenisInformasiKeteranganDTO> getJenisInformasiWithKeterangan(Long id, Pageable pageable) {
        JenisInformasi jenisInformasi = jenisInformasiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisInformasi not found with id: " + id));

        Page<JenisKeteranganInformasiDTO> jenisKeteranganInformasiDTOPage = mapJenisKeteranganToDTOPage(jenisInformasi.getJenisKeteranganList(), pageable);

        List<JenisInformasiKeteranganDTO> result = new ArrayList<>();
        for (JenisKeteranganInformasiDTO jenisKeteranganInformasiDTO : jenisKeteranganInformasiDTOPage.getContent()) {
            JenisInformasiKeteranganDTO jenisInformasiKeteranganDTO = new JenisInformasiKeteranganDTO();
            jenisInformasiKeteranganDTO.setId(jenisInformasi.getId());
            jenisInformasiKeteranganDTO.setNamaInformasi(jenisInformasi.getNamaInformasi());
            jenisInformasiKeteranganDTO.setJenisKeteranganInformasiDTOList(Collections.singletonList(jenisKeteranganInformasiDTO));
            result.add(jenisInformasiKeteranganDTO);
        }

        return new PageImpl<>(result, pageable, jenisKeteranganInformasiDTOPage.getTotalElements());
    }

    private Page<JenisKeteranganInformasiDTO> mapJenisKeteranganToDTOPage(List<JenisKeterangan> jenisKeteranganList, Pageable pageable) {
        List<JenisKeteranganInformasiDTO> jenisKeteranganInformasiDTOList = new ArrayList<>();
        for (JenisKeterangan jenisKeterangan : jenisKeteranganList) {
            JenisKeteranganInformasiDTO jenisKeteranganInformasiDTO = new JenisKeteranganInformasiDTO();
            jenisKeteranganInformasiDTO.setId(jenisKeterangan.getId());
            jenisKeteranganInformasiDTO.setKeterangan(jenisKeterangan.getKeterangan());
            jenisKeteranganInformasiDTOList.add(jenisKeteranganInformasiDTO);
        }

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > jenisKeteranganInformasiDTOList.size() ? jenisKeteranganInformasiDTOList.size() : (start + pageable.getPageSize());
        Page<JenisKeteranganInformasiDTO> jenisKeteranganInformasiDTOPage = new PageImpl<>(jenisKeteranganInformasiDTOList.subList(start, end), pageable, jenisKeteranganInformasiDTOList.size());

        return jenisKeteranganInformasiDTOPage;
    }

}



