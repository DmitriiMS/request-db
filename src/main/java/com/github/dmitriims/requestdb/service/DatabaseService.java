package com.github.dmitriims.requestdb.service;

import com.github.dmitriims.requestdb.exceptions.*;
import com.github.dmitriims.requestdb.model.ElasticRequest;
import com.github.dmitriims.requestdb.model.Folder;
import com.github.dmitriims.requestdb.model.Request;
import com.github.dmitriims.requestdb.model.Tag;
import com.github.dmitriims.requestdb.repositories.FolderRepository;
import com.github.dmitriims.requestdb.repositories.RequestRepository;
import com.github.dmitriims.requestdb.repositories.TagRepository;
import com.github.dmitriims.requestdb.repositories.elasticsearch.ElasticRequestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final FolderRepository folderRepository;
    private final RequestRepository requestRepository;
    private final TagRepository tagRepository;
    private final ElasticRequestRepository elasticRequestRepository;
    private final ElasticService elasticService;

    public Request saveRequestToDb(String requestText) throws CustomRuntimeException {
        Request request = requestRepository.findByText(requestText);
        if(request != null) {
            throw new CustomRuntimeException("Request: '" + request.getText() + "' is already added to the database.");
        }
        request = new Request(requestText);
        request = requestRepository.save(request);

        ElasticRequest eRequest = new ElasticRequest();
        eRequest.setText(request.getText());
        eRequest.setMongoId(request.getId());
        elasticRequestRepository.save(eRequest);
        return request;
    }

    public Tag saveTagToDb(String tagName) throws CustomRuntimeException {
        Tag tag = tagRepository.findByTagName(tagName);
        if(tag != null) {
            throw new CustomRuntimeException("Tag: '" + tag.getTagName() + "' is already added to the database.");
        }
        tag = new Tag(tagName);
        return tagRepository.save(tag);
    }

    public Folder saveFolderToDb(String folderName) throws CustomRuntimeException {
        Folder folder = folderRepository.findByFolderName(folderName);
        if(folder != null) {
            throw new CustomRuntimeException("Folder: '" + folder.getFolderName() + "' is already added to the database.");
        }
        folder = new Folder(folderName);
        return folderRepository.save(folder);
    }

    public List<Request> getAllRequests() {
        List<Request> foundRequests = new ArrayList<>();
        requestRepository.findAll().forEach(foundRequests::add);
        return foundRequests;
    }

    public List<Tag> getAllTags() {
        List<Tag> foundTags = new ArrayList<>();
        tagRepository.findAll().forEach(foundTags::add);
        return foundTags;
    }

    public List<Folder> getAllFolders() {
        List<Folder> foundTags = new ArrayList<>();
        folderRepository.findAll().forEach(foundTags::add);
        return foundTags;
    }

    public Request addTagToRequest(String requestId, String tagId) throws CustomRuntimeException {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if(optionalRequest.isEmpty()) {
            throw new CustomRuntimeException("No request with id: " + requestId + " found in the database.");
        }
        if(optionalTag.isEmpty()) {
            throw new CustomRuntimeException("No tag with id: " + tagId + " found in the database");
        }
        Request request = optionalRequest.get();
        Tag tag = optionalTag.get();

        if(request.getTags().size() >= 10) {
            throw new CustomRuntimeException("Request: '" + request.getText() + "' already has maximum number of tags.");
        }
        request.addTag(tag);
        request.setModifiedDateToNow();
        return requestRepository.save(request);
    }

    public Request moveRequestToFolder(String requestId, String folderId) throws CustomRuntimeException {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        if(optionalRequest.isEmpty()) {
            throw new CustomRuntimeException("No request with id: " + requestId + " found in the database.");
        }
        if(optionalFolder.isEmpty()) {
            throw new CustomRuntimeException("No folder with id: " + folderId + " found in the database.");
        }
        Request request = optionalRequest.get();
        Folder folder = optionalFolder.get();
        request.setFolder(folder);
        request.setModifiedDateToNow();
        return requestRepository.save(request);
    }

    public List<Request> getRequestsByFolderId(String folderId) throws CustomRuntimeException {
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        if(optionalFolder.isEmpty()) {
            throw new CustomRuntimeException("No folder with id: " + folderId + " found in the database.");
        }
        return requestRepository.findAllByFolder(optionalFolder.get());
    }

    public List<Request> getRequestsByTagId(String tagId) throws CustomRuntimeException {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if(optionalTag.isEmpty()) {
            throw new CustomRuntimeException("No tag with id: " + tagId + " found in the database");
        }
        return requestRepository.findAllByTagsContaining(optionalTag.get());
    }

    public List<Request> searchRequestsByText(String query) {
        List<String> requestIds = elasticService.performSearch(query);
        List<Request> requests = new ArrayList<>();
        requestRepository.findAllById(requestIds).forEach(requests::add);
        return requests;
    }
}
