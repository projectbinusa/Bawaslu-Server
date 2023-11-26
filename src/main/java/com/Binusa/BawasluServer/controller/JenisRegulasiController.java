package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.JenisRegulasiDTO;
import com.Binusa.BawasluServer.model.JenisRegulasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.JenisRegulasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/jenis-regulasi")
@CrossOrigin(origins = "http://localhost:3000")
public class JenisRegulasiController {
    @Autowired
    private JenisRegulasiService jenisRegulasiService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<JenisRegulasi>> addJenisRegulasi(@RequestBody JenisRegulasiDTO jenisRegulasi) {
        CommonResponse<JenisRegulasi> response = new CommonResponse<>();
        try {
            JenisRegulasi jenisRegulasi1 = jenisRegulasiService.save(jenisRegulasi);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(jenisRegulasi1);
            response.setMessage("Jenis regulasi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create jenis regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<JenisRegulasi>>> listAllJenisRegulasi() throws SQLException, ClassNotFoundException {
        CommonResponse<List<JenisRegulasi>> response = new CommonResponse<>();
        try {
            List<JenisRegulasi> jenisRegulasis = jenisRegulasiService.allJenisRegulasi();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenisRegulasis);
            response.setMessage("Jenis regulasi list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve jenis regulasi list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse<JenisRegulasi>> updateJenisRegulasi(@PathVariable("id") Long id, JenisRegulasiDTO jenisRegulasi) throws SQLException, ClassNotFoundException {
        CommonResponse<JenisRegulasi> response = new CommonResponse<>();
        try {
            Optional<JenisRegulasi> currentJenisRegulasi = jenisRegulasiService.findById(id);
            if (!currentJenisRegulasi.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Jenis regulasi with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            JenisRegulasi jenisRegulasi1 = jenisRegulasiService.update(id, jenisRegulasi);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenisRegulasi1);
            response.setMessage("Jenis regulasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update jenis regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deleteJenisRegulasi(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            jenisRegulasiService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Jenis regulasi deleted successfully.");
            response.setMessage("Jenis regulasi with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete jenis regulasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<JenisRegulasi>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<JenisRegulasi> response = new CommonResponse<>();
        try {
            JenisRegulasi jenisRegulasi = jenisRegulasiService.getJenisRegulasiById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenisRegulasi);
            response.setMessage("Jenis regulasi get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get jenis regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
