package com.github.dmitriims.requestdb.api;

import com.github.dmitriims.requestdb.dto.*;
import com.github.dmitriims.requestdb.exceptions.CustomRuntimeException;
import com.github.dmitriims.requestdb.model.Folder;
import com.github.dmitriims.requestdb.model.Request;
import com.github.dmitriims.requestdb.model.Tag;
import com.github.dmitriims.requestdb.service.DatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Сохранить запрос", description = "Сохраняет запрос по тексту запроса", operationId = "saveRequests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Запрос успешно сохранён"),
            @ApiResponse(responseCode = "400", description = "Запрос уже существует в базе данных")
    })
    @PostMapping(value = "/saveRequest", produces = "application/json")
    public ResponseEntity<Request> saveRequest(@RequestBody(required = false) IncomingRequest incomingRequest) {
        if(incomingRequest == null || incomingRequest.getRequestText().isBlank()) {
            throw new CustomRuntimeException("Пустой запрос.");
        }
        return new ResponseEntity<>(databaseService.saveRequestToDb(incomingRequest.getRequestText()), HttpStatus.CREATED);
    }

    @Operation(summary = "Сохранить тег", description = "Сохраняет тег по имени тега", operationId = "saveTag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Тег успешно сохранён"),
            @ApiResponse(responseCode = "400", description = "Тег уже существует в базе данных")
    })
    @PostMapping(value = "/saveTag", produces = "application/json")
    public ResponseEntity<Tag> saveTag(@RequestBody(required = false) IncomingTag incomingTag) {
        if(incomingTag == null || incomingTag.getTagName().isBlank()) {
            throw new CustomRuntimeException("Пустой запрос.");
        }
        return new ResponseEntity<>(databaseService.saveTagToDb(incomingTag.getTagName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Сохранить папку", description = "Сохраняет папку по имени папки", operationId = "saveFolder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Папка успешно сохранена"),
            @ApiResponse(responseCode = "400", description = "Папка уже существует в базе данных")
    })
    @PostMapping(value = "/saveFolder", produces = "application/json")
    public ResponseEntity<Folder> saveFolder(@RequestBody(required = false) IncomingFolder incomingFolder) {
        if(incomingFolder == null || incomingFolder.getFolderName().isBlank()) {
            throw new CustomRuntimeException("Пустой запрос.");
        }
        return new ResponseEntity<>(databaseService.saveFolderToDb(incomingFolder.getFolderName()), HttpStatus.CREATED);
    }

    @Operation(summary = "Получить список запросов", description = "Получает список всех запросов, сохранённых в системе", operationId = "getRequests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список запросов успешно получен")
    })
    @GetMapping(value = "/getRequests", produces = "application/json")
    public ResponseEntity<List<Request>> getRequests() {
        return new ResponseEntity<>(databaseService.getAllRequests(), HttpStatus.OK);
    }

    @Operation(summary = "Получить список тегов", description = "Получает список всех тегов, сохранённых в системе", operationId = "getTags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список тегов успешно получен")
    })
    @GetMapping(value = "/getTags", produces = "application/json")
    public ResponseEntity<List<Tag>> getTags() {
        return new ResponseEntity<>(databaseService.getAllTags(), HttpStatus.OK);
    }

    @Operation(summary = "Получить список папок", description = "Получает список всех папок, сохранённых в системе", operationId = "getFolders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список папок успешно получен")
    })
    @GetMapping(value = "/getFolders", produces = "application/json")
    public ResponseEntity<List<Folder>> getFolders() {
        return new ResponseEntity<>(databaseService.getAllFolders(), HttpStatus.OK);
    }

    @Operation(summary = "Добавить тег к запросу", description = "Добавляет тег к запросу по идентификаторам запроса и тега", operationId = "addTag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тег успешно добавлен к запросу"),
            @ApiResponse(responseCode = "400", description = "Запрос или тег не найдены в базе данных, или у запроса уже есть 10 тегов")
    })
    @PostMapping(value = "/addTag", produces = "application/json")
    public ResponseEntity<Request> addTagToRequest(@RequestBody TagRequest tagRequest) {
        return new ResponseEntity<>(databaseService.addTagToRequest(tagRequest.getRequestId(), tagRequest.getTagId()), HttpStatus.OK);
    }

    @Operation(summary = "Переместить запрос в папку", description = "Перемещает запрос в папку по идентификаторам запроса и папки", operationId = "moveToFolder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос успешно перемещён в папку"),
            @ApiResponse(responseCode = "400", description = "Запрос или папка не найдены в базе данных")
    })
    @PostMapping(value = "/moveToFolder", produces = "application/json")
    public ResponseEntity<Request> moveRequestToFolder(@RequestBody MoveToFolder moveTo) {
        return new ResponseEntity<>(databaseService.moveRequestToFolder(moveTo.getRequestId(), moveTo.getFolderId()), HttpStatus.OK);
    }

    @Operation(summary = "Найти все запросы в папке", description = "Находит все запросы в папке с указанным идентификатором", operationId = "getRequestsByFolder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запросы в указанной папке успешно найдены"),
            @ApiResponse(responseCode = "400", description = "Нет папки с указанным идентификатором")
    })
    @PostMapping(value = "/getRequestsByFolder", produces = "application/json")
    public ResponseEntity<List<Request>> getRequestsByFolder(@RequestBody(required = false) IncomingFolder incomingFolder) {
        return new ResponseEntity<>(databaseService.getRequestsByFolderId(incomingFolder.getFolderId()), HttpStatus.OK);
    }

    @Operation(summary = "Найти все запросы по тегу", description = "Находит все запросы, у которых есть указанный тег", operationId = "getRequestsByTag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запросы с указанным тегом успешно найдены"),
            @ApiResponse(responseCode = "400", description = "Нет тега с таким идентификатором")
    })
    @PostMapping(value = "/getRequestsByTag", produces = "application/json")
    public ResponseEntity<List<Request>> getRequestsByFolder(@RequestBody(required = false) IncomingTag incomingTag) {
        return new ResponseEntity<>(databaseService.getRequestsByTagId(incomingTag.getTagId()), HttpStatus.OK);
    }

    @Operation(summary = "Найти все запросы удовлетворяющие поисковому запросу", description = "Находит все запросы, у которых текст похож на поисковый запрос", operationId = "searchRequestsByText")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запросы с подходящим текстом найдены"),
    })
    @PostMapping(value = "/searchRequestsByText", produces = "application/json")
    public ResponseEntity<List<Request>> findRequestsByText(@RequestBody SearchText searchText) {
        return new ResponseEntity<>(databaseService.searchRequestsByText(searchText.getSearchText()), HttpStatus.OK);
    }

}
