package com.example.graphql.dao;

import com.example.graphql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
public interface AuthorDao extends JpaRepository<Author, Long> {

    Author findAuthorById(Long id);

    List<Author> findByIdIn(List<String> ids);
}
