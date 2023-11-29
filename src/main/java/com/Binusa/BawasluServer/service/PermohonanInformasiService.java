package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.PermohonanInformasiDTO;
import com.Binusa.BawasluServer.model.PermohonanInformasi;
import com.Binusa.BawasluServer.repository.PermohonanInformasiRepository;
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
public class PermohonanInformasiService {
    @Autowired
    private PermohonanInformasiRepository permohonanInformasiRepository;

    private long id;

    public PermohonanInformasiService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.appspot.com/o/%s?alt=media";

    public PermohonanInformasi save(PermohonanInformasiDTO permohonanInformasiDTO, MultipartFile multipartFile) throws Exception {
        PermohonanInformasi permohonanInformasi = new PermohonanInformasi();
        String image = imageConverter(multipartFile);
        permohonanInformasi.setEmail(permohonanInformasiDTO.getEmail());
        permohonanInformasi.setNama(permohonanInformasiDTO.getNama());
        permohonanInformasi.setNoHp(permohonanInformasiDTO.getNoHp());
        permohonanInformasi.setPekerjaan(permohonanInformasiDTO.getPekerjaan());
        permohonanInformasi.setPendidikan(permohonanInformasiDTO.getPendidikan());
        permohonanInformasi.setRincianInformasi(permohonanInformasiDTO.getRincianInformasi());
        permohonanInformasi.setTujuanInformasi(permohonanInformasiDTO.getTujuanInformasi());
        permohonanInformasi.setAlamat(permohonanInformasiDTO.getAlamat());
        permohonanInformasi.setCaraMemperolehInformasi(permohonanInformasiDTO.getCaraMemperolehInformasi());
        permohonanInformasi.setCaraMendapatInformasi(permohonanInformasiDTO.getCaraMendapatInformasi());
        permohonanInformasi.setDitujukanKepada(permohonanInformasiDTO.getDitujukanKepada());
        permohonanInformasi.setTandaPengenal(image);

        return permohonanInformasiRepository.save(permohonanInformasi);
    }

    public List<PermohonanInformasi> findAll() {
        List<PermohonanInformasi> permohonanInformasis = new ArrayList<>();
        permohonanInformasiRepository.findAll().forEach(permohonanInformasis::add);
        return permohonanInformasis;
    }

    public Optional<PermohonanInformasi> findById(Long id) {
        return Optional.ofNullable(permohonanInformasiRepository.findById(id));
    }

    public void delete(Long id) {
        PermohonanInformasi permohonanInformasi = permohonanInformasiRepository.findById(id);
        permohonanInformasiRepository.delete(permohonanInformasi);
    }

    public PermohonanInformasi update(Long id, PermohonanInformasiDTO permohonanInformasiDTO, MultipartFile multipartFile) throws Exception {
        PermohonanInformasi permohonanInformasi = permohonanInformasiRepository.findById(id);
        String image = imageConverter(multipartFile);
        permohonanInformasi.setEmail(permohonanInformasiDTO.getEmail());
        permohonanInformasi.setNama(permohonanInformasiDTO.getNama());
        permohonanInformasi.setNoHp(permohonanInformasiDTO.getNoHp());
        permohonanInformasi.setPekerjaan(permohonanInformasiDTO.getPekerjaan());
        permohonanInformasi.setPendidikan(permohonanInformasiDTO.getPendidikan());
        permohonanInformasi.setRincianInformasi(permohonanInformasiDTO.getRincianInformasi());
        permohonanInformasi.setTujuanInformasi(permohonanInformasiDTO.getTujuanInformasi());
        permohonanInformasi.setAlamat(permohonanInformasiDTO.getAlamat());
        permohonanInformasi.setCaraMemperolehInformasi(permohonanInformasiDTO.getCaraMemperolehInformasi());
        permohonanInformasi.setCaraMendapatInformasi(permohonanInformasiDTO.getCaraMendapatInformasi());
        permohonanInformasi.setDitujukanKepada(permohonanInformasiDTO.getDitujukanKepada());
        permohonanInformasi.setTandaPengenal(image);
        return permohonanInformasiRepository.save(permohonanInformasi);
    }

    public PermohonanInformasi getPermohonanKeberatanById(Long id) throws Exception {
        PermohonanInformasi permohonanInformasi = permohonanInformasiRepository.findById(id);
        if (permohonanInformasi == null) throw new Exception("Berita not found!!!");
        return permohonanInformasi;
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
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("../resources/bawaslu-firebase.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
