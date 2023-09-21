package com.kodigo.shopping.online.store.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IGenericController<T, ID> {

    @GetMapping("/all")
    public ResponseEntity<Page<T>> getPage(@PageableDefault(size = 10) Pageable pageable);

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable ID id);

    @GetMapping("/actives/{filter}")
    public ResponseEntity<Page<T>> getPage(@PageableDefault(size = 10) Pageable pageable, @PathVariable Boolean filter);

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T model);

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody T model);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id);

}
