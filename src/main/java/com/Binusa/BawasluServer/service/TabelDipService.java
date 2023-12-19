package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.TabelDipDTO;
import com.Binusa.BawasluServer.model.TabelDip;
import com.Binusa.BawasluServer.repository.TabelDipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TabelDipService {
    @Autowired
    TabelDipRepository tabelDipRepository;

    private long id;

    public TabelDipService() {}

//    add
    public TabelDip save(TabelDipDTO tabelDip) {
        TabelDip tabelDip1 = new TabelDip();
        tabelDip1.setDaftarDip(tabelDip.getDaftarDip());
        tabelDip1.setNamadokumen(tabelDip.getNamaDokumen());
        tabelDip1.setPdfDokumen(tabelDip.getPdfDokumen());
        return tabelDipRepository.save(tabelDip1);
    }

    public Optional<TabelDip> findById(Long id) {
        return Optional.ofNullable(tabelDipRepository.findById(id));
    }

//    delete
    public void delete(Long id) {
        TabelDip tabelDip = tabelDipRepository.findById(id);
        tabelDipRepository.delete(tabelDip);
    }

//    update
    public TabelDip update(Long id, TabelDipDTO tabelDip) throws Exception {
        TabelDip tabelDip1 = tabelDipRepository.findById(id);
        tabelDip1.setDaftarDip(tabelDip.getDaftarDip());
        tabelDip1.setNamadokumen(tabelDip.getNamaDokumen());
        tabelDip1.setPdfDokumen(tabelDip.getPdfDokumen());
        return tabelDipRepository.save(tabelDip1);
    }

//    get by id
    public TabelDip getTabelDipById(Long id) throws Exception {
        TabelDip tabelDip = tabelDipRepository.findById(id);
        if (tabelDip == null) throw new Exception("Tabel dip not found!!!");
        return tabelDip;
    }

//    get all by data terbaru
    public Page<TabelDip> findAllWithPagination(Pageable pageable) {
        return tabelDipRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

//    get all by daftar regulasi & created date
    public Page<TabelDip> allByDaftarDipCreatedDate(Pageable pageable, String daftarDip) {
        return tabelDipRepository.allByDaftarDip(pageable, daftarDip);
    }

//    get all by daftar regulasi
    public Page<TabelDip> allByDaftarDip(Pageable pageable, String daftarDip) {
        return tabelDipRepository.allByDaftarDip(pageable, daftarDip);
    }
}
