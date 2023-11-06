package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.JenisKeteranganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/jenisketerangan")
@CrossOrigin(origins = "http://localhost:3000")
public class JenisKeteranganController {

    @Autowired
    private JenisKeteranganService jenisKeteranganService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<JenisKeteranganDTO>> createJenisKeterangan(@RequestBody JenisKeteranganDTO jenisKeteranganDTO) {
        CommonResponse<JenisKeteranganDTO> response = new CommonResponse<>();
        try {
            JenisKeteranganDTO savedDTO = jenisKeteranganService.save(jenisKeteranganDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(savedDTO);
            response.setMessage("Jenis Keterangan created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create Jenis Keterangan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<JenisKeteranganDTO>> getJenisKeterangan(@PathVariable("id") Long id) {
        CommonResponse<JenisKeteranganDTO> response = new CommonResponse<>();
        try {
            JenisKeteranganDTO jenisKeteranganDTO = jenisKeteranganService.findById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenisKeteranganDTO);
            response.setMessage("Jenis Keterangan retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve Jenis Keterangan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<JenisKeteranganDTO>> updateJenisKeterangan(@PathVariable("id") Long id, @RequestBody JenisKeteranganDTO jenisKeteranganDTO) {
        CommonResponse<JenisKeteranganDTO> response = new CommonResponse<>();
        try {
            JenisKeteranganDTO updatedDTO = jenisKeteranganService.update(id, jenisKeteranganDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(updatedDTO);
            response.setMessage("Jenis Keterangan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update Jenis Keterangan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteJenisKeterangan(@PathVariable("id") Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            jenisKeteranganService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Jenis Keterangan deleted successfully.");
            response.setMessage("Jenis Keterangan with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete Jenis Keterangan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
