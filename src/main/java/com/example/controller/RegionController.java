package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.HttpRequestUtil;
import com.example.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    /*
       Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");

        if (!role.equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

     */

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto, HttpServletRequest request) {

        //@RequestHeader(value = "Authorization") String jwt
//        JwtDTO jwtDTO = JWTUtil.decode(jwt);
//        Integer id = (Integer) request.getAttribute("id");
//        ProfileRole role = (ProfileRole) request.getAttribute("role");
//
//        if (!role.equals(ProfileRole.ADMIN)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }

        Integer id= HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN,ProfileRole.MODERATOR);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                          @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.deleteById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<RegionDTO>> pagination(@RequestParam Integer page,
                                                          @RequestParam Integer size) {
        return ResponseEntity.ok(regionService.pagination(page, size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<RegionDTO>> getByLanguage(@RequestParam(value = "lang", defaultValue = "uz")
                                                         AppLanguage language) {
        return ResponseEntity.ok(regionService.getByLang(language));
    }

}
