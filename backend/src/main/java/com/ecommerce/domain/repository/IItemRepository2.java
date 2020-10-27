package com.ecommerce.domain.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.ItemJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository2 extends JpaRepository<ItemJpa, Long> {
    Page<ItemJpa> getByNameContainingAndProgressAndAvailable(Pageable pageable, String name, Boolean progress,
            Boolean available);

    Page<ItemJpa> getBySellerAndProgressAndAvailable(Pageable pageable, long name, Boolean progress, Boolean available);

    Page<ItemJpa> getByNameContainingAndCategoryContainingAndProgressAndAvailable(Pageable pageable, String name,
            String category, Boolean progress, Boolean available);

    Page<ItemJpa> getBySellerAndCategoryContainingAndProgressAndAvailable(Pageable pageable, long seller,
            String category, Boolean progress, Boolean available);
}
