package com.skax.eatool.mbb.workflow.business.dc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.*;
import com.skax.eatool.mbb.workflow.business.dc.entity.Mbbuser;
import com.skax.eatool.mbb.workflow.business.dc.dto.MbbuserDomainDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Mbbuser Repository Interface
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface MbbuserRepository extends JpaRepository<Mbbuser, Long> {

    // 기본 CRUD 메서드
    List<Mbbuser> findAll();

    Optional<Mbbuser> findById(Long id);

    Mbbuser save(Mbbuser entity);

    void deleteById(Long id);

    long count();

    // 커스텀 메서드
    List<Mbbuser> findByName(String name);

    Optional<Mbbuser> findByNameIgnoreCase(String name);

    Optional<Mbbuser> findByEmail(String email);

    // 페이징 메서드
    Page<Mbbuser> findAll(Pageable pageable);

    // 검색 메서드
    @Query("SELECT e FROM Mbbuser e WHERE e.name LIKE %:keyword% OR e.email LIKE %:keyword%")
    List<Mbbuser> searchByKeyword(@Param("keyword") String keyword);

}