package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public void setAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee != null) {
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {

        DayOfWeek day = date.getDayOfWeek();

        return employeeRepository.findAll()
                .stream()
                .filter(employee ->
                        employee.getDaysAvailable() != null &&
                                employee.getDaysAvailable().contains(day) &&
                                employee.getSkills() != null &&
                                employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}