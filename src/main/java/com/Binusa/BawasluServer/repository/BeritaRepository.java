package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Berita;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface BeritaRepository extends CrudRepository<Berita, Integer> {
    Berita findById(long id);

}
