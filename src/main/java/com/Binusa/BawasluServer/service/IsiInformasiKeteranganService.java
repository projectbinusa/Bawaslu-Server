package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganApiResponseDTO;
import com.Binusa.BawasluServer.DTO.IsiInformasiKeteranganDTO;
import com.Binusa.BawasluServer.model.IsiInformasiKeterangan;
import com.Binusa.BawasluServer.model.JenisKeterangan;
import com.Binusa.BawasluServer.repository.IsiInformasiKeteranganRepository;
import com.Binusa.BawasluServer.repository.JenisKeteranganRepository;
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

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IsiInformasiKeteranganService {
    @Autowired
    private JenisKeteranganRepository jenisKeteranganRepository;
    @Autowired
    private IsiInformasiKeteranganRepository isiInformasiKeteranganRepository;

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.appspot.com/o/%s?alt=media";

    public IsiInformasiKeteranganApiResponseDTO save(IsiInformasiKeteranganDTO isiInformasiKeteranganDTO, MultipartFile multipartFile) throws Exception {
        IsiInformasiKeterangan isiInformasiKeterangan = new IsiInformasiKeterangan();
        isiInformasiKeterangan.setDokumen(isiInformasiKeteranganDTO.getDokumen());

        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(isiInformasiKeteranganDTO.getJenisKeteranganId())
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + isiInformasiKeteranganDTO.getJenisKeteranganId()));

        isiInformasiKeterangan.setJenisKeterangan(jenisKeterangan);

        isiInformasiKeterangan.setPdfDokumen(uploadPdf(multipartFile));

        IsiInformasiKeterangan savedIsiInformasiKeterangan = isiInformasiKeteranganRepository.save(isiInformasiKeterangan);
        return mapIsiInformasiKeteranganToDTO(savedIsiInformasiKeterangan);
    }

    public IsiInformasiKeteranganApiResponseDTO update(Long id, IsiInformasiKeteranganDTO isiInformasiKeteranganDTO, MultipartFile multipartFile) throws Exception {
        IsiInformasiKeterangan existingIsiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));

        existingIsiInformasiKeterangan.setDokumen(isiInformasiKeteranganDTO.getDokumen());

        JenisKeterangan jenisKeterangan = jenisKeteranganRepository.findById(isiInformasiKeteranganDTO.getJenisKeteranganId())
                .orElseThrow(() -> new EntityNotFoundException("JenisKeterangan not found with id: " + isiInformasiKeteranganDTO.getJenisKeteranganId()));

        existingIsiInformasiKeterangan.setJenisKeterangan(jenisKeterangan);

        existingIsiInformasiKeterangan.setPdfDokumen(uploadPdf(multipartFile));

        IsiInformasiKeterangan updatedIsiInformasiKeterangan = isiInformasiKeteranganRepository.save(existingIsiInformasiKeterangan);
        return convertToApiResponseDTO(updatedIsiInformasiKeterangan);
    }

    public IsiInformasiKeteranganApiResponseDTO findById(Long id) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));

        return convertToApiResponseDTO(isiInformasiKeterangan);
    }

    public Page<IsiInformasiKeteranganApiResponseDTO> getAllIsiInformasiKeterangan(Pageable pageable) {
        // Menggunakan repository.findAll dengan pageable
        Page<IsiInformasiKeterangan> isiInformasiKeteranganPage = isiInformasiKeteranganRepository.findAll(pageable);

        // Mengubah halaman IsiInformasiKeterangan menjadi halaman DTO
        return isiInformasiKeteranganPage.map(this::convertToApiResponseDTO);
    }

    public void delete(Long id) {
        IsiInformasiKeterangan isiInformasiKeterangan = isiInformasiKeteranganRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IsiInformasiKeterangan not found with id: " + id));
        isiInformasiKeteranganRepository.delete(isiInformasiKeterangan);
    }

    private IsiInformasiKeteranganApiResponseDTO convertToApiResponseDTO(IsiInformasiKeterangan isiInformasiKeterangan) {
        IsiInformasiKeteranganApiResponseDTO isiInformasiKeteranganDTO = new IsiInformasiKeteranganApiResponseDTO();
        isiInformasiKeteranganDTO.setId(isiInformasiKeterangan.getId());
        isiInformasiKeteranganDTO.setDokumen(isiInformasiKeterangan.getDokumen());
        isiInformasiKeteranganDTO.setPdfDokumen(isiInformasiKeterangan.getPdfDokumen());
        isiInformasiKeteranganDTO.setJenisKeterangan(isiInformasiKeterangan.getJenisKeterangan().getId());
        return isiInformasiKeteranganDTO;
    }

    private IsiInformasiKeteranganApiResponseDTO mapIsiInformasiKeteranganToDTO(IsiInformasiKeterangan isiInformasiKeterangan) {
        IsiInformasiKeteranganApiResponseDTO dto = new IsiInformasiKeteranganApiResponseDTO();
        dto.setId(isiInformasiKeterangan.getId());
        dto.setDokumen(isiInformasiKeterangan.getDokumen());
        dto.setPdfDokumen(isiInformasiKeterangan.getPdfDokumen());
        dto.setJenisKeterangan(isiInformasiKeterangan.getJenisKeterangan().getId());

        return dto;
    }

    private String uploadPdf(MultipartFile multipartFile) throws Exception {
        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileExtension = getFileExtension(fileName);

            if (!isImageFile(fileExtension)) {
                throw new IllegalArgumentException("Jenis file tidak diizinkan");
            }

            File file = convertFile(multipartFile, fileName);
            var RESPONSE_URL = uploadFile(file, fileName);
            file.delete();
            return RESPONSE_URL;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error upload file!");
        }
    }

    private String getFileExtension(String fileName) {
        String[] splitFileName = fileName.split("\\.");
        return splitFileName[splitFileName.length - 1].toLowerCase();
    }

    private boolean isImageFile(String fileExtension) {
        return fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("jpeg");
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
