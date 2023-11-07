package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.Tags;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends CrudRepository<Tags, Integer> {
    Tags findById(long id);
}
