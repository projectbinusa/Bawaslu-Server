package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.RegulasiDTO;
import com.Binusa.BawasluServer.model.Regulasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.RegulasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/regulasi")
@CrossOrigin(origins = "http://localhost:4040")
public class RegulasiController {
    @Autowired
    private RegulasiService regulasiService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Regulasi>> createRegulasi(RegulasiDTO regulasiDTO, @RequestPart("upload")MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Regulasi> response = new CommonResponse<>();
        try {
            Regulasi regulasi = regulasiService.save(regulasiDTO, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(regulasi);
            response.setMessage("Regulasi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponse<Page<Regulasi>>> listAllRegulasi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException, ClassNotFoundException {
        CommonResponse<Page<Regulasi>> response = new CommonResponse<>();
        try {
            Page<Regulasi> regulasiPage = regulasiService.findAll(PageRequest.of(page, size));
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(regulasiPage);
            response.setMessage("Regulasi list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve regulasi list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Regulasi>> updateRegulasi(@PathVariable("id") Long id, RegulasiDTO regulasiDTO, @RequestPart("upload")MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Regulasi> response = new CommonResponse<>();
        try {
            Optional<Regulasi> currentRegulasi = regulasiService.findById(id);

            if (!currentRegulasi.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Regulasi with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update berita here...

            Regulasi menuRegulasi = regulasiService.update(id, regulasiDTO, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(menuRegulasi);
            response.setMessage("Regulasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deleteRegulasi(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            regulasiService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Regulasi deleted successfully.");
            response.setMessage("Regulasi with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<Regulasi>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Regulasi> response = new CommonResponse<>();
        try {
            Regulasi regulasi = regulasiService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(regulasi);
            response.setMessage("Regulasi get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-by-menu-regulasi", produces = "application/json")
    public ResponseEntity<CommonResponse<Page<Regulasi>>> listRegulasiByMenuRegulasi(
            @RequestParam("id-menu-regulasi") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        CommonResponse<Page<Regulasi>> response = new CommonResponse<>();
        try {
            // Validasi ukuran halaman dan urutan
            if (page < 0 || size <= 0) {
                throw new IllegalArgumentException("Invalid page or size parameters.");
            }

            // Validasi urutan pengurutan
            if (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC")) {
                throw new IllegalArgumentException("Invalid sortOrder parameter. Use 'ASC' or 'DESC'.");
            }

            // Memanggil service dengan paginasi
            Page<Regulasi> regulasis = regulasiService.allByMenuRegulasi(id, page, size, sortBy, sortOrder);

            if (regulasis.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Regulasi list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(regulasis);
            response.setMessage("Regulasi list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve regulasi list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
