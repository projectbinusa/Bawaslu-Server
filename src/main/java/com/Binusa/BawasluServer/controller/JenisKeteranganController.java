package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganApiResponseDTO;
import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganByJenisKeteranganApiResponseDTO;
import com.Binusa.BawasluServer.DTO.JenisKeteranganDTO;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.response.CustomResponse;
import com.Binusa.BawasluServer.service.IsiInformasiKeteranganService;
import com.Binusa.BawasluServer.service.JenisKeteranganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bawaslu/api/jenis-keterangan")
@CrossOrigin(origins = "*")
public class JenisKeteranganController {
    @Autowired
    private JenisKeteranganService jenisKeteranganService;


    @PostMapping("/add")
    public ResponseEntity<CustomResponse<JenisKeteranganDTO>> createJenisKeterangan(
            @RequestBody JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeteranganDTO createdJenisKeterangan = jenisKeteranganService.createJenisKeterangan(jenisKeteranganDTO);
        if (createdJenisKeterangan != null) {
            CustomResponse<JenisKeteranganDTO> response = new CustomResponse<>();
            response.setStatus("success");
            response.setCode(200);
            response.setData(createdJenisKeterangan);
            response.setMessage("Jenis keterangan berhasil dibuat");
            return ResponseEntity.ok(response);
        }
        CustomResponse<JenisKeteranganDTO> response = new CustomResponse<>();
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal membuat jenis keterangan");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // Endpoint untuk membaca semua jenis keterangan
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<JenisKeteranganDTO>>>getAllJenisKeterangan() {
        List<JenisKeteranganDTO> jenisKeteranganList = jenisKeteranganService.getAllJenisKeterangan();
        CustomResponse<List<JenisKeteranganDTO>>response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setData(jenisKeteranganList);
        response.setMessage("Data jenis keterangan");
        return ResponseEntity.ok(response);
    }

    // Endpoint untuk membaca jenis keterangan berdasarkan ID
    @GetMapping("/getBy/{id}")
    public ResponseEntity<CustomResponse<JenisKeteranganDTO>> getJenisKeteranganById(@PathVariable Long id) {
        JenisKeteranganDTO jenisKeterangan = jenisKeteranganService.getJenisKeteranganById(id);
        if (jenisKeterangan != null) {
            CustomResponse<JenisKeteranganDTO> response = new CustomResponse<>();
            response.setStatus("success");
            response.setCode(200);
            response.setData(jenisKeterangan);
            response.setMessage("Detail jenis keterangan");
            return ResponseEntity.ok(response);
        }
        CustomResponse<JenisKeteranganDTO> response = new CustomResponse<>();
        response.setStatus("error");
        response.setCode(404);
        response.setMessage("Jenis keterangan tidak ditemukan");
        return ResponseEntity.notFound().build();
    }

    // Endpoint untuk memperbarui jenis keterangan berdasarkan ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<JenisKeteranganDTO>> updateJenisKeterangan(
            @PathVariable Long id, @RequestBody JenisKeteranganDTO jenisKeteranganDTO) {
        JenisKeteranganDTO updatedJenisKeterangan = jenisKeteranganService.updateJenisKeterangan(id, jenisKeteranganDTO);
        if (updatedJenisKeterangan != null) {
            CustomResponse<JenisKeteranganDTO> response = new CustomResponse<>();
            response.setStatus("success");
            response.setCode(200);
            response.setData(updatedJenisKeterangan);
            response.setMessage("Jenis keterangan berhasil diperbarui");
            return ResponseEntity.ok(response);
        }
        CustomResponse<JenisKeteranganDTO> response = new CustomResponse<>();
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
    @GetMapping("/{jenisKeterangan}/isi-informasi")
    public ResponseEntity<CommonResponse<Page<IsiInformasiKeteranganApiResponseDTO>>> getIsiInformasiByCategory(
            @PathVariable("jenisKeterangan") Long jenisKeterangan,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Pageable pageable;
        if (sortOrder.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganPage = jenisKeteranganService.getByCategoryWithPagination(jenisKeterangan, pageable);

        CommonResponse<Page<IsiInformasiKeteranganApiResponseDTO>> response = new CommonResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setData(isiInformasiKeteranganPage);
        response.setMessage("Informasi retrieved successfully.");
        return ResponseEntity.ok(response);
    }

}

