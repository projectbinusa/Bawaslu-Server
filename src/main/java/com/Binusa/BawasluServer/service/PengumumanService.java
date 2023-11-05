package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.DTO.PengumumanDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.model.Pengumuman;
import com.Binusa.BawasluServer.repository.PengumumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PengumumanService {

    @Autowired
    private PengumumanRepository pengumumanRepository;
    private long id;

    public PengumumanService() {
    }

    public Pengumuman save(PengumumanDTO pengumuman) {

        Pengumuman newPengumuman = new Pengumuman();
        newPengumuman.setAuthor(pengumuman.getAuthor());
        newPengumuman.setIsi_pengumuman(pengumuman.getIsi_pengumuman());
        newPengumuman.setJudul_pengumuman(pengumuman.getJudul_pengumuman());
        newPengumuman.setTags(pengumuman.getTags());



        return pengumumanRepository.save(newPengumuman);
    }


    public Optional<Pengumuman> findById(Long id) {
        return Optional.ofNullable(pengumumanRepository.findById(id));
    }


    public List<Pengumuman> findAll() {
        List<Pengumuman> pengmuman = new ArrayList<>();
        pengumumanRepository.findAll().forEach(pengmuman::add);
        return pengmuman;
    }


    public void delete(Long id) {
        Pengumuman pengumuman = pengumumanRepository.findById(id);
        pengumumanRepository.delete(pengumuman);
    }


    public Pengumuman update(Long id) {
        Pengumuman pengumuman = pengumumanRepository.findById(id);
        return pengumumanRepository.save(pengumuman);
    }

}