package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Carousel;
import com.Binusa.BawasluServer.model.Pengumuman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
    List<Carousel> findTop7ByOrderByCreatedDateDesc();
    Carousel findById(long id);
}
