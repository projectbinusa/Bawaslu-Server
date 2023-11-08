package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.JenisRegulasi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JenisRegulasiRepository extends CrudRepository<JenisRegulasi, Integer> {
    JenisRegulasi findById(long id);
}
