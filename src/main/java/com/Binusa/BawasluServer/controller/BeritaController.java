package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.service.BeritaService;
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
public class BeritaController {

    public static final Logger logger = LoggerFactory.getLogger(BeritaController.class);

    @Autowired
    private BeritaService beritaService;


    //--------------------- Create a Berita ---------------------------------

    @RequestMapping(value = "/berita/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createberita(@RequestBody BeritaDTO berita) throws SQLException, ClassNotFoundException {
        logger.info("Creating berita : {}",berita);

        beritaService.save(berita);

        return new ResponseEntity<>(berita, HttpStatus.CREATED);
    }

    // -------------------Retrieve All Berita--------------------------------------------

    @RequestMapping(value = "/berita", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Berita>> listAllBerita() throws SQLException, ClassNotFoundException {

        List<Berita> berita = beritaService.findAll();

        return new ResponseEntity<>(berita, HttpStatus.OK);
    }

    // -------------------Retrieve Single Berita By Id------------------------------------------

    @RequestMapping(value = "/berita/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBerita(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        logger.info("Fetching Berita with id {}", id);

        Optional<Berita> berita = beritaService.findById(id);

        if (berita == null) {
            logger.error("Berita with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Berita with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(berita, HttpStatus.OK);
    }

    // ------------------- Update a Berita ------------------------------------------------
    @RequestMapping(value = "/berita/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBerita(@PathVariable("id") long id, @RequestBody BeritaDTO berita) throws SQLException, ClassNotFoundException {
        logger.info("Updating Berita with id {}", id);

        Optional<Berita> currentBerita = beritaService.findById(id);

        if (currentBerita == null) {
            logger.error("Unable to update. Berita with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update. Berita with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        currentBerita.orElseThrow().setAuthor(berita.getAuthor());
        currentBerita.orElseThrow().setIsi_berita(berita.getIsi_berita());
        currentBerita.orElseThrow().setJudul_berita(berita.getJudul_berita());
        currentBerita.orElseThrow().setTags(berita.getTags());


        beritaService.update(currentBerita.get().getId());
        return new ResponseEntity<>(currentBerita, HttpStatus.OK);

    }

    // ------------------- Delete a Movie-----------------------------------------

    @RequestMapping(value = "/berita/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteberita(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        logger.info("Fetching & Deleting Berita with id {}", id);

        beritaService.delete(id);
        return new ResponseEntity<Berita>(HttpStatus.NO_CONTENT);
    }
}
