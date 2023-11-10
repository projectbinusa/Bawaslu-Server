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

    @Autowired
    private IsiInformasiKeteranganRepository isiInformasiKeteranganRepository;

    public List<JenisKeteranganDTO> getAllJenisKeterangan() {
        List<JenisKeterangan> jenisKeteranganList = jenisKeteranganRepository.findAll();
        List<JenisKeteranganDTO> jenisKeteranganDTOList = new ArrayList<>();

        for (JenisKeterangan jenisKeterangan : jenisKeteranganList) {
            JenisKeteranganDTO jenisKeteranganDTO = new JenisKeteranganDTO();
            jenisKeteranganDTO.setId(jenisKeterangan.getId());
            jenisKeteranganDTO.setNama_keterangan(jenisKeterangan.getNama_keterangan());
            // Set other properties if needed
            jenisKeteranganDTOList.add(jenisKeteranganDTO);
        }

        return jenisKeteranganDTOList;
    }

    public JenisKeteranganDTO save(JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan jenisKeterangan = new JenisKeterangan();
        jenisKeterangan.setNama_keterangan(jenisKeteranganDTO.getNama_keterangan());

        // Menyertakan data terkait IsiInformasiKeterangan jika ada
        if (jenisKeteranganDTO.getIsiInformasiKeteranganDTOList() != null) {
            List<IsiInformasiKeterangan> isiInformasiKeteranganList = jenisKeteranganDTO.getIsiInformasiKeteranganDTOList()
                    .stream()
                    .map(isiDTO -> {
                        IsiInformasiKeterangan isiInformasiKeterangan = new IsiInformasiKeterangan();
                        isiInformasiKeterangan.setDokumen(isiDTO.getDokumen());
                        // Set properti lain jika ada
                        return isiInformasiKeterangan;
                    })
                    .collect(Collectors.toList());

            jenisKeterangan.setIsiInformasiKeteranganList(isiInformasiKeteranganList);
        }

        JenisKeterangan savedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);
        JenisKeteranganDTO savedDTO = new JenisKeteranganDTO();
        savedDTO.setId(savedJenisKeterangan.getId());
        savedDTO.setNama_keterangan(savedJenisKeterangan.getNama_keterangan());
        savedDTO.setIsiInformasiKeteranganDTOList(
                savedJenisKeterangan.getIsiInformasiKeteranganList()
                        .stream()
                        .map(isiInformasiKeterangan -> {
                            IsiInformasiKeteranganDTO isiDTO = new IsiInformasiKeteranganDTO();
                            isiDTO.setId(isiInformasiKeterangan.getId());
                            isiDTO.setDokumen(isiInformasiKeterangan.getDokumen());
                            // Set properti lain jika ada
                            return isiDTO;
                        })
                        .collect(Collectors.toList())
        );

        return savedDTO;
    }

    public JenisKeteranganDTO findById(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));

        // Menggunakan eager loading untuk mengambil data terkait IsiInformasiKeterangan
        jenisKeterangan.getIsiInformasiKeteranganList().size();

        JenisKeteranganDTO jenisKeteranganDTO = new JenisKeteranganDTO();
        jenisKeteranganDTO.setId(jenisKeterangan.getId());
        jenisKeteranganDTO.setNama_keterangan(jenisKeterangan.getNama_keterangan());
        jenisKeteranganDTO.setIsiInformasiKeteranganDTOList(
                jenisKeterangan.getIsiInformasiKeteranganList()
                        .stream()
                        .map(isiInformasiKeterangan -> {
                            IsiInformasiKeteranganDTO isiDTO = new IsiInformasiKeteranganDTO();
                            isiDTO.setId(isiInformasiKeterangan.getId());
                            isiDTO.setDokumen(isiInformasiKeterangan.getDokumen());
                            // Set properti lain jika ada
                            return isiDTO;
                        })
                        .collect(Collectors.toList())
        );

        return jenisKeteranganDTO;
    }

    public List<JenisKeteranganDTO> getJenisKeteranganByJenisInformasiId(Long jenisInformasiId) {
        // Menggunakan repository untuk mengambil daftar jenis keterangan berdasarkan jenis informasi ID
        List<JenisKeterangan> jenisKeteranganList = jenisKeteranganRepository.findByJenisInformasiId(jenisInformasiId);

        List<JenisKeteranganDTO> jenisKeteranganDTOList = jenisKeteranganList.stream()
                .map(jenisKeterangan -> {
                    JenisKeteranganDTO jenisKeteranganDTO = new JenisKeteranganDTO();
                    jenisKeteranganDTO.setId(jenisKeterangan.getId());
                    jenisKeteranganDTO.setNama_keterangan(jenisKeterangan.getNama_keterangan());
                    return jenisKeteranganDTO;
                })
                .collect(Collectors.toList());

        return jenisKeteranganDTOList;
    }


    public JenisKeteranganDTO update(Long id, JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));

        jenisKeterangan.setNama_keterangan(jenisKeteranganDTO.getNama_keterangan());

        // Hapus isiInformasiKeteranganList yang ada
        if (jenisKeterangan.getIsiInformasiKeteranganList() != null) {
            jenisKeterangan.getIsiInformasiKeteranganList().clear();
        }

        // Menyertakan data terkait IsiInformasiKeterangan jika ada
        if (jenisKeteranganDTO.getIsiInformasiKeteranganDTOList() != null) {
            List<IsiInformasiKeterangan> isiInformasiKeteranganList = jenisKeteranganDTO.getIsiInformasiKeteranganDTOList()
                    .stream()
                    .map(isiDTO -> {
                        IsiInformasiKeterangan isiInformasiKeterangan = new IsiInformasiKeterangan();
                        isiInformasiKeterangan.setDokumen(isiDTO.getDokumen());
                        // Set properti lain jika ada
                        return isiInformasiKeterangan;
                    })
                    .collect(Collectors.toList());

            jenisKeterangan.setIsiInformasiKeteranganList(isiInformasiKeteranganList);
        }

        JenisKeterangan updatedJenisKeterangan = jenisKeteranganRepository.save(jenisKeterangan);
        JenisKeteranganDTO updatedDTO = new JenisKeteranganDTO();
        updatedDTO.setId(updatedJenisKeterangan.getId());
        updatedDTO.setNama_keterangan(updatedJenisKeterangan.getNama_keterangan());
        updatedDTO.setIsiInformasiKeteranganDTOList(
                updatedJenisKeterangan.getIsiInformasiKeteranganList()
                        .stream()
                        .map(isiInformasiKeterangan -> {
                            IsiInformasiKeteranganDTO isiDTO = new IsiInformasiKeteranganDTO();
                            isiDTO.setId(isiInformasiKeterangan.getId());
                            isiDTO.setDokumen(isiInformasiKeterangan.getDokumen());
                            // Set properti lain jika ada
                            return isiDTO;
                        })
                        .collect(Collectors.toList())
        );

        return updatedDTO;
    }

    public void delete(Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + id));
        jenisKeteranganRepository.delete(jenisKeterangan);
    }
}
