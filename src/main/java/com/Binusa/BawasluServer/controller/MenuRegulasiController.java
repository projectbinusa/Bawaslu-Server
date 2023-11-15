package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.MenuRegulasiDTO;
import com.Binusa.BawasluServer.model.MenuRegulasi;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.MenuRegulasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/menu-regulasi")
@CrossOrigin(origins = "http://41.216.186.59")
public class MenuRegulasiController {
    @Autowired
    private MenuRegulasiService menuRegulasiService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<MenuRegulasi>> createMenuRegulasi(@RequestBody MenuRegulasiDTO menuRegulasiDTO) throws SQLException, ClassNotFoundException {
        CommonResponse<MenuRegulasi> response = new CommonResponse<>();
        try {
            MenuRegulasi menuRegulasi = menuRegulasiService.save(menuRegulasiDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(menuRegulasi);
            response.setMessage("Menu regulasi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create menu regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<MenuRegulasi>>> listAllMenuRegulasi() throws SQLException, ClassNotFoundException {
        CommonResponse<List<MenuRegulasi>> response = new CommonResponse<>();
        try {
            List<MenuRegulasi> menuRegulasis = menuRegulasiService.allMenuRegulasi();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(menuRegulasis);
            response.setMessage("Menu regulasi list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve menu regulasi list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<CommonResponse<MenuRegulasi>> updateMenuRegulasi(@PathVariable("id") long id, MenuRegulasiDTO menuRegulasiDTO) throws SQLException, ClassNotFoundException {
        CommonResponse<MenuRegulasi> response = new CommonResponse<>();
        try {
            Optional<MenuRegulasi> currentMenuRegulasi = menuRegulasiService.findById(id);

            if (!currentMenuRegulasi.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Menu regulasi with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update berita here...

            MenuRegulasi menuRegulasi = menuRegulasiService.update(id, menuRegulasiDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(menuRegulasi);
            response.setMessage("Menu regulasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update menu regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deleteMenuRegulasi(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            menuRegulasiService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Menu regulasi deleted successfully.");
            response.setMessage("Menu regulasi with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete menu regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<MenuRegulasi>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<MenuRegulasi> response = new CommonResponse<>();
        try {
            MenuRegulasi menuRegulasi = menuRegulasiService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(menuRegulasi);
            response.setMessage("Menu regulasi get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get menu regulasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get-by-jenis-regulasi", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<MenuRegulasi>>> listMenuByJenisRegulasi(@RequestParam("id-jenis-regulasi") Long id) throws SQLException, ClassNotFoundException {
        CommonResponse<List<MenuRegulasi>> response = new CommonResponse<>();
        try {
            List<MenuRegulasi> menuRegulasis = menuRegulasiService.allByJenisRegulasi(id);
            if(menuRegulasis.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Menu regulasi list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(menuRegulasis);
            response.setMessage("Menu regulasi list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve menu regulasi list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
