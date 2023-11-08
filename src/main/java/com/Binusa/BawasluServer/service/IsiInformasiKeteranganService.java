package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class IsiInformasiKeteranganService {

    @Autowired
    private IsiInformasiKeteranganRepository isiInformasiKeteranganRepository;

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.firebaseapp.com/o/%s?alt=media";

    public IsiInformasiKeteranganDTO save(IsiInformasiKeteranganDTO isiInformasiKeteranganDTO, MultipartFile multipartFile) throws Exception {
        IsiInformasiKeterangan isiInformasiKeterangan = new IsiInformasiKeterangan();
        isiInformasiKeterangan.setDokumen(isiInformasiKeteranganDTO.getDokumen());
        isiInformasiKeterangan.setPdfDokumen(uploadPdf(multipartFile));

        IsiInformasiKeterangan savedIsiInformasiKeterangan = isiInformasiKeteranganRepository.save(isiInformasiKeterangan);
        IsiInformasiKeteranganDTO savedDTO = new IsiInformasiKeteranganDTO();
        savedDTO.setId(savedIsiInformasiKeterangan.getId());
        savedDTO.setDokumen(savedIsiInformasiKeterangan.getDokumen());

        return savedDTO;
    }

    public IsiInformasiKeteranganDTO findById(Long id) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        IsiInformasiKeteranganDTO isiInformasiKeteranganDTO = new IsiInformasiKeteranganDTO();
        isiInformasiKeteranganDTO.setId(isiInformasiKeterangan.getId());
        isiInformasiKeteranganDTO.setDokumen(isiInformasiKeterangan.getDokumen());

        return isiInformasiKeteranganDTO;
    }

    public IsiInformasiKeteranganDTO update(Long id, IsiInformasiKeteranganDTO isiInformasiKeteranganDTO, MultipartFile multipartFile) throws Exception {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        isiInformasiKeterangan.setDokumen(isiInformasiKeteranganDTO.getDokumen());
        isiInformasiKeterangan.setPdfDokumen(uploadPdf(multipartFile));

        IsiInformasiKeterangan updatedIsiInformasiKeterangan = isiInformasiKeteranganRepository.save(isiInformasiKeterangan);
        IsiInformasiKeteranganDTO updatedDTO = new IsiInformasiKeteranganDTO();
        updatedDTO.setId(updatedIsiInformasiKeterangan.getId());
        updatedDTO.setDokumen(updatedIsiInformasiKeterangan.getDokumen());

        return updatedDTO;
    }

    public void delete(Long id) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        isiInformasiKeteranganRepository.delete(isiInformasiKeterangan);
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
