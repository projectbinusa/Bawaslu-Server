package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.BeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.model.Tags;
import com.Binusa.BawasluServer.repository.BeritaRepository;
import com.Binusa.BawasluServer.repository.CategoryBeritaRepository;
import com.Binusa.BawasluServer.repository.TagsRepository;
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
import java.util.Set;


@Service
public class BeritaService {

    @Autowired
    private BeritaRepository beritaDao;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private CategoryBeritaRepository categoryBeritaRepository;

    private long id;

    public BeritaService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.firebaseapp.com/o/%s?alt=media";

    public Berita save(BeritaDTO berita, MultipartFile multipartFile) throws Exception {

        Berita newBerita = new Berita();
        String image = imageConverter(multipartFile);
        newBerita.setAuthor(berita.getAuthor());
        newBerita.setJudulBerita(berita.getJudulBerita());
        newBerita.setIsiBerita(berita.getIsiBerita());
        newBerita.setImage(image);
        newBerita.setCategoryBerita(categoryBeritaRepository.findById(berita.getCategoryId()));

        return beritaDao.save(newBerita);
    }

    public Optional<Berita> findById(Long id) {
        return Optional.ofNullable(beritaDao.findById(id));
    }

    public Page<Berita> findAllWithPagination(Pageable pageable) {
        return beritaDao.findAll(pageable);
    }

    public void delete(Long id) {
        Berita berita = beritaDao.findById(id);
        beritaDao.delete(berita);
    }


    public Berita update(Long id, BeritaDTO beritaDTO, MultipartFile multipartFile) throws Exception {
        Berita berita = beritaDao.findById(id);
        String image = imageConverter(multipartFile);
        berita.setJudulBerita(beritaDTO.getJudulBerita());
        berita.setIsiBerita(beritaDTO.getIsiBerita());
        berita.setAuthor(beritaDTO.getAuthor());
        berita.setImage(image);
        berita.setCategoryBerita(categoryBeritaRepository.findById(beritaDTO.getCategoryId()));
        return beritaDao.save(berita);
    }

    public List<Berita> beritaTerbaru(){
        return beritaDao.findFirst5ByOrderByUpdatedDateDesc();
    }

    public List<Berita> searchBerita(String judul) {
        return beritaDao.searchByJudulBerita(judul);
    }

    public Berita getBeritaById(Long id) throws Exception {
        Berita berita = beritaDao.findById(id);
        if (berita == null) throw new Exception("Berita not found!!!");
        return berita;
    }

    public List<Berita> arsip(String bulan){
        return beritaDao.find(bulan);
    }

    public Berita tagsInBerita(Long beritaId, Long tagsId) {
        Set<Tags> tagsSet = null;
        Berita berita = beritaDao.findById(beritaId);
        Tags tags = tagsRepository.findById(tagsId);
        tagsSet = berita.getTagsBerita();
        tagsSet.add(tags);
        berita.setTagsBerita(tagsSet);
        return beritaDao.save(berita);
    }

    public List<Berita> getByTags(Long tagsId) {
        return beritaDao.getAllByTags(tagsId);
    }

    public List<Berita> getByCategory(Long categoryId) {
        return beritaDao.getAllByCategory(categoryId);
    }

    public List<Berita> relatedPosts(Long idBerita) throws Exception {
        String berita = beritaDao.getByIdBerita(idBerita);
        return beritaDao.relatedPost(berita);
    }

    public List<Berita> terbaruByCategory(Long categoryId) {
        return beritaDao.terbaruByCategory(categoryId);
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