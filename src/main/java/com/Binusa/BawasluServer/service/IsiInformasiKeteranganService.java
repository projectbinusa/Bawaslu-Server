package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IsiInformasiKeteranganService {

    @Autowired
    private IsiInformasiKeteranganRepository isiInformasiKeteranganRepository;

    public IsiInformasiKeteranganDTO save(IsiInformasiKeteranganDTO isiInformasiKeteranganDTO) {
        IsiInformasiKeterangan isiInformasiKeterangan = new IsiInformasiKeterangan();
        isiInformasiKeterangan.setDokumen(isiInformasiKeteranganDTO.getDokumen());

        IsiInformasiKeterangan savedIsiInformasiKeterangan = isiInformasiKeteranganRepository.save(isiInformasiKeterangan);
        IsiInformasiKeteranganDTO savedDTO = new IsiInformasiKeteranganDTO();
        savedDTO.setId(savedIsiInformasiKeterangan.getId());
        savedDTO.setDokumen(savedIsiInformasiKeterangan.getDokumen());

        return savedDTO;
    }

    public IsiInformasiKeteranganDTO findById(Long id) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        IsiInformasiKeteranganDTO isiInformasiKeteranganDTO = new IsiInformasiKeteranganDTO();
        isiInformasiKeteranganDTO.setId(isiInformasiKeterangan.getId());
        isiInformasiKeteranganDTO.setDokumen(isiInformasiKeterangan.getDokumen());

        return isiInformasiKeteranganDTO;
    }

    public IsiInformasiKeteranganDTO update(Long id, IsiInformasiKeteranganDTO isiInformasiKeteranganDTO) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        isiInformasiKeterangan.setDokumen(isiInformasiKeteranganDTO.getDokumen());

        IsiInformasiKeterangan updatedIsiInformasiKeterangan = isiInformasiKeteranganRepository.save(isiInformasiKeterangan);
        IsiInformasiKeteranganDTO updatedDTO = new IsiInformasiKeteranganDTO();
        updatedDTO.setId(updatedIsiInformasiKeterangan.getId());
        updatedDTO.setDokumen(updatedIsiInformasiKeterangan.getDokumen());

        return updatedDTO;
    }

    public void delete(Long id) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        isiInformasiKeteranganRepository.delete(isiInformasiKeterangan);
    }

}
