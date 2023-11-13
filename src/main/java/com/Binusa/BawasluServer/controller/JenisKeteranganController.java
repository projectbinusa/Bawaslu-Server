package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.response.CustomResponse;
import com.Binusa.BawasluServer.service.JenisKeteranganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bawaslu/api/jenis-keterangan")
@CrossOrigin(origins = "http://localhost:3000")
public class JenisKeteranganController {
    @Autowired
    private JenisKeteranganService jenisKeteranganService;

    // Endpoint untuk membuat jenis keterangan baru
    @PostMapping("/add")
    public ResponseEntity<CustomResponse<JenisKeterangan>> createJenisKeterangan(
            @RequestBody JenisKeterangan jenisKeterangan) {
        JenisKeterangan createdJenisKeterangan = jenisKeteranganService.createJenisKeterangan(jenisKeterangan);
        if (createdJenisKeterangan != null) {
            CustomResponse<JenisKeterangan> response = new CustomResponse<>();
            response.setStatus("success");
            response.setCode(200);
            response.setData(createdJenisKeterangan);
            response.setMessage("Jenis keterangan berhasil dibuat");
            return ResponseEntity.ok(response);
        }
        CustomResponse<JenisKeterangan> response = new CustomResponse<>();
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal membuat jenis keterangan");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // Endpoint untuk membaca semua jenis keterangan
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<JenisKeterangan>>> getAllJenisKeterangan() {
        List<JenisKeterangan> jenisKeteranganList = jenisKeteranganService.getAllJenisKeterangan();
        CustomResponse<List<JenisKeterangan>> response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setData(jenisKeteranganList);
        response.setMessage("Data jenis keterangan");
        return ResponseEntity.ok(response);
    }

    // Endpoint untuk membaca jenis keterangan berdasarkan ID
    @GetMapping("/getBy/{id}")
    public ResponseEntity<CustomResponse<JenisKeterangan>> getJenisKeteranganById(@PathVariable Long id) {
        JenisKeterangan jenisKeterangan = jenisKeteranganService.getJenisKeteranganById(id);
        if (jenisKeterangan != null) {
            CustomResponse<JenisKeterangan> response = new CustomResponse<>();
            response.setStatus("success");
            response.setCode(200);
            response.setData(jenisKeterangan);
            response.setMessage("Detail jenis keterangan");
            return ResponseEntity.ok(response);
        }
        CustomResponse<JenisKeterangan> response = new CustomResponse<>();
        response.setStatus("error");
        response.setCode(404);
        response.setMessage("Jenis keterangan tidak ditemukan");
        return ResponseEntity.notFound().build();
    }

    // Endpoint untuk memperbarui jenis keterangan berdasarkan ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<JenisKeterangan>> updateJenisKeterangan(
            @PathVariable Long id, @RequestBody JenisKeterangan jenisKeterangan) {
        JenisKeterangan updatedJenisKeterangan = jenisKeteranganService.updateJenisKeterangan(id, jenisKeterangan);
        if (updatedJenisKeterangan != null) {
            CustomResponse<JenisKeterangan> response = new CustomResponse<>();
            response.setStatus("success");
            response.setCode(200);
            response.setData(updatedJenisKeterangan);
            response.setMessage("Jenis keterangan berhasil diperbarui");
            return ResponseEntity.ok(response);
        }
        CustomResponse<JenisKeterangan> response = new CustomResponse<>();
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal memperbarui jenis keterangan");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // Endpoint untuk menghapus jenis keterangan berdasarkan ID
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteJenisKeterangan(@PathVariable Long id) {
        jenisKeteranganService.deleteJenisKeterangan(id);
        CustomResponse<Void> response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setMessage("Jenis keterangan berhasil dihapus");
        return ResponseEntity.ok(response);
    }
}
