package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.repository.BeritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BeritaService {

    @Autowired
    private BeritaRepository beritaDao;
    private long id;

    public BeritaService() {
    }

    public Berita save(BeritaDTO berita) {

        Berita newBerita = new Berita();
        newBerita.setAuthor(berita.getAuthor());
        newBerita.setIsi_berita(berita.getIsi_berita());
        newBerita.setJudul_berita(berita.getJudul_berita());
        newBerita.setTags(berita.getTags());



        return beritaDao.save(newBerita);
    }


    public Optional<Berita> findById(Long id) {
        return Optional.ofNullable(beritaDao.findById(id));
    }


    public List<Berita> findAll() {
        List<Berita> berita = new ArrayList<>();
        beritaDao.findAll().forEach(berita::add);
        return berita;
    }


    public void delete(Long id) {
        Berita berita = beritaDao.findById(id);
        beritaDao.delete(berita);
    }


    public Berita update(Long id) {
        Berita berita = beritaDao.findById(id);
        return beritaDao.save(berita);
    }

}