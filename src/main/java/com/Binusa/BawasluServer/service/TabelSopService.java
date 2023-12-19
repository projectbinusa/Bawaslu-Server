package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.TabelSopDto;
import com.Binusa.BawasluServer.model.TabelSop;
import com.Binusa.BawasluServer.repository.TabelSopReporitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TabelSopService {
    @Autowired
    TabelSopReporitory tabelSopReporitory;

    private long id;

    public TabelSopService() {}

//    add
    public TabelSop save(TabelSopDto tabelSopDto) {
        TabelSop tabelSop = new TabelSop();
        tabelSop.setDaftarSop(tabelSopDto.getDaftarSop());
        tabelSop.setNamaDokumen(tabelSopDto.getNamaDokumen());
        tabelSop.setPdfDokumen(tabelSopDto.getPdfDokumen());
        return tabelSopReporitory.save(tabelSop);
    }

    public Optional<TabelSop> findById(Long id) {
        return Optional.ofNullable(tabelSopReporitory.findById(id));
    }

//    delete
    public void delete(Long id) {
        TabelSop tabelSop = tabelSopReporitory.findById(id);
        tabelSopReporitory.delete(tabelSop);
    }

//    update
    public TabelSop update(Long id, TabelSopDto tabelSopDto) throws Exception {
        TabelSop tabelSop = tabelSopReporitory.findById(id);
        tabelSop.setDaftarSop(tabelSopDto.getDaftarSop());
        tabelSop.setNamaDokumen(tabelSopDto.getNamaDokumen());
        tabelSop.setPdfDokumen(tabelSopDto.getPdfDokumen());
        return tabelSopReporitory.save(tabelSop);
    }

//    get by id
    public TabelSop getTabelSopById(Long id) throws Exception {
        TabelSop tabelSop = tabelSopReporitory.findById(id);
        if (tabelSop == null) throw new Exception("Tabel sop not found!!!");
        return tabelSop;
    }

//    get all by data terbaru
    public Page<TabelSop> findAllWithPagination(Pageable pageable) {
        return tabelSopReporitory.findAllByOrderByCreatedDateDesc(pageable);
    }

//    get all by daftar regulasi & created date
    public Page<TabelSop> allByDaftarSopCreatedDate(Pageable pageable, String daftarSop) {
        return tabelSopReporitory.allByDaftarSop(pageable, daftarSop);
    }

//    get all by daftar regulasi
    public Page<TabelSop> allByDaftarSop(Pageable pageable, String daftarSop) {
        return tabelSopReporitory.allByDaftarSop(pageable, daftarSop);
    }
}
