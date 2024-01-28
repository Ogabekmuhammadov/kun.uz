package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    public RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(true);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());

        regionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public RegionDTO findById(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Region not found❌❌❌");
        }

        return toDTO(optional.get());

    }


    public Boolean updateById(Integer id, RegionDTO dto) {
        RegionEntity entity = get(id);

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        regionRepository.save(entity);
        return true;
    }


    public Boolean deleteById(Integer id) {
//        RegionEntity regionEntity = get(id);
        int effectedRows = regionRepository.delete(id);
        //
        return true;
    }

    public PageImpl<RegionDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<RegionEntity> entityPage = regionRepository.findAllByVisible(pageable, true);

        long totalElements = entityPage.getTotalElements();

        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityPage) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }
    public List<RegionDTO> getByLang(AppLanguage language) {
        List<RegionDTO> dtoList = new LinkedList<>();
        Iterable<RegionEntity> all = regionRepository.findAll();

        for (RegionEntity entity : all) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            switch (language) {
                case uz -> dto.setName(entity.getNameUz());
                case ru -> dto.setName(entity.getNameRu());
                default -> dto.setName(entity.getNameEn());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }


    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();

        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setVisible(entity.getVisible());

        return dto;
    }


    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Region not found");
        });
    }

}
