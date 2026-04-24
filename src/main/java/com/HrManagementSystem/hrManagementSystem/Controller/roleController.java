package com.HrManagementSystem.hrManagementSystem.Controller;

import com.HrManagementSystem.hrManagementSystem.DTO.RoleDTO;
import com.HrManagementSystem.hrManagementSystem.Repository.RoleRepository;
import com.HrManagementSystem.hrManagementSystem.Service.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/api/roles")
public class roleController {
    @Autowired
    private RoleRepository roleRepository;
    private roleService roleServ;

    @PostMapping("/add")
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO) {
        roleServ.addRole(roleDTO);
        return roleDTO;
    }

    @PostMapping("/updateByID")
    public RoleDTO updateRole(@RequestParam Long id, @RequestBody RoleDTO roleDTO) {
        return roleServ.updateRole(id, roleDTO);
    }

    @GetMapping("/getroles")
    public List<RoleDTO> getRoles() {
        return roleServ.getAllRoles();
    }

    @GetMapping("getRolesById")
    public RoleDTO getRole(@RequestParam Long id) {
        return roleServ.getRoleById(id);
    }

    @DeleteMapping("/deleteRoleByID")
    public void deleteRole(@RequestParam Long id) {
        roleServ.deleteRole(id);
        ResponseEntity.ok("Role deleted successfully");
    }
}
