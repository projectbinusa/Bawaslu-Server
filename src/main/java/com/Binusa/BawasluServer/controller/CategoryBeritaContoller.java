package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.CategoryBeritaDTO;
import com.Binusa.BawasluServer.model.CategoryBerita;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.CategoryBeritaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/category-berita")
@CrossOrigin(origins = "*")
public class CategoryBeritaContoller {
    @Autowired
    private CategoryBeritaService categoryBeritaService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<CategoryBerita>> createCategoryBerita(@RequestBody CategoryBeritaDTO categoryBerita) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryBerita> response = new CommonResponse<>();
        try {
            CategoryBerita berita1 = categoryBeritaService.save(categoryBerita);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(berita1);
            response.setMessage("Category berita created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create category berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<Page<CategoryBerita>>> listAlCategoryBerita(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sort,
            @RequestParam(defaultValue = "asc") String direction) throws SQLException, ClassNotFoundException {
        CommonResponse<Page<CategoryBerita>> response = new CommonResponse<>();
        try {
            // Membuat objek Pageable untuk digunakan dalam repository.findAll
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

            // Menggunakan service.findAll dengan pageable
            Page<CategoryBerita> categoryBeritas = categoryBeritaService.findAll(pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBeritas);
            response.setMessage("Category berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve category berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/all-limit-7", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<CategoryBerita>>> listAlCategoryBeritaLimit7() throws SQLException, ClassNotFoundException {
        CommonResponse<List<CategoryBerita>> response = new CommonResponse<>();
        try {
            List<CategoryBerita> categoryBeritas = categoryBeritaService.findAllByLimit7();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBeritas);
            response.setMessage("Category berita list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve category berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<CommonResponse<CategoryBerita>> updateCategoryBerita(@PathVariable("id") Long id, @RequestBody CategoryBeritaDTO categoryBerita) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryBerita> response = new CommonResponse<>();
        try {
            Optional<CategoryBerita> currentCategoryBerita = categoryBeritaService.findById(id);

            if (!currentCategoryBerita.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Category berita with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update berita here...

            CategoryBerita categoryBerita1 = categoryBeritaService.update(id, categoryBerita);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita1);
            response.setMessage("Category berita updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update category berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deletecategoryberita(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            categoryBeritaService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Category berita deleted successfully.");
            response.setMessage("Category berita with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete category berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<CategoryBerita>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryBerita> response = new CommonResponse<>();
        try {
            CategoryBerita categoryBerita = categoryBeritaService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Category berita get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get category berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
