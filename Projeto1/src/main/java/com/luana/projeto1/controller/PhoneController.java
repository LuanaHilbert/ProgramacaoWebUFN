package com.luana.projeto1.controller;

import com.luana.projeto1.dto.CreatePhoneDTO;
import com.luana.projeto1.dto.PhoneDTO;
import com.luana.projeto1.mapper.PhoneMapper;
import com.luana.projeto1.model.Phone;
import com.luana.projeto1.service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping
    public List<PhoneDTO> getAllPhones() {
        return phoneService.findAll().stream()
                .map(PhoneMapper::toDTO) // Uso do mapper
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneDTO> getPhoneById(@PathVariable Long id) {
        Optional<Phone> phone = phoneService.findById(id);
        return phone.map(p -> ResponseEntity.ok(PhoneMapper.toDTO(p))) // Mapper aqui
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createPhone(@RequestBody CreatePhoneDTO createPhoneDTO) {
        try {
            Phone phone = PhoneMapper.toEntity(createPhoneDTO); // Conversão via mapper
            Phone savedPhone = phoneService.save(createPhoneDTO.userId(), phone);
            return ResponseEntity.ok(PhoneMapper.toDTO(savedPhone));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        if (phoneService.existsById(id)) {
            phoneService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}