package com.kaan.barcode.repository;

import com.kaan.barcode.entity.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long>{
    Optional<Barcode> findByProductId(Long productId);
}
