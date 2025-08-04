package com.skax.eatool.mbb.workflow.business.dc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.*;
import com.skax.eatool.mbb.workflow.business.dc.entity.Mbbuser;
import com.skax.eatool.mbb.workflow.business.dc.dto.MbbuserDomainDto;

/**
 * Mbbuser Repository Implementation
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Transactional
public class MbbuserRepositoryImpl implements MbbuserRepository {

    @Autowired
    private MbbuserRepository mbbuserRepository;

    @Override
    public List<MbbuserDomainDto> findAll() {
        return mbbuserRepository.findAll().stream()
                .map(this::convertToDomainDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MbbuserDomainDto> findById(Long id) {
        return mbbuserRepository.findById(id)
                .map(this::convertToDomainDto);
    }

    @Override
    public MbbuserDomainDto save(MbbuserDomainDto domainDto) {
        Mbbuser entity = convertToEntity(domainDto);
        Mbbuser savedEntity = mbbuserRepository.save(entity);
        return convertToDomainDto(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        mbbuserRepository.deleteById(id);
    }

    @Override
    public long count() {
        return mbbuserRepository.count();
    }

    // Entity ↔ Domain DTO 변환 메서드
    private MbbuserDomainDto convertToDomainDto(Mbbuser entity) {
        if (entity == null) return null;
        
        MbbuserDomainDto dto = new MbbuserDomainDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private Mbbuser convertToEntity(MbbuserDomainDto dto) {
        if (dto == null) return null;
        
        Mbbuser entity = new Mbbuser();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

}