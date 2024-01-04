package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.TabelDipDTO;
import com.Binusa.BawasluServer.model.TabelDip;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.TabelDipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/tabel-dip")
//untuk deploy server
//@CrossOrigin(origins = "https://api-bawaslu.excellentsistem.com")

//untuk local
@CrossOrigin(origins = "http://localhost:4040/")
public class TabelDipController {
    @Autowired
    TabelDipService tabelDipService;

    //    add
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<TabelDip>> createTabelDip(@RequestBody TabelDipDTO tabelDipDTO) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelDip> response = new CommonResponse<>();
        try {
            TabelDip tabelDip = tabelDipService.save(tabelDipDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(tabelDip);
            response.setMessage("Data dip created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create data dip : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    delete
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<CommonResponse<String>> deleteTabelDip(@PathVariable("id") Long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            tabelDipService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Tabel dip deleted successfully.");
            response.setMessage("Tabel dip with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete tabel dip : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    put
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<TabelDip>> updateTabelDip(@PathVariable("id") Long id, @RequestBody TabelDipDTO tabelDipDTO ) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelDip> response = new CommonResponse<>();
        try {
            Optional<TabelDip> currentTabelDip = tabelDipService.findById(id);

            if (!currentTabelDip.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Tabel dip with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            TabelDip tabelDip = tabelDipService.update(id, tabelDipDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Tabel dip updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update dip : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get by id
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CommonResponse<TabelDip>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelDip> response = new CommonResponse<>();
        try {
            TabelDip tabelDip = tabelDipService.getTabelDipById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Tabel dip get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get tabel dip : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all by created date
    @GetMapping("/all")
    public ResponseEntity<CommonResponse<Page<TabelDip>>> allTabelRegulasi(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sort", defaultValue = "createdDate") String sort,
        @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelDip> tabelDips = tabelDipService.findAllWithPagination(pageable);

            CommonResponse<Page<TabelDip>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDips);
            response.setMessage("Tabel dip list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelDip>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel dip list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all by daftar regulasi & created date
    @GetMapping("/all-terbaru")
    public ResponseEntity<CommonResponse<Page<TabelDip>>> allTabelDipByDaftarDipCreatedDate(
            @RequestParam("daftarDip") String daftarDip,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "created_date") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelDip> tabelRegulasis = tabelDipService.allByDaftarDipCreatedDate(pageable, daftarDip);

            CommonResponse<Page<TabelDip>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelRegulasis);
            response.setMessage("Tabel dip list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelDip>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel dip list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all by created date
    @GetMapping("/all-by-daftar-dip")
    public ResponseEntity<CommonResponse<Page<TabelDip>>> allTabelDipByDaftarDip(
            @RequestParam("daftarDip") String daftarDip,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelDip> tabelDips = tabelDipService.allByDaftarDip(pageable, daftarDip);

            CommonResponse<Page<TabelDip>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDips);
            response.setMessage("Tabel dip list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelDip>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel dip list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
