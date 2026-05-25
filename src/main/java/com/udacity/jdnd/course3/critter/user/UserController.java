package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetRepository petRepository;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());

        Customer savedCustomer = customerService.saveCustomer(customer);

        customerDTO.setId(savedCustomer.getId());

        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {

        return customerService.getAllCustomers()
                .stream()
                .map(customer -> {
                    CustomerDTO dto = new CustomerDTO();

                    dto.setId(customer.getId());
                    dto.setName(customer.getName());
                    dto.setPhoneNumber(customer.getPhoneNumber());
                    dto.setNotes(customer.getNotes());

                    List<Long> petIds = petRepository.findByOwnerIdOrderByIdAsc(customer.getId())
                            .stream()
                            .map(pet -> pet.getId())
                            .toList();

                    dto.setPetIds(petIds);

                    return dto;
                })
                .toList();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {

        Customer customer = customerService.getOwnerByPet(petId);

        CustomerDTO dto = new CustomerDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setNotes(customer.getNotes());

        dto.setPetIds(
                petRepository.findByOwnerIdOrderByIdAsc(customer.getId())
                        .stream()
                        .map(pet -> pet.getId())
                        .toList()
        );

        return dto;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        Employee savedEmployee = employeeService.saveEmployee(employee);

        employeeDTO.setId(savedEmployee.getId());

        return employeeDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        Employee employee = employeeService.getEmployee(employeeId);

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());

        return dto;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) {

        employeeService.setAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        return employeeService.findEmployeesForService(
                        employeeDTO.getSkills(),
                        employeeDTO.getDate())
                .stream()
                .map(employee -> {
                    EmployeeDTO dto = new EmployeeDTO();

                    dto.setId(employee.getId());
                    dto.setName(employee.getName());
                    dto.setSkills(employee.getSkills());
                    dto.setDaysAvailable(employee.getDaysAvailable());

                    return dto;
                })
                .toList();
    }
}