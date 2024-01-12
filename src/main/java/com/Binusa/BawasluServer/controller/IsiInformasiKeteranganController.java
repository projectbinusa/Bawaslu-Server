package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganApiResponseDTO;
import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.model.JenisInformasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.response.CustomResponse;
import com.Binusa.BawasluServer.service.IsiInformasiKeteranganService;
import com.google.protobuf.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
    @RestController
    @RequestMapping("/bawaslu/api/isi-keterangan-informasi")
    @CrossOrigin(origins = "*")
    public class IsiInformasiKeteranganController {
        @Autowired
        private IsiInformasiKeteranganService isiInformasiKeteranganService;


        @PostMapping("/add")
        public ResponseEntity<CommonResponse<IsiInformasiKeteranganApiResponseDTO>> createIsiInformasiKeterangan(
                @RequestBody IsiInformasiKeteranganDTO isiInformasiKeteranganDTO) {
            CommonResponse<IsiInformasiKeteranganApiResponseDTO> response = new CommonResponse<>();
            try {
                // Menyimpan data dengan memanggil service
                IsiInformasiKeteranganApiResponseDTO savedIsiInformasiKeterangan = isiInformasiKeteranganService.save(isiInformasiKeteranganDTO);

                response.setStatus("success");
                response.setCode(HttpStatus.CREATED.value());
                response.setData(savedIsiInformasiKeterangan);
                response.setMessage("Isi Informasi Keterangan created successfully.");

                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (EntityNotFoundException e) {
                // Tangani kesalahan jika entitas tidak ditemukan
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("JenisKeterangan not found: " + e.getMessage());

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                // Tangani kesalahan dari service dan kirim respon kesalahan yang sesuai
                response.setStatus("error");
                response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setData(null);
                response.setMessage("Failed to create Isi Informasi Keterangan: " + e.getMessage());

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<CommonResponse<IsiInformasiKeteranganApiResponseDTO>> updateIsiInformasiKeterangan(
                @PathVariable("id") Long id,
                @RequestBody IsiInformasiKeteranganDTO isiInformasiKeteranganDTO) {
            CommonResponse<IsiInformasiKeteranganApiResponseDTO> response = new CommonResponse<>();
            try {
                // Memperbarui data dengan memanggil service
                IsiInformasiKeteranganApiResponseDTO updatedIsiInformasiKeterangan = isiInformasiKeteranganService.update(id, isiInformasiKeteranganDTO);

                response.setStatus("success");
                response.setCode(HttpStatus.OK.value());
                response.setData(updatedIsiInformasiKeterangan);
                response.setMessage("Isi Informasi Keterangan updated successfully.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                // Tangani kesalahan jika entitas tidak ditemukan
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Isi Informasi Keterangan not found: " + e.getMessage());

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                // Tangani kesalahan dari service dan kirim respon kesalahan yang sesuai
                response.setStatus("error");
                response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setData(null);
                response.setMessage("Failed to update Isi Informasi Keterangan: " + e.getMessage());

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        @GetMapping("/all")
        public ResponseEntity<CustomResponse<Page<IsiInformasiKeteranganApiResponseDTO>>> getAllIsiInformasiKeterangan(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sort,
                @RequestParam(defaultValue = "asc") String direction) {

            CustomResponse<Page<IsiInformasiKeteranganApiResponseDTO>> response = new CustomResponse<>();
            try {
                // Membuat objek Pageable untuk digunakan dalam repository.findAll
                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

                // Menggunakan service.getAllIsiInformasiKeterangan dengan pageable
                Page<IsiInformasiKeteranganApiResponseDTO> isiInformasiKeteranganList = isiInformasiKeteranganService.getAllIsiInformasiKeterangan(pageable);

                response.setStatus("success");
                response.setCode(200);
                response.setData(isiInformasiKeteranganList);
                response.setMessage("Data jenis informasi");
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.setStatus("error");
                response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setData(null);
                response.setMessage("Failed to retrieve jenis informasi data: " + e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/getBy/{id}")
        public ResponseEntity<CommonResponse<IsiInformasiKeteranganApiResponseDTO>> getIsiInformasiKeterangan(@PathVariable("id") Long id) {
            CommonResponse<IsiInformasiKeteranganApiResponseDTO> response = new CommonResponse<>();
            try {
                IsiInformasiKeteranganApiResponseDTO isiInformasiKeterangan = isiInformasiKeteranganService.findById(id);
                response.setStatus("success");
                response.setCode(HttpStatus.OK.value());
                response.setData(isiInformasiKeterangan);
                response.setMessage("Isi Informasi Keterangan retrieved successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.setStatus("error");
                response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setData(null);
                response.setMessage("Failed to retrieve Isi Informasi Keterangan: " + e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<CommonResponse<String>> deleteIsiInformasiKeterangan(@PathVariable("id") Long id) {
            CommonResponse<String> response = new CommonResponse<>();
            try {
                isiInformasiKeteranganService.delete(id);
                response.setStatus("success");
                response.setCode(HttpStatus.NO_CONTENT.value());
                response.setData("Isi Informasi Keterangan deleted successfully.");
                response.setMessage("Isi Informasi Keterangan with id " + id + " deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                response.setStatus("error");
                response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setData(null);
                response.setMessage("Failed to delete Isi Informasi Keterangan: " + e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
