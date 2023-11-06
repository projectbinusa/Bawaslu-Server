package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.BeritaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BeritaController {

    @Autowired
    private BeritaService beritaService;

    @RequestMapping(value = "/berita/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<BeritaDTO>> createberita(@RequestBody BeritaDTO berita) throws SQLException, ClassNotFoundException {
        CommonResponse<BeritaDTO> response = new CommonResponse<>();
        try {
            beritaService.save(berita);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(berita);
            response.setMessage("Berita created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> listAllBerita() throws SQLException, ClassNotFoundException {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> berita = beritaService.findAll();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse<BeritaDTO>> updateBerita(@PathVariable("id") long id, @RequestBody BeritaDTO berita) throws SQLException, ClassNotFoundException {
        CommonResponse<BeritaDTO> response = new CommonResponse<>();
        try {
            Optional<Berita> currentBerita = beritaService.findById(id);

            if (!currentBerita.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Berita with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update berita here...

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita);
            response.setMessage("Berita updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deleteberita(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            beritaService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Berita deleted successfully.");
            response.setMessage("Berita with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
