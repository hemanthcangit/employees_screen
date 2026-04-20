package com.HrManagementSystem.hrManagementSystem.Controller;

import com.HrManagementSystem.hrManagementSystem.DTO.employeeRequestDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.employeeResponseDTO;
import com.HrManagementSystem.hrManagementSystem.Service.employeeService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/api/employees")
public class employeeController {
    @Autowired
    private employeeService empService;

    @PostMapping("/addEmployee")
    public ResponseEntity<employeeResponseDTO> createEmployees(@RequestBody employeeRequestDTO empRequestDto){
        return ResponseEntity.ok(empService.create(empRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<employeeResponseDTO>> getEmployee(@RequestParam int Page, @RequestParam int Size
    ,@RequestParam(defaultValue="id") String sortBy
    ,@RequestParam(defaultValue = "asc") String sortDir){
        return ResponseEntity.ok(empService.getAllEmployees(Page,Size,sortBy,sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<employeeResponseDTO> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(empService.getById(id));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<employeeResponseDTO> update(@PathVariable Long id, @RequestBody
    employeeRequestDTO dto){
        return ResponseEntity.ok(empService.updateEmployee(id,dto));
    }

    @PutMapping("/inactive/{id}")
    public ResponseEntity<String> inactiveEmployee(@PathVariable Long id){
        empService.inactive(id);
        return ResponseEntity.ok("Employee made as inactive");
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        empService.deleteAnEmployee(id);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }

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
