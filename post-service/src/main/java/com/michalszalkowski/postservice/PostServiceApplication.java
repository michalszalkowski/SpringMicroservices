package com.michalszalkowski.articleservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class PostServiceApplication {

	@Bean
	CommandLineRunner commandLineRunner(PostRepository postRepository) {
		return strings -> {
			Stream.of("Lorem 1", "Lorem  2", "Lorem 3").forEach(title -> postRepository.save(new Post(title)));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}
}

@Entity
class Post {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	public Post() {
	}

	public Post(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}

@RepositoryRestResource
interface PostRepository extends JpaRepository<Post, Long> {

	@RestResource(path = "by-title")
	Collection<Post> findByTitle(@Param("title") String title);
}