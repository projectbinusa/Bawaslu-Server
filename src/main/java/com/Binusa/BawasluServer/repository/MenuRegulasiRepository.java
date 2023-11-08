package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.MenuRegulasi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRegulasiRepository extends CrudRepository<MenuRegulasi, Integer> {
    MenuRegulasi findById(long id);

    @Query(value = "SELECT * FROM menu_regulasi  WHERE jenis_regulasi_id = :jenisId", nativeQuery = true)
    List<MenuRegulasi> getByJenis(Long jenisId);
}
