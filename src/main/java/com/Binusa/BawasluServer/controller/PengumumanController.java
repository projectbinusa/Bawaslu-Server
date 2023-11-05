package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.DTO.PengumumanDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.model.Pengumuman;
import com.Binusa.BawasluServer.service.PengumumanService;
import com.Binusa.BawasluServer.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PengumumanController {

    public static final Logger logger = LoggerFactory.getLogger(PengumumanController.class);

    @Autowired
    private PengumumanService pengumumanService;

    @RequestMapping(value = "/pengumuman/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createpengumuman(@RequestBody PengumumanDTO pengumuman) throws SQLException, ClassNotFoundException {
        logger.info("Creating pengumuman : {}",pengumuman);

        pengumumanService.save(pengumuman);

        return new ResponseEntity<>(pengumuman, HttpStatus.CREATED);
    }

    // -------------------Retrieve All Berita--------------------------------------------

    @RequestMapping(value = "/pengumuman", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Pengumuman>> listAllPengumuman() throws SQLException, ClassNotFoundException {

        List<Pengumuman> pengumuman = pengumumanService.findAll();

        return new ResponseEntity<>(pengumuman, HttpStatus.OK);
    }


    @RequestMapping(value = "/pengumuman/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBerita(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        logger.info("Fetching Pengumuman with id {}", id);

        Optional<Pengumuman> pengumuman = pengumumanService.findById(id);

        if (pengumuman == null) {
            logger.error("Pengumuman with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Pengumuman with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pengumuman, HttpStatus.OK);
    }

    @RequestMapping(value = "/pengumuman/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePengumuman(@PathVariable("id") long id, @RequestBody PengumumanDTO pengumuman) throws SQLException, ClassNotFoundException {
        logger.info("Updating Berita with id {}", id);

        Optional<Pengumuman> currentPengumuman = pengumumanService.findById(id);

        if (currentPengumuman == null) {
            logger.error("Unable to update. Berita with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update. Berita with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        currentPengumuman.orElseThrow().setAuthor(pengumuman.getAuthor());
        currentPengumuman.orElseThrow().setIsi_pengumuman(pengumuman.getIsi_pengumuman());
        currentPengumuman.orElseThrow().setJudul_pengumuman(pengumuman.getJudul_pengumuman());
        currentPengumuman.orElseThrow().setTags(pengumuman.getTags());


        pengumumanService.update(currentPengumuman.get().getId());
        return new ResponseEntity<>(currentPengumuman, HttpStatus.OK);

    }


    @RequestMapping(value = "/pengumuman/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletepengumuman(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        logger.info("Fetching & Deleting Pengumuman with id {}", id);

        pengumumanService.delete(id);
        return new ResponseEntity<Berita>(HttpStatus.NO_CONTENT);
    }
}
