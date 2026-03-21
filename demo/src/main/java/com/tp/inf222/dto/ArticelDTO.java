package com.tp.inf222.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ArticelDTO {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ArticleRequest {

        @NotBlank(message = "Le titre est obligatoire")
        private String title;

        @NotBlank(message = "Le contenu est obligatoire")
        private String content;

        @NotBlank(message = "L'auteur est obligatoire")
        private String auteur;

        @NotBlank(message = "La date est obligatoire")
        private String date;

        @NotBlank(message = "La catégorie est obligatoire")
        private String categorie;

        private String tags;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ArticleResponse {
        private Integer id;
        private String title;
        private String content;
        private String auteur;
        private String date;
        private String categorie;
        private String tags;
        private String error;

        private ArticleResponse(String error){this.error = error;}
        public static ArticleResponse error(String error){return new ArticleResponse(error);}
    }
}
