package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.RegulasiDTO;
import com.Binusa.BawasluServer.model.Regulasi;
import com.Binusa.BawasluServer.repository.MenuRegulasiRepository;
import com.Binusa.BawasluServer.repository.RegulasiRepository;
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
public class RegulasiService {
    @Autowired
    private RegulasiRepository regulasiRepository;

    @Autowired
    private MenuRegulasiRepository menuRegulasiRepository;

    private long id;

    public RegulasiService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.firebaseapp.com/o/%s?alt=media";

    public Regulasi save(RegulasiDTO regulasiDTO, MultipartFile multipartFile) throws Exception {
        Regulasi regulasi = new Regulasi();
        regulasi.setMenuRegulasi(menuRegulasiRepository.findById(regulasiDTO.getMenuRegulasi()));
        regulasi.setDokumen(regulasiDTO.getDokumen());
        regulasi.setPdfDokumen(uploadPdf(multipartFile));
        return regulasiRepository.save(regulasi);
    }

    public Optional<Regulasi> findById(Long id) {
        return Optional.ofNullable(regulasiRepository.findById(id));
    }

    public List<Regulasi> findAll() {
        List<Regulasi> regulasis = new ArrayList<>();
        regulasiRepository.findAll().forEach(regulasis::add);
        return regulasis;
    }

    public void delete(Long id) {
        Regulasi regulasi = regulasiRepository.findById(id);
        regulasiRepository.delete(regulasi);
    }

    public Regulasi update(Long id, RegulasiDTO regulasiDTO, MultipartFile multipartFile) throws Exception {
        Regulasi regulasi = regulasiRepository.findById(id);
        regulasi.setMenuRegulasi(menuRegulasiRepository.findById(regulasiDTO.getMenuRegulasi()));
        regulasi.setDokumen(regulasiDTO.getDokumen());
        regulasi.setPdfDokumen(uploadPdf(multipartFile));
        return regulasiRepository.save(regulasi);
    }

    public Regulasi getById(Long id) throws Exception{
        Regulasi regulasi = regulasiRepository.findById(id);
        if(regulasi == null) throw new Exception("Menu regulasi not found!");
        return regulasi;
    }

    public List<Regulasi> allByMenuRegulasi(Long id) {
        return regulasiRepository.getByMenuRegulasi(id);
    }

    private String uploadPdf(MultipartFile multipartFile) throws Exception {
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
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./src/main/resources/bawaslu-firebase.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
