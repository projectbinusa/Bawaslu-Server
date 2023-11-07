package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.repository.BeritaRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BeritaService {

    @Autowired
    private BeritaRepository beritaDao;
    private long id;

    public BeritaService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.firebaseapp.com/o/%s?alt=media";

    public Berita save(BeritaDTO berita, MultipartFile multipartFile) throws Exception {

        Berita newBerita = new Berita();
        String image = imageConverter(multipartFile);
        newBerita.setAuthor(berita.getAuthor());
        newBerita.setTags(berita.getTags());
        newBerita.setJudulBerita(berita.getJudulBerita());
        newBerita.setIsiBerita(berita.getIsiBerita());
        newBerita.setImage(image);

        return beritaDao.save(newBerita);
    }


    public Optional<Berita> findById(Long id) {
        return Optional.ofNullable(beritaDao.findById(id));
    }

    public List<Berita> findAll() {
        List<Berita> berita = new ArrayList<>();
        beritaDao.findAll().forEach(berita::add);
        return berita;
    }


    public void delete(Long id) {
        Berita berita = beritaDao.findById(id);
        beritaDao.delete(berita);
    }


    public Berita update(Long id, BeritaDTO beritaDTO, MultipartFile multipartFile) throws Exception {
        Berita berita = beritaDao.findById(id);
        String image = imageConverter(multipartFile);
        berita.setJudulBerita(berita.getJudulBerita());
        berita.setIsiBerita(beritaDTO.getIsiBerita());
        berita.setAuthor(beritaDTO.getAuthor());
        berita.setTags(beritaDTO.getTags());
        berita.setImage(image);
        return beritaDao.save(berita);
    }

    public List<Berita> beritaTerbaru(){
        return beritaDao.findFirst5ByOrderByUpdatedDateDesc();
    }

    public List<Berita> searchBerita(String judul) {
        return beritaDao.findByJudulBerita(judul);
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