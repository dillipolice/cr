package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Customer owner = customerService.getCustomer(petDTO.getOwnerId());

        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setOwner(owner);

        Pet savedPet = petService.savePet(pet);

        if (owner.getPets() == null) {
            owner.setPets(new java.util.ArrayList<>());
        }

        owner.getPets().add(savedPet);
        customerService.saveCustomer(owner);

        petDTO.setId(savedPet.getId());

        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet pet = petService.getPet(petId);

        PetDTO dto = new PetDTO();

        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());

        if (pet.getOwner() != null) {
            dto.setOwnerId(pet.getOwner().getId());
        }

        return dto;
    }

    @GetMapping
    public List<PetDTO> getPets(){

        return petService.getPets()
                .stream()
                .map(pet -> {
                    PetDTO dto = new PetDTO();

                    dto.setId(pet.getId());
                    dto.setName(pet.getName());
                    dto.setType(pet.getType());
                    dto.setBirthDate(pet.getBirthDate());
                    dto.setNotes(pet.getNotes());

                    if (pet.getOwner() != null) {
                        dto.setOwnerId(pet.getOwner().getId());
                    }

                    return dto;
                })
                .toList();
    }
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.getPetsByOwner(ownerId)
                .stream()
                .map(pet -> {
                    PetDTO dto = new PetDTO();

                    dto.setId(pet.getId());
                    dto.setName(pet.getName());
                    dto.setType(pet.getType());
                    dto.setBirthDate(pet.getBirthDate());
                    dto.setNotes(pet.getNotes());

                    if (pet.getOwner() != null) {
                        dto.setOwnerId(pet.getOwner().getId());
                    }

                    return dto;
                })
                .toList();
    }
}
