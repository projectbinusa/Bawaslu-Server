package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.response.CustomResponse;
import com.Binusa.BawasluServer.service.JenisInformasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bawaslu/api/jenis-informasi")
@CrossOrigin(origins = "http://localhost:3000")
public class JenisInformasiController {
    @Autowired
    private JenisInformasiService jenisInformasiService;

    // Endpoint untuk membuat jenis informasi baru
    @PostMapping("/add")
    public ResponseEntity<CustomResponse<JenisInformasi>> createJenisInformasi(
            @RequestBody JenisInformasi jenisInformasi) {
        JenisInformasi createdJenisInformasi = jenisInformasiService.createJenisInformasi(jenisInformasi);
        CustomResponse<JenisInformasi> response = new CustomResponse<>();
        if (createdJenisInformasi != null) {
            response.setStatus("success");
            response.setCode(200);
            response.setData(createdJenisInformasi);
            response.setMessage("Jenis informasi berhasil dibuat");
            return ResponseEntity.ok(response);
        }
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal membuat jenis informasi");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // Endpoint untuk membaca semua jenis informasi
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<JenisInformasi>>> getAllJenisInformasi() {
        List<JenisInformasi> jenisInformasiList = jenisInformasiService.getAllJenisInformasi();
        CustomResponse<List<JenisInformasi>> response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setData(jenisInformasiList);
        response.setMessage("Data jenis informasi");
        return ResponseEntity.ok(response);
    }

    // Endpoint untuk membaca jenis informasi berdasarkan ID
    @GetMapping("/getBy/{id}")
    public ResponseEntity<CustomResponse<JenisInformasi>> getJenisInformasiById(@PathVariable Long id) {
        JenisInformasi jenisInformasi = jenisInformasiService.getJenisInformasiById(id);
        CustomResponse<JenisInformasi> response = new CustomResponse<>();
        if (jenisInformasi != null) {
            response.setStatus("success");
            response.setCode(200);
            response.setData(jenisInformasi);
            response.setMessage("Detail jenis informasi");
            return ResponseEntity.ok(response);
        }
        response.setStatus("error");
        response.setCode(404);
        response.setMessage("Jenis informasi tidak ditemukan");
        return ResponseEntity.notFound().build();
    }

    // Endpoint untuk memperbarui jenis informasi berdasarkan ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<JenisInformasi>> updateJenisInformasi(
            @PathVariable Long id, @RequestBody JenisInformasi jenisInformasi) {
        JenisInformasi updatedJenisInformasi = jenisInformasiService.updateJenisInformasi(id, jenisInformasi);
        CustomResponse<JenisInformasi> response = new CustomResponse<>();
        if (updatedJenisInformasi != null) {
            response.setStatus("success");
            response.setCode(200);
            response.setData(updatedJenisInformasi);
            response.setMessage("Jenis informasi berhasil diperbarui");
            return ResponseEntity.ok(response);
        }
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal memperbarui jenis informasi");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // Endpoint untuk menghapus jenis informasi berdasarkan ID
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteJenisInformasi(@PathVariable Long id) {
        jenisInformasiService.deleteJenisInformasi(id);
        CustomResponse<Void> response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setMessage("Jenis informasi berhasil dihapus");
        return ResponseEntity.ok(response);
    }
}