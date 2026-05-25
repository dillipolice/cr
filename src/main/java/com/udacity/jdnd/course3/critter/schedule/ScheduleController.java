package com.udacity.jdnd.course3.critter.schedule;


import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        List<Employee> employees =
                employeeRepository.findAllById(scheduleDTO.getEmployeeIds());

        List<Pet> pets =
                petRepository.findAllById(scheduleDTO.getPetIds());

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);

        scheduleDTO.setId(savedSchedule.getId());

        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.getAllSchedules()
                .stream()
                .map(schedule -> {
                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.getScheduleForPet(petId)
                .stream()
                .map(schedule -> {
                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return scheduleService.getScheduleForEmployee(employeeId)
                .stream()
                .map(schedule -> {
                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        return scheduleService.getScheduleForCustomer(customerId)
                .stream()
                .map(schedule -> {
                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }
}
