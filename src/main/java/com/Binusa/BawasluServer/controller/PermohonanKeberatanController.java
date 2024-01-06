package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.PermohonanKeberatanDTO;
import com.Binusa.BawasluServer.model.PermohonanKeberatan;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.PermohonanKeberatanService;
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
//untuk deploy server
//@CrossOrigin(origins = "https://api-bawaslu.excellentsistem.com")

//untuk local
//@CrossOrigin(origins = "http://localhost:4040/")
public class PermohonanKeberatanController {
    @Autowired
    private PermohonanKeberatanService permohonanKeberatanService;

    @RequestMapping(value = "/permohonan-keberatan/add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<PermohonanKeberatan>> createPermohonanKeberatan(PermohonanKeberatanDTO permohonanKeberatanDTO, @RequestPart("fotoIdentitas") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<PermohonanKeberatan> response = new CommonResponse<>();
        try {
            PermohonanKeberatan permohonanKeberatan = permohonanKeberatanService.save(permohonanKeberatanDTO, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(permohonanKeberatan);
            response.setMessage("Permohonan keberatan created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create permohonan keberatan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/permohonan-keberatan", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<PermohonanKeberatan>>> listAllPermohonanKeberatan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) throws SQLException, ClassNotFoundException {
        CommonResponse<List<PermohonanKeberatan>> response = new CommonResponse<>();
        try {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            Page<PermohonanKeberatan> keberatanPage = permohonanKeberatanService.findAll(pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(keberatanPage.getContent());
            response.setMessage("Permohonan keberatan list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve permohonan keberatan list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/permohonan-keberatan/{id}", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<PermohonanKeberatan>> updatePermohonanKeberatan(@PathVariable("id") Long id, PermohonanKeberatanDTO permohonanKeberatanDTO, @RequestPart("fotoIdentitas") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<PermohonanKeberatan> response = new CommonResponse<>();
        try {
            Optional<PermohonanKeberatan> currentPermohonanKeberatan = permohonanKeberatanService.findById(id);

            if (!currentPermohonanKeberatan.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Permohonan keberatan with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

//            // Update berita here...

            PermohonanKeberatan permohonanKeberatan = permohonanKeberatanService.update(id, permohonanKeberatanDTO, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(permohonanKeberatan);
            response.setMessage("Permohonan keberatan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update permohonan keberatan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/permohonan-keberatan/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deletePermohonanKeberatan(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            permohonanKeberatanService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Permohonan keberatan deleted successfully.");
            response.setMessage("Permohonan keberatan with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete permohonan keberatan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/permohonan-keberatan/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<PermohonanKeberatan>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<PermohonanKeberatan> response = new CommonResponse<>();
        try {
            PermohonanKeberatan permohonanKeberatan = permohonanKeberatanService.getPermohonanKeberatanById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(permohonanKeberatan);
            response.setMessage("Permohonan keberatan get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get permohonan keberatan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
