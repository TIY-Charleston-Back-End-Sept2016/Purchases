package com.theironyard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by rdw1995 on 10/24/16.
 */
public interface PurchaseRepo extends PagingAndSortingRepository<Purchase, Integer> {
    Page<Purchase> findByCategory (Pageable pageable, String category);

}
