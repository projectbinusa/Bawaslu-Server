package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.TabelSopDto;
import com.Binusa.BawasluServer.model.TabelSop;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.TabelSopService;
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
@RequestMapping("/bawaslu/api/tabel-sop")
//untuk deploy server
@CrossOrigin(origins = "https://api-bawaslu.excellentsistem.com")

//untuk local
//@CrossOrigin(origins = "http://localhost:4040")
public class TabelSopController {
    @Autowired
    TabelSopService tabelSopService;

//    add
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<TabelSop>> createTabelSop(@RequestBody TabelSopDto tabelSopDto) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelSop> response = new CommonResponse<>();
        try {
            TabelSop tabelSop = tabelSopService.save(tabelSopDto);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(tabelSop);
            response.setMessage("Data sop created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create data sop : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    delete
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<CommonResponse<String>> deleteTabelSop(@PathVariable("id") Long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            tabelSopService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Tabel sop deleted successfully.");
            response.setMessage("Tabel sop with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete tabel sop : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    put
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<TabelSop>> updateTabelSop(@PathVariable("id") Long id, @RequestBody TabelSopDto tabelSopDto ) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelSop> response = new CommonResponse<>();
        try {
            Optional<TabelSop> currentTabelSop = tabelSopService.findById(id);

            if (!currentTabelSop.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Tabel sop with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            TabelSop tabelSop = tabelSopService.update(id, tabelSopDto);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelSop);
            response.setMessage("Tabel sop updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update sop : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    get by id
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CommonResponse<TabelSop>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<TabelSop> response = new CommonResponse<>();
        try {
            TabelSop tabelSop = tabelSopService.getTabelSopById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelSop);
            response.setMessage("Tabel sop get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get tabel sop : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    get all by created date
    @GetMapping("/all")
    public ResponseEntity<CommonResponse<Page<TabelSop>>> allTabelSop(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdDate") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelSop> tabelSops = tabelSopService.findAllWithPagination(pageable);

            CommonResponse<Page<TabelSop>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelSops);
            response.setMessage("Tabel sop list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelSop>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel sop list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    get all by daftar regulasi & created date
    @GetMapping("/all-terbaru")
    public ResponseEntity<CommonResponse<Page<TabelSop>>> allTabelDipByDaftarSopCreatedDate(
            @RequestParam("daftarSop") String daftarSop,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "created_date") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelSop> tabelSops = tabelSopService.allByDaftarSop(pageable, daftarSop);

            CommonResponse<Page<TabelSop>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelSops);
            response.setMessage("Tabel sop list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelSop>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel sop list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    get all by created date
    @GetMapping("/all-by-daftar-sop")
    public ResponseEntity<CommonResponse<Page<TabelSop>>> allTabelDipByDaftarSop(
            @RequestParam("daftarSop") String daftarSop,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
            Page<TabelSop> tabelSops = tabelSopService.allByDaftarSop(pageable, daftarSop);

            CommonResponse<Page<TabelSop>> response = new CommonResponse<>();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelSops);
            response.setMessage("Tabel sop list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            CommonResponse<Page<TabelSop>> response = new CommonResponse<>();
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tabel sop list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
