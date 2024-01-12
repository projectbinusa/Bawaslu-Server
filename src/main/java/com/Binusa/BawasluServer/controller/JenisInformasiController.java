package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.JenisInformasiDTO;
import com.Binusa.BawasluServer.DTO.JenisInformasiKeteranganDTO;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.response.CustomResponse;
import com.Binusa.BawasluServer.service.JenisInformasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("bawaslu/api/jenis-informasi")
@CrossOrigin(origins = "*")
public class JenisInformasiController {
    @Autowired
    private JenisInformasiService jenisInformasiService;


    @PostMapping("/add")
    public ResponseEntity<CustomResponse<JenisInformasiDTO>> createJenisInformasi(@RequestBody JenisInformasiDTO jenisInformasiDTO) {
        JenisInformasi createdJenisInformasi = jenisInformasiService.createJenisInformasi(jenisInformasiDTO);
        CustomResponse<JenisInformasiDTO> response = new CustomResponse<>();
        if (createdJenisInformasi != null) {
            response.setStatus("success");
            response.setCode(200);
            response.setData(convertToDTO(createdJenisInformasi));
            response.setMessage("Jenis informasi berhasil dibuat");
            return ResponseEntity.ok(response);
        }
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal membuat jenis informasi");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<JenisInformasiDTO>>> getAllJenisInformasi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<JenisInformasi> jenisInformasiPage = jenisInformasiService.getAllJenisInformasi(pageable);

        CustomResponse<List<JenisInformasiDTO>> response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setData(convertToDTOList(jenisInformasiPage.getContent()));
        response.setMessage("Data jenis informasi");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getBy/{id}")
    public ResponseEntity<CustomResponse<JenisInformasiDTO>> getJenisInformasiById(@PathVariable Long id) {
        JenisInformasi jenisInformasi = jenisInformasiService.getJenisInformasiById(id);
        CustomResponse<JenisInformasiDTO> response = new CustomResponse<>();
        if (jenisInformasi != null) {
            response.setStatus("success");
            response.setCode(200);
            response.setData(convertToDTO(jenisInformasi));
            response.setMessage("Detail jenis informasi");
            return ResponseEntity.ok(response);
        }
        response.setStatus("error");
        response.setCode(404);
        response.setMessage("Jenis informasi tidak ditemukan");
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<JenisInformasiDTO>> updateJenisInformasi(@PathVariable Long id, @RequestBody JenisInformasiDTO jenisInformasiDTO) {
        JenisInformasi updatedJenisInformasi = jenisInformasiService.updateJenisInformasi(id, jenisInformasiDTO);
        CustomResponse<JenisInformasiDTO> response = new CustomResponse<>();
        if (updatedJenisInformasi != null) {
            response.setStatus("success");
            response.setCode(200);
            response.setData(convertToDTO(updatedJenisInformasi));
            response.setMessage("Jenis informasi berhasil diperbarui");
            return ResponseEntity.ok(response);
        }
        response.setStatus("error");
        response.setCode(500);
        response.setMessage("Gagal memperbarui jenis informasi");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteJenisInformasi(@PathVariable Long id) {
        jenisInformasiService.deleteJenisInformasi(id);
        CustomResponse<Void> response = new CustomResponse<>();
        response.setStatus("success");
        response.setCode(200);
        response.setMessage("Jenis informasi berhasil dihapus");
        return ResponseEntity.ok(response);
    }

    private JenisInformasiDTO convertToDTO(JenisInformasi jenisInformasi) {
        JenisInformasiDTO jenisInformasiDTO = new JenisInformasiDTO();
        jenisInformasiDTO.setId(jenisInformasi.getId());
        jenisInformasiDTO.setNamaInformasi(jenisInformasi.getNamaInformasi());
        return jenisInformasiDTO;
    }

    private List<JenisInformasiDTO> convertToDTOList(List<JenisInformasi> jenisInformasiList) {
        List<JenisInformasiDTO> jenisInformasiDTOList = new ArrayList<>();
        for (JenisInformasi jenisInformasi : jenisInformasiList) {
            jenisInformasiDTOList.add(convertToDTO(jenisInformasi));
        }
        return jenisInformasiDTOList;
    }

    @GetMapping("/getByIdWithKeterangan")
    public ResponseEntity<CommonResponse<Page<JenisInformasiKeteranganDTO>>> getJenisInformasiWithKeterangan(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        CommonResponse<Page<JenisInformasiKeteranganDTO>> response = new CommonResponse<>();
        try {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            Page<JenisInformasiKeteranganDTO> jenisInformasiPage = jenisInformasiService.getJenisInformasiWithKeterangan(id, pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenisInformasiPage);
            response.setMessage("Informasi retrieved successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed to retrieve informasi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}