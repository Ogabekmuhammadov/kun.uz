package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exp.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
    public static Integer getProfileId(HttpServletRequest request, ProfileRole... requeridRoleList){
        Integer id=(Integer) request.getAttribute("id");
        ProfileRole role=(ProfileRole) request.getAttribute("role");

        if (requeridRoleList.length==0){
            return id;
        }
        for (ProfileRole requeridRole:requeridRoleList){
            if (role.equals(requeridRole)){
                return id;
            }
        }
        throw new ForbiddenException("Method not allowed");

//        if (!role.equals(requeridRole)) {
//
//            throw new ForbiddenException("Method not allowed");
//        }
//        return id;

    }

    public static JwtDTO getJWTDTO(HttpServletRequest request, ProfileRole... requiredRoleList) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id,role);
        for (ProfileRole requiredRole : requiredRoleList) {
            if (role.equals(requiredRole)) {
                return dto;
            }
        }
        throw new ForbiddenException("Method not allowed");
    }

}
