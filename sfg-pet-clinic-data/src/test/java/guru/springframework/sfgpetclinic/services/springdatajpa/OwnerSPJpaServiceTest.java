package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.model.repositories.PetRepository;
import guru.springframework.sfgpetclinic.model.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSPJpaServiceTest {
    public static final String LAST_NAME = "Weston";
    public static final Long OWNER_ID = 3L;
    @Mock
    PetTypeRepository petTypeRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    OwnerRepository ownerRepository;
    @InjectMocks
    OwnerSPJpaService ownerSPJpaService;

    Owner ownerReturn;

    @BeforeEach
    void setUp() {
        ownerReturn = Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnersSet = new HashSet<>();
        returnOwnersSet.add(Owner.builder().id(453L).build());
        returnOwnersSet.add(Owner.builder().id(556L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwnersSet);

        Set<Owner> owners = ownerSPJpaService.findAll();
        assertNotNull(owners);
        assertEquals(2,owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(ownerReturn));
        Owner owner = ownerSPJpaService.findById(OWNER_ID);
        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerSPJpaService.findById(OWNER_ID);
        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(345L).build();
        when(ownerRepository.save(any())).thenReturn(ownerReturn);
        Owner SavedOwner = ownerSPJpaService.save(ownerToSave);
        assertNotNull(SavedOwner);

    }

    @Test
    void delete() {
        ownerSPJpaService.delete(ownerReturn);
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerSPJpaService.deleteById(OWNER_ID);
        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(ownerReturn);
        Owner owner = ownerSPJpaService.findByLastName(LAST_NAME);
        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
        verify(ownerRepository).findByLastName(any());
    }
}