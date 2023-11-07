package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.model.Tags;
import com.Binusa.BawasluServer.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagsService {
    @Autowired
    private TagsRepository tagsRepository;
    private long id;

    public TagsService() {
    }

    public Tags save(Tags tagss) {
        Tags tags = new Tags();
        tags.setTags(tags.getTags());
        return tagsRepository.save(tags);
    }

    public Optional<Tags> findById(Long id) {
        return Optional.ofNullable(tagsRepository.findById(id));
    }

    public List<Tags> findAll() {
        List<Tags> berita = new ArrayList<>();
        tagsRepository.findAll().forEach(berita::add);
        return berita;
    }

    public void delete(Long id) {
        Tags tags = tagsRepository.findById(id);
        tagsRepository.delete(tags);
    }

    public Tags update(Long id, Tags tags) throws Exception {
        Tags tags1 = tagsRepository.findById(id);
        return tagsRepository.save(tags);
    }
}
