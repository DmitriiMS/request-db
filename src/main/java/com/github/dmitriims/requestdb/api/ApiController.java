package com.github.dmitriims.requestdb.api;

import com.github.dmitriims.requestdb.dto.*;
import com.github.dmitriims.requestdb.model.Folder;
import com.github.dmitriims.requestdb.model.Request;
import com.github.dmitriims.requestdb.model.Tag;
import com.github.dmitriims.requestdb.service.DatabaseService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@NoArgsConstructor
public class ApiController {

    private DatabaseService databaseService;

    @Autowired
    public ApiController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/saveRequest")
    public ResponseEntity<Request> saveRequest(@RequestBody(required = false) IncomingRequest incomingRequest) {
        return new ResponseEntity<>(databaseService.saveRequestToDb(incomingRequest.getRequestText()), HttpStatus.CREATED);
    }

    @PostMapping("/saveTag")
    public ResponseEntity<Tag> saveTag(@RequestBody(required = false) IncomingTag incomingTag) {
        return new ResponseEntity<>(databaseService.saveTagToDb(incomingTag.getTagName()), HttpStatus.CREATED);
    }

    @PostMapping("/saveFolder")
    public ResponseEntity<Folder> saveFolder(@RequestBody(required = false) IncomingFolder incomingFolder) {
        return new ResponseEntity<>(databaseService.saveFolderToDb(incomingFolder.getFolderName()), HttpStatus.CREATED);
    }

    @GetMapping("/getRequests")
    public ResponseEntity<List<Request>> getRequests() {
        return new ResponseEntity<>(databaseService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("/getTags")
    public ResponseEntity<List<Tag>> getTags() {
        return new ResponseEntity<>(databaseService.getAllTags(), HttpStatus.OK);
    }

    @GetMapping("/getFolders")
    public ResponseEntity<List<Folder>> getFolders() {
        return new ResponseEntity<>(databaseService.getAllFolders(), HttpStatus.OK);
    }

    @PostMapping("/addTag")
    public ResponseEntity<Request> addTagToRequest(@RequestBody TagRequest tagRequest) {
        return new ResponseEntity<>(databaseService.addTagToRequest(tagRequest.getRequestId(), tagRequest.getTagId()), HttpStatus.OK);
    }

    @PostMapping("/moveToFolder")
    public ResponseEntity<Request> moveRequestToFolder(@RequestBody MoveToFolder moveTo) {
        return new ResponseEntity<>(databaseService.moveRequestToFolder(moveTo.getRequestId(), moveTo.getFolderId()), HttpStatus.OK);
    }

    @PostMapping("/getRequestsByFolder")
    public ResponseEntity<List<Request>> getRequestsByFolder(@RequestBody(required = false) IncomingFolder incomingFolder) {
        return new ResponseEntity<>(databaseService.getRequestsByFolderId(incomingFolder.getFolderId()), HttpStatus.OK);
    }

    @PostMapping("/getRequestsByTag")
    public ResponseEntity<List<Request>> getRequestsByFolder(@RequestBody(required = false) IncomingTag incomingTag) {
        return new ResponseEntity<>(databaseService.getRequestsByTagId(incomingTag.getTagId()), HttpStatus.OK);
    }

}
