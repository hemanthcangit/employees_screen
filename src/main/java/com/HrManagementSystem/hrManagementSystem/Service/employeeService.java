package com.HrManagementSystem.hrManagementSystem.Service;

import com.HrManagementSystem.hrManagementSystem.DTO.employeeRequestDTO;
import com.HrManagementSystem.hrManagementSystem.DTO.employeeResponseDTO;
import com.HrManagementSystem.hrManagementSystem.Entity.Employee;
import com.HrManagementSystem.hrManagementSystem.Repository.employeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class employeeService {
    @Autowired
    private employeeRepository employeeRepo;

    //Employee Creation
    public employeeResponseDTO create(employeeRequestDTO dto){
        //From DTO to Entity
        Employee emp = mapToEntity(dto);
        //Saving Employee Details into DB
        Employee saved = employeeRepo.save(emp);
        //Converting saved details back to DTO and returning
        return mapToResponse(saved);
    }

    //Getting list of employees fromm DB
    public Page<employeeResponseDTO> getAllEmployees(int page,int Size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,Size,sort);
        Page<Employee> EmployeePage = employeeRepo.findByIsActiveTrue(pageable);
        return EmployeePage.map(this::mapToResponse);

    }
    //update Employee
    public employeeResponseDTO updateEmployee(Long id, employeeRequestDTO dto){
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setPhoneNo(dto.getPhoneNo());
        emp.setPersonalEmail(dto.getPersonalEmail());
        emp.setGender(dto.getGender());
        emp.setDateOfBirth(dto.getDateOfBirth());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setTimeZone(dto.getTimeZone());
        emp.setEmploymentType(dto.getEmploymentType());
        emp.setManager_id(dto.getManagerId());

        //Job details
        emp.setUserName(dto.getUserName());
        emp.setWorkEmail(dto.getWorkEmail());
        emp.setDateOfJoining(dto.getDateOfJoining());
        emp.setJobCategory(dto.getJobCategory());

        return mapToResponse(employeeRepo.save(emp));
    }

    //Making an Employee Inactive
    public void inactive(Long id){
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setActive(false);
        employeeRepo.save(emp);
        System.out.println("Employee updated Successfully");
    }

    //Deleting only Inactive Employees
    public void deleteAnEmployee(Long id){
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Employee Not Found"));
        if(Boolean.TRUE.equals(emp.isActive())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Active Employee cannot be deleted. Please deactivate first."
            );
        }
        employeeRepo.delete(emp);
    }

    //Returning Employee Response from DB : mapper : Employee->DTO
    private employeeResponseDTO mapToResponse(Employee saved) {
        employeeResponseDTO dto = new employeeResponseDTO();
        dto.setEmployeeId(saved.getId());
        dto.setFullName(saved.getFirstName() + " "+saved.getLastName());
        dto.setUserName(saved.getUserName());
        dto.setWorkEmail(saved.getWorkEmail());
        dto.setDateOfJoining(saved.getDateOfJoining());
        dto.setManagers(saved.getManager_id());
        dto.setPhoneNo(saved.getPhoneNo());
        return dto;
    }

    //Mapper: dto to entity
    private Employee mapToEntity(employeeRequestDTO dto) {
        Employee emp = new Employee();
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setPhoneNo(dto.getPhoneNo());
        emp.setPersonalEmail(dto.getPersonalEmail());
        emp.setGender(dto.getGender());
        emp.setDateOfBirth(dto.getDateOfBirth());
        emp.setMaritalStatus(dto.getMaritalStatus());
        emp.setTimeZone(dto.getTimeZone());
        emp.setEmploymentType(dto.getEmploymentType());
        emp.setManager_id(dto.getManagerId());

        //Job details
        emp.setUserName(dto.getUserName());
        emp.setWorkEmail(dto.getWorkEmail());
        emp.setDateOfJoining(dto.getDateOfJoining());
        emp.setJobCategory(dto.getJobCategory());

        emp.setActive(true);
        return emp;
    }

    //Get Employee By Id
    public employeeResponseDTO getById(Long id) {
        Employee emp = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToResponse(emp);
    }

    public Page<employeeResponseDTO> search(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        //Sorting
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        //Pagination
        Pageable pageable = PageRequest.of(page, size, sort);

        // Handle empty keyword
        if (keyword == null || keyword.isBlank()) {
            return employeeRepo.findByIsActiveTrue(pageable)
                    .map(this::mapToResponse);
        }

        //Search
        Page<Employee> employeePage =
                employeeRepo.findByFirstNameContaining(keyword, pageable);

        return employeePage.map(this::mapToResponse);
    }
}
