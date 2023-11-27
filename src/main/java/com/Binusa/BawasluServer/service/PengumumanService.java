package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.PengumumanDTO;
import com.Binusa.BawasluServer.model.Pengumuman;
import com.Binusa.BawasluServer.repository.PengumumanRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PengumumanService {

    @Autowired
    private PengumumanRepository pengumumanRepository;
    private long id;

    public PengumumanService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.firebaseapp.com/o/%s?alt=media";

    public Pengumuman save(PengumumanDTO pengumuman, MultipartFile multipartFile) throws Exception {

        Pengumuman newPengumuman = new Pengumuman();
        String image = imageConverter(multipartFile);
        newPengumuman.setAuthor(pengumuman.getAuthor());
        newPengumuman.setTags(pengumuman.getTags());
        newPengumuman.setIsiPengumuman(pengumuman.getIsiPengumuman());
        newPengumuman.setJudulPengumuman(pengumuman.getJudulPengumuman());
        newPengumuman.setImage(image);

        return pengumumanRepository.save(newPengumuman);
    }


    public Optional<Pengumuman> findById(Long id) {
        return Optional.ofNullable(pengumumanRepository.findById(id));
    }


    public Page<Pengumuman> findAllWithPagination(Pageable pageable) {
        return pengumumanRepository.findAll(pageable);
    }


    public void delete(Long id) {
        Pengumuman pengumuman = pengumumanRepository.findById(id);
        pengumumanRepository.delete(pengumuman);
    }


    public Pengumuman update(Long id, PengumumanDTO pengumumanDTO, MultipartFile multipartFile) throws Exception {
        Pengumuman pengumuman = pengumumanRepository.findById(id);
        String image = imageConverter(multipartFile);
        pengumuman.setJudulPengumuman(pengumumanDTO.getJudulPengumuman());
        pengumuman.setIsiPengumuman(pengumumanDTO.getIsiPengumuman());
        pengumuman.setTags(pengumumanDTO.getTags());
        pengumuman.setAuthor(pengumumanDTO.getAuthor());
        pengumuman.setImage(image);
        return pengumumanRepository.save(pengumuman);
    }

    public List<Pengumuman> searchPengumuman(String judul) {
        return pengumumanRepository.findByJudulPengumuman(judul);
    }

    public Pengumuman getPengumumanById(Long id) throws Exception {
        Pengumuman pengumuman = pengumumanRepository.findById(id);
        if (pengumuman == null) throw new Exception("Pengumuman not found!!!");
        return pengumuman;
    }

    public List<Pengumuman> relatedPosts(Long idPengumuman) throws Exception {
        String pengumuman = pengumumanRepository.getByIdPengumuman(idPengumuman);
        return pengumumanRepository.relatedPost(pengumuman);
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
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./src/main/resources/bawaslu-firebase.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}