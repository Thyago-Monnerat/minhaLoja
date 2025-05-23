package com.thyago.MinhaLoja.supplierTests;

import com.thyago.MinhaLoja.business.mappers.SupplierMapper;
import com.thyago.MinhaLoja.business.models.SupplierModel;
import com.thyago.MinhaLoja.business.repositories.SupplierRepository;
import com.thyago.MinhaLoja.business.services.SupplierService;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierAddDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierUpdateDto;
import com.thyago.MinhaLoja.infrastructure.exceptions.AlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.NotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierTest {

    @InjectMocks
    SupplierService supplierService;

    @Mock
    SupplierRepository supplierRepository;

    @Mock
    SupplierMapper supplierMapper;

    SupplierModel supplierModel;
    SupplierAddDto supplierAddDto;
    SupplierUpdateDto supplierUpdateDto;
    SupplierDto supplierDto;

    @BeforeEach
    void setup() {
        supplierModel = new SupplierModel();
        supplierModel.setId(1L);
        supplierModel.setName("supplier");
        supplierModel.setContact("123");

        supplierAddDto = new SupplierAddDto("supplier", "123123123");
        supplierUpdateDto = new SupplierUpdateDto("abc", "asdasd");

        supplierDto = new SupplierDto(1L, "supplier", "123", List.of());
    }

    @Test
    @DisplayName("Should return a list with registered suppliers")
    void shouldReturnAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(List.of(supplierModel));
        when(supplierMapper.toDto(supplierModel)).thenReturn(supplierDto);

        List<SupplierDto> result = supplierService.getSuppliers();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("supplier", result.get(0).name());
        verify(supplierRepository).findAll();
        verify(supplierMapper).toDto(supplierModel);
    }

    @Test
    @DisplayName("Should throw not found exception when supplier not found by id")
    void shouldThrowNotFoundWhenGetSupplierById() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFound.class, () -> supplierService.findSupplierById(1L));

        verify(supplierRepository).findById(1L);
    }

    @Test
    @DisplayName("Should add a new supplier")
    void shouldAddASupplier() {
        when(supplierRepository.findByName(supplierAddDto.name())).thenReturn(Optional.empty());
        when(supplierMapper.fromAddDtoToModel(supplierAddDto)).thenReturn(supplierModel);

        supplierService.addSupplier(supplierAddDto);

        verify(supplierRepository).save(supplierModel);
        verify(supplierMapper).fromAddDtoToModel(supplierAddDto);
    }

    @Test
    @DisplayName("Should throw already exists exception when adding supplier with duplicate name")
    void shouldThrowAlreadyExistsWhenAddDuplicateSupplier() {
        when(supplierRepository.findByName(supplierAddDto.name())).thenReturn(Optional.of(supplierModel));

        Assertions.assertThrows(AlreadyExists.class, () -> supplierService.addSupplier(supplierAddDto));

        verify(supplierRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update an existing supplier")
    void shouldUpdateASupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplierModel));

        doAnswer(invocation -> {
            SupplierUpdateDto dto = invocation.getArgument(0);
            SupplierModel model = invocation.getArgument(1);
            model.setName(dto.name());
            model.setContact(dto.contact());
            return null;
        }).when(supplierMapper).updateModelFromDto(any(), any());

        supplierService.updateSupplier(1L, supplierUpdateDto);

        Assertions.assertEquals("abc", supplierModel.getName());
        Assertions.assertEquals("asdasd", supplierModel.getContact());
        verify(supplierRepository).save(supplierModel);
    }

    @Test
    @DisplayName("Should throw not found exception when updating a non existent supplier")
    void shouldThrowNotFoundWhenUpdateNonExistentSupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFound.class, () -> supplierService.updateSupplier(1L, supplierUpdateDto));

        verify(supplierRepository).findById(1L);
        verify(supplierRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw already exists exception when updating supplier with existing name")
    void shouldThrowAlreadyExistsWhenUpdatingSupplierWithExistingName() {
        SupplierModel otherSupplier = new SupplierModel();
        otherSupplier.setId(2L);
        otherSupplier.setName("abc");

        when(supplierRepository.findByName("abc")).thenReturn(Optional.of(otherSupplier));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplierModel));

        Assertions.assertThrows(AlreadyExists.class, () -> supplierService.updateSupplier(1L, supplierUpdateDto));

        verify(supplierRepository).findByName("abc");
        verify(supplierRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete an existing supplier")
    void shouldDeleteASupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplierModel));

        supplierService.deleteSupplier(1L);

        verify(supplierRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw not found exception when deleting a non existent supplier")
    void shouldThrowNotFoundWhenDeleteNonExistentSupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFound.class, () -> supplierService.deleteSupplier(1L));

        verify(supplierRepository, never()).deleteById(any());
    }
}
