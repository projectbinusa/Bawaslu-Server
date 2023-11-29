package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.RegulasiDTO;
import com.Binusa.BawasluServer.model.Regulasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.RegulasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/regulasi")
@CrossOrigin(origins = "https://api-bawaslu.excellentsistem.com")
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

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Regulasi>>> listAllRegulasi() throws SQLException, ClassNotFoundException {
        CommonResponse<List<Regulasi>> response = new CommonResponse<>();
        try {
            List<Regulasi> regulasi = regulasiService.findAll();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(regulasi);
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

    @RequestMapping(value = "/get-by-menu-regulasi", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Regulasi>>> listRegulasiByMenuRegulasi(@RequestParam("id-menu-regulasi") Long id) throws SQLException, ClassNotFoundException {
        CommonResponse<List<Regulasi>> response = new CommonResponse<>();
        try {
            List<Regulasi> regulasis = regulasiService.allByMenuRegulasi(id);
            if(regulasis.isEmpty()) {
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
