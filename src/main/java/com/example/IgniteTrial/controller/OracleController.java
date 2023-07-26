package com.example.IgniteTrial.controller;

import com.example.IgniteTrial.Exceptions.ResourceNotFoundException;
import com.example.IgniteTrial.model.company_3;
import com.example.IgniteTrial.repository.OracleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OracleController {


    /**
     * Contains the function to access data from DB using JPA
     */

    @Autowired
    OracleRepository oracleRepository;

    @GetMapping("/getAll")
    public List<company_3> getAllNotes() {
        return oracleRepository.findAll();
    }



    @PostMapping("/insert")
    public company_3 insert(@Valid @RequestBody company_3 company_3) {
        return oracleRepository.save(company_3);
    }

    @GetMapping("/get/{id}")
    public company_3 getNoteById(@PathVariable(value = "id") Integer company_code) {
        return oracleRepository.findById(company_code)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", company_code));
    }

    @PutMapping("/update")
    public company_3 updateNote(@Valid @RequestBody company_3 company_3) {

        company_3 comp = oracleRepository.findById(company_3.getCompany_code())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", company_3.getCompany_code()));

        comp.setComp_name(company_3.getComp_name());
        comp.setShare_price(company_3.getShare_price());

        company_3 updatedNote = oracleRepository.save(company_3);
        return updatedNote;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Integer company_code) {
        company_3 note = oracleRepository.findById(company_code)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", company_code));

        oracleRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
