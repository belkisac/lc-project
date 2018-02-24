package com.example.dateproject.models.data;

import com.example.dateproject.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductDao extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    List<Product> findByUserId(int id);
}
