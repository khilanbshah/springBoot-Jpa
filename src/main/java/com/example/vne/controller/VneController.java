package com.example.vne.controller;

import com.example.vne.Exception.ResourceNotFoundException;
import com.example.vne.model.Vne;
import com.example.vne.repository.VneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/Vne")
@Transactional
public class VneController {
    @Autowired
    VneRepository vneRepository;

    @GetMapping("/")
    public Iterable<Vne> getAllVne() {
        return vneRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Vne> findOne(@PathVariable long id) throws ResourceNotFoundException {
        Vne vne = vneRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vne not found for this id :: " + id));
        return ResponseEntity.ok().body(vne);
    }



    @PostMapping("/")
    public Vne save(@RequestBody Vne vne) {
        return vneRepository.save(vne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vne> updateVne(@PathVariable(value = "id") Long vneId,
                                               @RequestBody Vne vneDetails) throws ResourceNotFoundException {
        Vne vne = vneRepository.findById(vneId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + vneId));
        vne.setHost(vneDetails.getHost());
        vne.setPassword(vneDetails.getPassword());
        vne.setUser(vneDetails.getUser());


        final Vne updatedVne = vneRepository.save(vne);
        return ResponseEntity.ok(updatedVne);
    }


    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteById(@PathVariable long vneId) throws ResourceNotFoundException {

        Vne vne = vneRepository.findById(vneId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + vneId));

        vneRepository.deleteById(vneId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
