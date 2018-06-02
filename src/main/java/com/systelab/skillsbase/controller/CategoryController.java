package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Category;
import com.systelab.skillsbase.repository.CategoryRepository;
import com.systelab.skillsbase.repository.CategoryNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "Category", description = "API for Category management", tags = {"Category"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/skillsbase/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    

    @ApiOperation(value = "Get all Categories", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("")
    @PermitAll
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @ApiOperation(value = "Get Category", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/{uid}")
    public ResponseEntity<Category> getCategory(@PathVariable("uid") Long skillId) {
        return this.categoryRepository.findById(skillId).map(ResponseEntity::ok).orElseThrow(() -> new CategoryNotFoundException(skillId));

    }

    @ApiOperation(value = "Create a Category", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody @ApiParam(value = "Category", required = true) @Valid Category s) {
        Category Category = this.categoryRepository.save(s);

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(Category.getId()).toUri();
        return ResponseEntity.created(uri).body(Category);
    }

    @ApiOperation(value = "Create or Update (idempotent) an existing Category", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/{uid}")
    public ResponseEntity<Category> updateCategory(@PathVariable("uid") Long categoryId, @RequestBody @ApiParam(value = "Category", required = true) @Valid Category s) {
        return this.categoryRepository
                .findById(categoryId)
                .map(existing -> {
                    s.setId(categoryId);
                    Category Category = this.categoryRepository.save(s);
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfLink).body(Category);
                }).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }


    @ApiOperation(value = "Delete a Category", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/{uid}")
    public ResponseEntity<?> removeCategory(@PathVariable("uid") Long categoryId) {
        return this.categoryRepository.findById(categoryId)
                .map(c -> {
                    categoryRepository.delete(c);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}