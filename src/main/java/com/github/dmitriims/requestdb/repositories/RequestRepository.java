package com.github.dmitriims.requestdb.repositories;

import com.github.dmitriims.requestdb.model.Folder;
import com.github.dmitriims.requestdb.model.Request;
import com.github.dmitriims.requestdb.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, String> {
    Request findByText(String text);

    List<Request> findAllByFolder(Folder folder);
    List<Request> findAllByTagsContaining(Tag tag);
}
