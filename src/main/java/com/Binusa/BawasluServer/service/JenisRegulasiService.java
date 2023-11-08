package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.model.JenisRegulasi;
import com.Binusa.BawasluServer.repository.JenisRegulasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JenisRegulasiService {
    @Autowired
    private JenisRegulasiRepository jenisRegulasiRepository;

    private long id;

    public Optional<JenisRegulasi> findById(Long id) {
        return Optional.ofNullable(jenisRegulasiRepository.findById(id));
    }

    public JenisRegulasi save(JenisRegulasi jenisRegulasi){
        JenisRegulasi jenisRegulasi1 = new JenisRegulasi();
        jenisRegulasi1.setJenisRegulasi(jenisRegulasi.getJenisRegulasi());

        return jenisRegulasiRepository.save(jenisRegulasi1);
    }

    public List<JenisRegulasi> allJenisRegulasi() {
        List<JenisRegulasi> jenisRegulasis = new ArrayList<>();
        jenisRegulasiRepository.findAll().forEach(jenisRegulasis::add);
        return jenisRegulasis;
    }

    public JenisRegulasi getJenisRegulasiById(Long id) throws Exception{
        JenisRegulasi jenisRegulasi = jenisRegulasiRepository.findById(id);
        if (jenisRegulasi == null) throw new Exception("Jenis regulasi not found!!!");
        return jenisRegulasi;
    }

    public void delete(Long id) {
        JenisRegulasi jenisRegulasi = jenisRegulasiRepository.findById(id);
        jenisRegulasiRepository.delete(jenisRegulasi);
    }

    public JenisRegulasi update(long id, JenisRegulasi jenisRegulasi) throws Exception{
        JenisRegulasi jenisRegulasi1 = jenisRegulasiRepository.findById(id);
        jenisRegulasi1.setJenisRegulasi(jenisRegulasi.getJenisRegulasi());
        return jenisRegulasiRepository.save(jenisRegulasi1);
    }
}
