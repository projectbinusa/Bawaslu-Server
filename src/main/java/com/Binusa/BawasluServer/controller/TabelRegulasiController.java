package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.TabelRegulasiDTO;
import com.Binusa.BawasluServer.model.TabelRegulasi;
import com.Binusa.BawasluServer.service.TabelRegulasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.Binusa.BawasluServer.response.CommonResponse;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/tabel-regulasi")
//untuk deploy server
//@CrossOrigin(origins = "https://api-bawaslu.excellentsistem.com")

//untuk local
@CrossOrigin(origins = "http://localhost:4040/")

public class TabelRegulasiController {
    @Autowired
    TabelRegulasiService tabelRegulasiService;

//    add
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<TabelRegulasi>> createTabelRegulasi(@RequestBody TabelRegulasiDTO tabelRegulasi) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelRegulasi> response = new CommonResponse<>();
        try {
            TabelRegulasi tabelRegulasi1 = tabelRegulasiService.save(tabelRegulasi);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(tabelRegulasi1);
            response.setMessage("Data regulasi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create data regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    delete
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<CommonResponse<String>> deleteTabelRegulasi(@PathVariable("id") Long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            tabelRegulasiService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Tabel regulasi deleted successfully.");
            response.setMessage("Tabel regulasi with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete tabel regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    put
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<TabelRegulasi>> updateTabelRegulasi(@PathVariable("id") Long id, @RequestBody TabelRegulasiDTO tabelRegulasi ) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelRegulasi> response = new CommonResponse<>();
        try {
            Optional<TabelRegulasi> currentTabelRegulasi = tabelRegulasiService.findById(id);

            if (!currentTabelRegulasi.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Tabel regulasi with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            TabelRegulasi tabelRegulasi1 = tabelRegulasiService.update(id, tabelRegulasi);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelRegulasi1);
            response.setMessage("Tabel regulasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update regulasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get by id
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CommonResponse<TabelRegulasi>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelRegulasi> response = new CommonResponse<>();
        try {
            TabelRegulasi tabelRegulasi = tabelRegulasiService.getTabelRegulasiById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelRegulasi);
            response.setMessage("Tabel regulasi get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get tabel regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all by created date
    @GetMapping("/all")
    public ResponseEntity<CommonResponse<Page<TabelRegulasi>>> allTabelRegulasi(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sort", defaultValue = "createdDate") String sort,
        @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelRegulasi> tabelRegulasis = tabelRegulasiService.findAllWithPagination(pageable);

            CommonResponse<Page<TabelRegulasi>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelRegulasis);
            response.setMessage("Tabel regulasi list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelRegulasi>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel regulasi list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all by daftar regulasi & created date
    @GetMapping("/all-terbaru")
    public ResponseEntity<CommonResponse<Page<TabelRegulasi>>> allTabelRegulasiByDaftarRegulasiCreatedDate(
            @RequestParam("daftarRegulasi") String daftarRegulasi,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "created_date") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelRegulasi> tabelRegulasis = tabelRegulasiService.allByDaftarRegulasiCreatedDate(pageable, daftarRegulasi);

            CommonResponse<Page<TabelRegulasi>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelRegulasis);
            response.setMessage("Tabel regulasi list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelRegulasi>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel regulasi list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all by created date
    @GetMapping("/all-by-daftar-regulasi")
    public ResponseEntity<CommonResponse<Page<TabelRegulasi>>> allTabelRegulasiByDaftarRegulasi(
            @RequestParam("daftarRegulasi") String daftarRegulasi,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelRegulasi> tabelRegulasis = tabelRegulasiService.allByDaftarRegulasi(pageable, daftarRegulasi);

            CommonResponse<Page<TabelRegulasi>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelRegulasis);
            response.setMessage("Tabel regulasi list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelRegulasi>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel regulasi list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
