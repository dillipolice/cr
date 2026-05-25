package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {

        List<Pet> pets = petRepository.findAll();
        List<Long> petIds = new ArrayList<>();

        for (Pet pet : pets) {
            Customer owner = pet.getOwner();

            if (owner != null && owner.getId().equals(customerId)) {
                petIds.add(pet.getId());
            }
        }

        List<Schedule> schedules = new ArrayList<>();

        for (Long petId : petIds) {
            schedules.addAll(scheduleRepository.findAllByPetsId(petId));
        }

        return schedules;
    }
}