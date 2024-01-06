package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.CarouselPayloadDTO;
import com.Binusa.BawasluServer.DTO.CarouselResponseDTO;
import com.Binusa.BawasluServer.model.Carousel;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/carousel")
@CrossOrigin(origins = "*")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;


    @GetMapping("/all")
    public List<CarouselResponseDTO> getAllCarousels() {
        return carouselService.getAllCarousels();
    }

    @GetMapping("/ById/{id}")
    public ResponseEntity<CarouselResponseDTO> getCarouselById(@PathVariable Long id) {
        Optional<CarouselResponseDTO> carousel = carouselService.getCarouselById(id);
        return carousel.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/terbaru")
    public List<CarouselResponseDTO> get7DataTerbaru() {
        return carouselService.get7DataTerbaru();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Carousel>> createCarousel(@ModelAttribute CarouselPayloadDTO carouselPayload, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Carousel> response = new CommonResponse<>();
        try {
            Carousel carousel1 = carouselService.saveCarousel(carouselPayload, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(carousel1);
            response.setMessage("Carousel berhasil dibuat.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Gagal membuat Carousel: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Carousel>> updateCarousel(@PathVariable("id") Long id, @ModelAttribute CarouselPayloadDTO carouselPayload, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Carousel> response = new CommonResponse<>();
        try {
            // ...
            Carousel carousel = carouselService.updateCarousel(id, carouselPayload, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(carousel);
            response.setMessage("Carousel berhasil diperbarui.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Gagal memperbarui Carousel: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarousel(@PathVariable Long id) {
        if (!carouselService.getCarouselById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carouselService.deleteCarousel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
