package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.JenisInformasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bawaslu/api/jenisinformasi")
@CrossOrigin(origins = "http://localhost:3000")
public class JenisInformasiController {

    @Autowired
    private JenisInformasiService jenisInformasiService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<JenisInformasiDTO>> createJenisInformasi(@RequestBody JenisInformasiDTO jenisInformasiDTO) {
        CommonResponse<JenisInformasiDTO> response = new CommonResponse<>();
        try {
            JenisInformasiDTO savedDTO = jenisInformasiService.save(jenisInformasiDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(savedDTO);
            response.setMessage("Jenis Informasi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create Jenis Informasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<JenisInformasiDTO>> getJenisInformasi(@PathVariable("id") Long id) {
        CommonResponse<JenisInformasiDTO> response = new CommonResponse<>();
        try {
            JenisInformasiDTO jenisInformasiDTO = jenisInformasiService.findById(id);

            // Mengambil jenis keterangan terkait dengan jenis informasi berdasarkan ID jenis informasi
            List<JenisKeteranganDTO> jenisKeteranganList = jenisInformasiService.getJenisKeteranganByJenisInformasiId(id);
            jenisInformasiDTO.setJenisKeteranganList(jenisKeteranganList);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenisInformasiDTO);
            response.setMessage("Jenis Informasi retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve Jenis Informasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<JenisInformasiDTO>> updateJenisInformasi(@PathVariable("id") Long id, @RequestBody JenisInformasiDTO jenisInformasiDTO) {
        CommonResponse<JenisInformasiDTO> response = new CommonResponse<>();
        try {
            JenisInformasiDTO updatedDTO = jenisInformasiService.update(id, jenisInformasiDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(updatedDTO);
            response.setMessage("Jenis Informasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update Jenis Informasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteJenisInformasi(@PathVariable("id") Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            jenisInformasiService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Jenis Informasi deleted successfully.");
            response.setMessage("Jenis Informasi with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete Jenis Informasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
