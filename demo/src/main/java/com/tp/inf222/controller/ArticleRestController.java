package com.tp.inf222.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.tp.inf222.dto.ArticelDTO.ArticleRequest;
import com.tp.inf222.dto.ArticelDTO.ArticleResponse;
import com.tp.inf222.services.ArticleService;

@RestController @RequestMapping("/api/articles")
@Tag(name = "Articles", description = "Gestion des articles du blog")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    @Operation(summary = "Creer un article")
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest request) {
        ArticleResponse saved = articleService.createArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    @Operation(summary = "Lister tous les articles(filtres : categorie, date, auteur)")
    public ResponseEntity<List<ArticleResponse>> getAllArticles(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String auteur) {
        return ResponseEntity.ok(articleService.getAllArticles(categorie, date, auteur));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des articles par nom ou contenu")
    public ResponseEntity<List<ArticleResponse>> searchArticles(@RequestParam String query) {
        return ResponseEntity.ok(articleService.searchArticles(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lire un article par son ID")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Integer id) {
        ArticleResponse article = articleService.getArticleById(id);
        if(article == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un article")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Integer id,
            @RequestBody ArticleRequest request) {
        ArticleResponse updated = articleService.updateArticle(id, request);
        if(updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un article")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer id) {
        boolean supprime = articleService.deleteArticle(id);
        if(!supprime)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Article ID " + id + " introuvable.");
        return ResponseEntity.ok("Article ID " + id + " supprime avec succes.");
    }
}
