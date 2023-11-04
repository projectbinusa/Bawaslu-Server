package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Berita;
import com.Binusa.BawasluServer.model.Pengumuman;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PengumumanRepository extends CrudRepository<Pengumuman, Integer> {
    Pengumuman findById(long id);

}
