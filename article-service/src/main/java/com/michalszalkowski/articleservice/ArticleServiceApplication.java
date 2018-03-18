package com.michalszalkowski.articleservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ArticleServiceApplication {

	@Bean
	CommandLineRunner commandLineRunner(ArticleRepository articleRepository) {
		return strings -> {
			Stream.of("Lorem 1", "Lorem  2", "Lorem 3").forEach(title -> articleRepository.save(new Article(title)));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ArticleServiceApplication.class, args);
	}
}

@Entity
class Article {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	public Article() {
	}

	public Article(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}

@RepositoryRestResource
interface ArticleRepository extends JpaRepository<Article, Long> {

	@RestResource(path = "by-title")
	Collection<Article> findByTitle(@Param("title") String title);
}