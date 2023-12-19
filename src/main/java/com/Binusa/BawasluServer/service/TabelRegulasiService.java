package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.TabelRegulasiDTO;
import com.Binusa.BawasluServer.model.TabelRegulasi;
import com.Binusa.BawasluServer.repository.TabelRegulasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TabelRegulasiService {
    @Autowired
    TabelRegulasiRepository tabelRegulasiRepository;

    private long id;

    public TabelRegulasiService() {}

//    add
    public TabelRegulasi save(TabelRegulasiDTO tabelRegulasi) {
        TabelRegulasi tabelRegulasi1 = new TabelRegulasi();
        tabelRegulasi1.setDaftarRegulasi(tabelRegulasi.getDaftarRegulasi());
        tabelRegulasi1.setNamaDokumen(tabelRegulasi.getNamaDokumen());
        tabelRegulasi1.setPdfDokumen(tabelRegulasi.getPdfDokumen());
        return tabelRegulasiRepository.save(tabelRegulasi1);
    }

    public Optional<TabelRegulasi> findById(Long id) {
        return Optional.ofNullable(tabelRegulasiRepository.findById(id));
    }

//    delete
    public void delete(Long id) {
        TabelRegulasi tabelRegulasi = tabelRegulasiRepository.findById(id);
        tabelRegulasiRepository.delete(tabelRegulasi);
    }

//    update
    public TabelRegulasi update(Long id, TabelRegulasiDTO tabelRegulasi) throws Exception {
        TabelRegulasi tabelRegulasi1 = tabelRegulasiRepository.findById(id);
        tabelRegulasi1.setDaftarRegulasi(tabelRegulasi.getDaftarRegulasi());
        tabelRegulasi1.setNamaDokumen(tabelRegulasi.getNamaDokumen());
        tabelRegulasi1.setPdfDokumen(tabelRegulasi.getPdfDokumen());
        return tabelRegulasiRepository.save(tabelRegulasi1);
    }

//    get by id
    public TabelRegulasi getTabelRegulasiById(Long id) throws Exception {
        TabelRegulasi tabelRegulasi = tabelRegulasiRepository.findById(id);
        if (tabelRegulasi == null) throw new Exception("Tabel regulasi not found!!!");
        return tabelRegulasi;
    }

//    get all by data terbaru
    public Page<TabelRegulasi> findAllWithPagination(Pageable pageable) {
        return tabelRegulasiRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

//    get all by daftar regulasi & created date
    public Page<TabelRegulasi> allByDaftarRegulasiCreatedDate(Pageable pageable, String daftarRegulasi) {
        return tabelRegulasiRepository.allByDaftarRegulasiByCreatedDate(pageable, daftarRegulasi);
    }

//    get all by daftar regulasi
    public Page<TabelRegulasi> allByDaftarRegulasi(Pageable pageable, String daftarRegulasi) {
        return tabelRegulasiRepository.allByDaftarRegulasi(pageable, daftarRegulasi);
    }
}
