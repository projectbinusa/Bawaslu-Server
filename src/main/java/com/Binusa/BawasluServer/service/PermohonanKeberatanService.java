package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.PermohonanKeberatanDTO;
import com.Binusa.BawasluServer.model.PermohonanKeberatan;
import com.Binusa.BawasluServer.repository.PermohonanKeberatanRepository;
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
public class PermohonanKeberatanService {
    @Autowired
    private PermohonanKeberatanRepository permohonanKeberatanRepository;
    private long id;

    public PermohonanKeberatanService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.firebaseapp.com/o/%s?alt=media";

    public PermohonanKeberatan save(PermohonanKeberatanDTO permohonanKeberatanDTO, MultipartFile multipartFile) throws Exception {
        PermohonanKeberatan permohonanKeberatan = new PermohonanKeberatan();
        String image = imageConverter(multipartFile);
        permohonanKeberatan.setAlamat(permohonanKeberatanDTO.getAlamat());
        permohonanKeberatan.setAlasan(permohonanKeberatanDTO.getAlasan());
        permohonanKeberatan.setEmail(permohonanKeberatanDTO.getEmail());
        permohonanKeberatan.setJenisIdentitas(permohonanKeberatanDTO.getJenisIdentitas());
        permohonanKeberatan.setKasusPosisi(permohonanKeberatanDTO.getKasusPosisi());
        permohonanKeberatan.setNama(permohonanKeberatanDTO.getNama());
        permohonanKeberatan.setNomorIndentitas(permohonanKeberatanDTO.getNomorIndentitas());
        permohonanKeberatan.setNoTelp(permohonanKeberatanDTO.getNoTelp());
        permohonanKeberatan.setTandaPengenal(image);
        permohonanKeberatan.setTujuanPenggunaanInformasi(permohonanKeberatanDTO.getTujuanPenggunaanInformasi());

        return permohonanKeberatanRepository.save(permohonanKeberatan);
    }

    public List<PermohonanKeberatan> findAll() {
        List<PermohonanKeberatan> permohonanKeberatans = new ArrayList<>();
        permohonanKeberatanRepository.findAll().forEach(permohonanKeberatans::add);
        return permohonanKeberatans;
    }

    public Optional<PermohonanKeberatan> findById(Long id) {
        return Optional.ofNullable(permohonanKeberatanRepository.findById(id));
    }

    public void delete(Long id) {
        PermohonanKeberatan permohonanKeberatan = permohonanKeberatanRepository.findById(id);
        permohonanKeberatanRepository.delete(permohonanKeberatan);
    }

     public PermohonanKeberatan update(Long id, PermohonanKeberatanDTO permohonanKeberatanDTO, MultipartFile multipartFile) throws Exception {
        PermohonanKeberatan permohonanKeberatan = permohonanKeberatanRepository.findById(id);
        String image = imageConverter(multipartFile);
         permohonanKeberatan.setAlamat(permohonanKeberatanDTO.getAlamat());
         permohonanKeberatan.setAlasan(permohonanKeberatan.getAlasan());
         permohonanKeberatan.setEmail(permohonanKeberatanDTO.getEmail());
         permohonanKeberatan.setJenisIdentitas(permohonanKeberatanDTO.getJenisIdentitas());
         permohonanKeberatan.setKasusPosisi(permohonanKeberatanDTO.getKasusPosisi());
         permohonanKeberatan.setNama(permohonanKeberatanDTO.getNama());
         permohonanKeberatan.setNomorIndentitas(permohonanKeberatanDTO.getNomorIndentitas());
         permohonanKeberatan.setNoTelp(permohonanKeberatanDTO.getNoTelp());
         permohonanKeberatan.setTandaPengenal(image);
         permohonanKeberatan.setTujuanPenggunaanInformasi(permohonanKeberatanDTO.getTujuanPenggunaanInformasi());
        return permohonanKeberatanRepository.save(permohonanKeberatan);
    }

    public PermohonanKeberatan getPermohonanKeberatanById(Long id) throws Exception {
        PermohonanKeberatan permohonanKeberatan = permohonanKeberatanRepository.findById(id);
        if (permohonanKeberatan == null) throw new Exception("Permohonan keberatan not found!!!");
        return permohonanKeberatan;
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
