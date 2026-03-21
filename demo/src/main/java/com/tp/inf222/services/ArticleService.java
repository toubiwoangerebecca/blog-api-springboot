package com.tp.inf222.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.tp.inf222.dto.ArticelDTO.ArticleRequest;
import com.tp.inf222.dto.ArticelDTO.ArticleResponse;
import com.tp.inf222.entity.ArticleEntity;
import com.tp.inf222.repository.ArticleRepository;

@Service
public class ArticleService 
{

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) 
    {
        this.articleRepository = articleRepository;
    }

    // == Mapping ==================

    private ArticleEntity toEntity(ArticleRequest req) 
    {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(req.getTitle());
        entity.setContent(req.getContent());
        entity.setAuteur(req.getAuteur());
        entity.setDate(req.getDate());
        entity.setCategorie(req.getCategorie());
        entity.setTags(req.getTags());
        return entity;
    }

    private ArticleResponse toResponse(ArticleEntity entity) 
    {
        return ArticleResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .auteur(entity.getAuteur())
                .date(entity.getDate())
                .categorie(entity.getCategorie())
                .tags(entity.getTags())
                .build();
    }

    // == CRUD =====================

    public ArticleResponse createArticle(ArticleRequest request) 
    {
        ArticleEntity find = articleRepository.findFirstByContent(request.getContent());

        if(find != null) return ArticleResponse.error("Ce livre existe deja");
        ArticleEntity saved = articleRepository.save(toEntity(request));
        return toResponse(saved);
    }

    public List<ArticleResponse> getAllArticles(String categorie, String date, String auteur) 
    {
        List<ArticleEntity> entities;

        if(categorie != null && date != null)
            entities = articleRepository.findByCategorieAndDate(categorie, date);
        else if(categorie != null)
            entities = articleRepository.findByCategorie(categorie);
        else if(date != null)
            entities = articleRepository.findByDate(date);
        else if(auteur != null)
            entities = articleRepository.findByAuteur(auteur);
        else
            entities = articleRepository.findAll();

        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ArticleResponse getArticleById(Integer id) 
    {
        return articleRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public ArticleResponse updateArticle(Integer id, ArticleRequest request) 
    {
        ArticleEntity article = articleRepository.findById(id).orElse(null);
        if(article == null) return null;

        if(request.getTitle() != null && !request.getTitle().isBlank())
            article.setTitle(request.getTitle());
        if(request.getContent() != null && !request.getContent().isBlank())
            article.setContent(request.getContent());
        if(request.getAuteur() != null && !request.getAuteur().isBlank())
            article.setAuteur(request.getAuteur());
        if(request.getDate() != null && !request.getDate().isBlank())
            article.setDate(request.getDate());
        if(request.getCategorie() != null && !request.getCategorie().isBlank())
            article.setCategorie(request.getCategorie());
        if(request.getTags() != null)
            article.setTags(request.getTags());

        return toResponse(articleRepository.save(article));
    }

    public boolean deleteArticle(Integer id) 
    {
        if(!articleRepository.existsById(id)) return false;
        articleRepository.deleteById(id);
        return true;
    }

    public List<ArticleResponse> searchArticles(String query) 
    {
        return articleRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }
}
