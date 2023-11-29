package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.PengumumanDTO;
import com.Binusa.BawasluServer.model.Pengumuman;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.PengumumanService;
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
@RequestMapping("/bawaslu/api/pengumuman")
@CrossOrigin(origins = "https://api-bawaslu.excellentsistem.com")
public class PengumumanController {

    @Autowired
    private PengumumanService pengumumanService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Pengumuman>> createpengumuman(PengumumanDTO pengumuman, @RequestPart("file")MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Pengumuman> response = new CommonResponse<>();
        try {
            Pengumuman pengumuman1 = pengumumanService.save(pengumuman, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(pengumuman1);
            response.setMessage("Pengumuman created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create pengumuman: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<Page<Pengumuman>>> listAllPengumuman(
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

        CommonResponse<Page<Pengumuman>> response = new CommonResponse<>();
        try {
            Page<Pengumuman> pengumumanPage = pengumumanService.findAllWithPagination(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(pengumumanPage);
            response.setMessage("Pengumuman list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve pengumuman list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Pengumuman>> updatePengumuman(@PathVariable("id") Long id, PengumumanDTO pengumuman, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Pengumuman> response = new CommonResponse<>();
        try {
            Optional<Pengumuman> currentPengumuman = pengumumanService.findById(id);

            if (!currentPengumuman.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Pengumuman with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update pengumuman here...

            Pengumuman pengumuman1 = pengumumanService.update(id, pengumuman, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(pengumuman1);
            response.setMessage("Pengumuman updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update pengumuman: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deletepengumuman(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            pengumumanService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Pengumuman deleted successfully.");
            response.setMessage("Pengumuman with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete pengumuman: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Pengumuman>>> searchPengumuman(@RequestParam("search") String judul) {
        CommonResponse<List<Pengumuman>> response = new CommonResponse<>();
        try {
            List<Pengumuman> pengumumans = pengumumanService.searchPengumuman(judul);
            if(pengumumans.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Pengumuman list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(pengumumans);
            response.setMessage("Pengumuman list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve pengumuman list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<Pengumuman>> getById(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Pengumuman> response = new CommonResponse<>();
        try {
            Pengumuman pengumuman = pengumumanService.getPengumumanById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(pengumuman);
            response.setMessage("Berita get successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get pengumuman : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/related-pengumuman/by-id-pengumuman", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Pengumuman>>> relatedPosts(@RequestParam("id") Long id) {
        CommonResponse<List<Pengumuman>> response = new CommonResponse<>();
        try {
            List<Pengumuman> pengumumanList = pengumumanService.relatedPosts(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(pengumumanList);
            response.setMessage("Pengumuman list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve pengumuman list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
