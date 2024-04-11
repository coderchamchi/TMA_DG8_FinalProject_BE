package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.SizeService;
import com.bezkoder.springjwt.dto.ProductSaveRequest;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.dto.SizeRequestDTO;
import com.bezkoder.springjwt.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("ProjectSJ/Size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseJson<Boolean>> addProduct(@Validated @RequestBody SizeRequestDTO sizeRequestDTO)
    {
        boolean check = sizeService.saveSize(sizeRequestDTO);
        if(!check){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Error: Can not save this size"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Add Successes"));
    }

    @GetMapping("/test/{id}")
    public List<Size> getSizeService(@PathVariable("id") long id) {
        return sizeService.findByIdProduct(id);
    }
}
