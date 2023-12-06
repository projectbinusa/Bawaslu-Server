package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.DTO.CategoryBeritaDTO;
import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.model.CategoryBerita;
import com.Binusa.BawasluServer.repository.BeritaRepository;
import com.Binusa.BawasluServer.repository.CategoryBeritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryBeritaService {
    @Autowired
    private CategoryBeritaRepository categoryBeritaRepository;
    @Autowired
    private BeritaRepository beritaRepository;

    private long id;

    public CategoryBeritaService() {
    }

    public CategoryBerita save(CategoryBeritaDTO categoryBerita) {
        CategoryBerita categoryBerita1 = new CategoryBerita();
        categoryBerita1.setCategory(categoryBerita.getCategory());
        return categoryBeritaRepository.save(categoryBerita1);
    }

    public Optional<CategoryBerita> findById(Long id) {
        return Optional.ofNullable(categoryBeritaRepository.findById(id));
    }

    public Page<CategoryBerita> findAll(Pageable pageable) {
        return categoryBeritaRepository.findAllByOrderByUpdatedDateDesc(pageable);
    }
    public List<CategoryBerita> findAllByLimit7() {
        List<CategoryBerita> category = new ArrayList<>();
        categoryBeritaRepository.limit7().forEach(category::add);
        return category;
    }

    @Transactional
    public void delete(Long id) {
        CategoryBerita categoryBerita = categoryBeritaRepository.getById(id);
        if (categoryBerita != null) {
            List<Berita> relatedBeritas = beritaRepository.getAllByCategory(categoryBerita.getId());

            for (Berita berita : relatedBeritas) {
                beritaRepository.delete(berita);
            }

            categoryBeritaRepository.delete(categoryBerita);
        }
        categoryBeritaRepository.delete(categoryBerita);
    }

    public CategoryBerita update(Long id, CategoryBeritaDTO categoryBerita) {
        CategoryBerita categoryBerita1 = categoryBeritaRepository.findById(id);
        categoryBerita1.setCategory(categoryBerita.getCategory());
        return categoryBeritaRepository.save(categoryBerita1);
    }

    public CategoryBerita getById(Long id) throws Exception{
        CategoryBerita categoryBerita = categoryBeritaRepository.findById(id);
        if (categoryBerita == null) throw new Exception("Category berita not found!!!");
        return categoryBerita;

    }

}
