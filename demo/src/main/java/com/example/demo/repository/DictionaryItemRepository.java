package com.example.demo.repository;

import com.example.demo.entity.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Integer> {
    Optional<DictionaryItem> findByKey(String key);
}