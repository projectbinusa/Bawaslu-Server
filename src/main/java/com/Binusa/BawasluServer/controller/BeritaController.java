package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.BeritaService;
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
@RequestMapping("/bawaslu/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BeritaController {

    @Autowired
    private BeritaService beritaService;

    @RequestMapping(value = "/berita/add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Berita>> createberita(BeritaDTO berita, @RequestPart("file")MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Berita> response = new CommonResponse<>();
        try {
            Berita berita1 = beritaService.save(berita, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(berita1);
            response.setMessage("Berita created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<Page<Berita>>> listAllBerita(
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

        CommonResponse<Page<Berita>> response = new CommonResponse<>();
        try {
            Page<Berita> beritaPage = beritaService.findAllWithPagination(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/berita/put/{id}", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Berita>> updateBerita(@PathVariable("id") Long id, BeritaDTO berita, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Berita> response = new CommonResponse<>();
        try {
            Optional<Berita> currentBerita = beritaService.findById(id);

            if (!currentBerita.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Berita with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update berita here...

            Berita berita1 = beritaService.update(id, berita, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita1);
            response.setMessage("Berita updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deleteberita(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            beritaService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Berita deleted successfully.");
            response.setMessage("Berita with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita-terbaru", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> listBeritaTerbaru() throws SQLException, ClassNotFoundException {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> berita = beritaService.beritaTerbaru();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> searchBerita(@RequestParam("search") String judul) {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> beritas = beritaService.searchBerita(judul);
            if(beritas.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Berita list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritas);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/arsip", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> listBeritaArsip(@RequestParam("bulan") String bulan) throws SQLException, ClassNotFoundException {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> berita = beritaService.arsip(bulan);
            if(berita.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Berita list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<Berita>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Berita> response = new CommonResponse<>();
        try {
            Berita berita1 = beritaService.getBeritaById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita1);
            response.setMessage("Berita get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/berita/{tags}/in/{berita}")
    public Berita addTags(
            @PathVariable Long tags,
            @PathVariable Long berita
    ) {
        return beritaService.tagsInBerita(berita, tags);
    }

    @RequestMapping(value = "/berita/by-tags", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> searchTags(@RequestParam("tags") Long tagsId) {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> beritas = beritaService.getByTags(tagsId);
            if(beritas.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Berita list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritas);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/by-category", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> allByCategory(@RequestParam("categoryId") Long categoryId) {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> beritas = beritaService.getByCategory(categoryId);
            if(beritas.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Berita list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritas);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/berita/by-id", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Berita>>> relatedPosts(@RequestParam("id") Long id) {
        CommonResponse<List<Berita>> response = new CommonResponse<>();
        try {
            List<Berita> beritas = beritaService.relatedPosts(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritas);
            response.setMessage("Berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
