package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.InformasiDTO;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.InformasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bawaslu/api/informasi")
@CrossOrigin(origins = "http://localhost:3000")
public class InformasiController {
    @Autowired
    private InformasiService informasiService;

    @GetMapping("/getInformasi")
    public ResponseEntity<CommonResponse<InformasiDTO>> getInformasi() {
        CommonResponse<InformasiDTO> response = new CommonResponse<>();
        try {
            InformasiDTO informasiDTO = informasiService.getInformasi();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(informasiDTO);
            response.setMessage("Informasi retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve Informasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
