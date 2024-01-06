package com.Binusa.BawasluServer.controller;

import com.Binusa.BawasluServer.DTO.TagsDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.model.Tags;
import com.Binusa.BawasluServer.response.CommonResponse;
import com.Binusa.BawasluServer.service.BeritaService;
import com.Binusa.BawasluServer.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bawaslu/api/tags")
@CrossOrigin(origins = "*")
public class TagsController {
    @Autowired
    private TagsService tagsService;

    @Autowired
    private BeritaService beritaService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CommonResponse<Tags>> createTags(@RequestBody TagsDTO tags) throws SQLException, ClassNotFoundException {
        CommonResponse<Tags> response = new CommonResponse<>();
        try {
            Tags tags1 = tagsService.save(tags);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(tags1);
            response.setMessage("Tags created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create tags : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<List<Tags>>> listAllTags() throws SQLException, ClassNotFoundException {
        CommonResponse<List<Tags>> response = new CommonResponse<>();
        try {
            List<Tags> tags = tagsService.findAll();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tags);
            response.setMessage("Tags list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve tags list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<CommonResponse<Tags>> updateTags(@PathVariable("id") Long id, @RequestBody Tags berita) throws SQLException, ClassNotFoundException {
        CommonResponse<Tags> response = new CommonResponse<>();
        try {
            Optional<Tags> currentTags = tagsService.findById(id);

            if (!currentTags.isPresent()) {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Tags with id " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Update berita here...

            Tags tags = tagsService.update(id, berita);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tags);
            response.setMessage("Tags updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update tags : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<String>> deleteTags(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            tagsService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Tags deleted successfully.");
            response.setMessage("Tags with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete tags : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
