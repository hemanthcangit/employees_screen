package com.HrManagementSystem.hrManagementSystem.Controller;

import com.HrManagementSystem.hrManagementSystem.DTO.employeeRequestDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.employeeResponseDTO;
import com.HrManagementSystem.hrManagementSystem.Service.employeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/api/employees")
@Tag(name = "Employee APIs", description = "Operations related to Employees")
public class employeeController {
    @Autowired
    private employeeService empService;

    @Operation(summary = "Create Employee")
    @PostMapping("/addEmployee")
    public ResponseEntity<employeeResponseDTO> createEmployees(@RequestBody employeeRequestDTO empRequestDto){
        return ResponseEntity.ok(empService.create(empRequestDto));
    }
    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<Page<employeeResponseDTO>> getEmployee(@RequestParam int Page, @RequestParam int Size
    ,@RequestParam(defaultValue="id") String sortBy
    ,@RequestParam(defaultValue = "asc") String sortDir){
        return ResponseEntity.ok(empService.getAllEmployees(Page,Size,sortBy,sortDir));
    }

    @Operation(summary = "Get Employee By ID")
    @GetMapping("/{id}")
    public ResponseEntity<employeeResponseDTO> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(empService.getById(id));

    }

    @Operation(summary = "update Employee By Id")
    @PutMapping("/update/{id}")
    public ResponseEntity<employeeResponseDTO> update(@PathVariable Long id, @RequestBody
    employeeRequestDTO dto){
        return ResponseEntity.ok(empService.updateEmployee(id,dto));
    }

    @Operation(summary = "Inactive EMployee By ID")
    @PutMapping("/inactive/{id}")
    public ResponseEntity<String> inactiveEmployee(@PathVariable Long id){
        empService.inactive(id);
        return ResponseEntity.ok("Employee made as inactive");
    }

    @Operation(summary = "Delete Employee By ID")
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        empService.deleteAnEmployee(id);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }

    @Operation(summary = "Search Employee")
    @GetMapping("/search")
    public ResponseEntity<Page<employeeResponseDTO>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                empService.search(keyword, page, size, sortBy, sortDir)
        );
    }
}
