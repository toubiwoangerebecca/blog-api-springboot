package com.tp.inf222.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tp.inf222.entity.ArticleEntity;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    List<ArticleEntity> findByAuteur(String auteur);
    List<ArticleEntity> findByCategorie(String categorie);
    List<ArticleEntity> findByDate(String date);
    ArticleEntity findFirstByContent(String content);
    List<ArticleEntity> findByCategorieAndDate(String categorie, String date);
    List<ArticleEntity> findByTitle(String title);

    List<ArticleEntity> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
        String title, String content
    );
}
