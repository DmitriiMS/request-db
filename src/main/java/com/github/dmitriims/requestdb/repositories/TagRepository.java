package com.github.dmitriims.requestdb.repositories;

import com.github.dmitriims.requestdb.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface TagRepository extends CrudRepository<Tag, String> {
    Tag findByTagName(String tagName);
}
