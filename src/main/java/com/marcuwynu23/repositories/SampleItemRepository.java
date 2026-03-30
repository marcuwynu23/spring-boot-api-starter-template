package com.marcuwynu23.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcuwynu23.models.SampleItem;

public interface SampleItemRepository extends JpaRepository<SampleItem, Long> {
}
