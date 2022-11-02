package com.github.dmitriims.requestdb.repositories;

import com.github.dmitriims.requestdb.model.Folder;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface FolderRepository extends CrudRepository<Folder, String> {
    Folder findByFolderName(String folderName);
}
