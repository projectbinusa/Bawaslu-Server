package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Regulasi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegulasiRepository extends CrudRepository<Regulasi, Integer> {
    Regulasi findById(long id);

    @Query(value = "SELECT * FROM regulasi  WHERE menu_regulasi = :menuRegulasi", nativeQuery = true)
    List<Regulasi> getByMenuRegulasi(Long menuRegulasi);
}
