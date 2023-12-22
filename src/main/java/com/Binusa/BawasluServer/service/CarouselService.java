package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.CarouselPayloadDTO;
import com.Binusa.BawasluServer.DTO.CarouselResponseDTO;
import com.Binusa.BawasluServer.model.Carousel;
import com.Binusa.BawasluServer.model.Pengumuman;
import com.Binusa.BawasluServer.repository.CarouselRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarouselService {

    @Autowired
    private CarouselRepository carouselRepository;

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.appspot.com/o/%s?alt=media";

    public List<CarouselResponseDTO> getAllCarousels() {
        List<Carousel> carousels = carouselRepository.findAll();
        return carousels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CarouselResponseDTO> getCarouselById(Long id) {
        return carouselRepository.findById(id)
                .map(this::convertToDTO);
    }
    public Optional<Carousel> findById(Long id) {
        return carouselRepository.findById(id);
    }


    public Carousel saveCarousel(CarouselPayloadDTO carouselPayload, MultipartFile multipartFile) throws Exception {
        try {
            Carousel newCarousel = new Carousel();
            String foto = imageConverter(multipartFile);
            newCarousel.setFoto(foto);
            newCarousel.setNamaCarousel(carouselPayload.getNamaCarousel());
            return carouselRepository.save(newCarousel);
        } catch (Exception e) {
            throw new Exception("Gagal menyimpan Carousel: " + e.getMessage());
        }
    }

    public Carousel updateCarousel(Long id, CarouselPayloadDTO carouselPayload, MultipartFile multipartFile) throws Exception {
        Optional<Carousel> optionalCarousel = carouselRepository.findById(id);
        if (optionalCarousel.isPresent()) {
            try {
                Carousel carousel = optionalCarousel.get();
                String foto = imageConverter(multipartFile);
                carousel.setNamaCarousel(carouselPayload.getNamaCarousel());
                carousel.setFoto(foto);
                return carouselRepository.save(carousel);
            } catch (Exception e) {
                throw new Exception("Gagal memperbarui Carousel: " + e.getMessage());
            }
        } else {
            throw new NoSuchElementException("Carousel tidak ditemukan dengan id: " + id);
        }
    }


    public void deleteCarousel(Long id) {
        carouselRepository.deleteById(id);
    }

    public List<CarouselResponseDTO> get7DataTerbaru() {
        List<Carousel> latestCarousels = carouselRepository.findTop7ByOrderByCreatedDateDesc();
        return latestCarousels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    private CarouselResponseDTO convertToDTO(Carousel carousel) {
        CarouselResponseDTO dto = new CarouselResponseDTO();
        dto.setId(carousel.getId());
        dto.setNamaCarousel(carousel.getNamaCarousel());
        dto.setFoto(carousel.getFoto());
        return dto;
    }

    private String imageConverter(MultipartFile multipartFile) throws Exception {
        try {
            String fileName = getExtension(multipartFile.getOriginalFilename());
            File file = convertFile(multipartFile, fileName);
            var RESPONSE_URL = uploadFile(file, fileName);
            file.delete();
            return RESPONSE_URL;
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("Error upload file!");
        }
    }

    private String getExtension(String fileName) {
        return  fileName.split("\\.")[0];
    }

    private File convertFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return file;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("bawaslu-a6bd2.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}

