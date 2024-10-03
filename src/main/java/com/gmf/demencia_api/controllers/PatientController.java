package com.gmf.demencia_api.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.patient.dtos.PatientDTO;
import com.gmf.demencia_api.services.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/patient/v1")
@Tag(name = "Paciente", description = "Endpoints para gerenciamento de pacientes.")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    @Operation(summary = "Todos os pacientes.", description = "Todos os pacientes.", 
    tags = {"Paciente"}, 
    responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class))
                                    )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Page<PatientDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAll(pageable).map(PatientDTO::new));
    }

    @GetMapping(value="/{id}")
    @Operation(summary = "Um paciente.", description = "Um paciente.", 
    tags = {"Paciente"}, 
    responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                    content = @Content(schema = @Schema(implementation = PatientDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PatientDTO> findById(@PathVariable String id) {
        Patient patient = patientService.findById(id);
        PatientDTO patientResponseDTO = new PatientDTO(patient);
        return ResponseEntity.status(HttpStatus.OK).body(patientResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Adiciona um paciente.", 
    description = "Adiciona um paciente.", 
    tags = {"Paciente"}, 
    responses = {
            @ApiResponse(description = "Success", responseCode = "201", 
                    content = @Content(schema = @Schema(implementation = Patient.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Patient> create(@RequestBody PatientDTO patientDto, Authentication authentication) {
        var patient = new Patient();
        BeanUtils.copyProperties(patientDto, patient);
        return  ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(patient, authentication));
    }

    @PutMapping(value="/{id}")
    @Operation(summary = "Atualiza um paciente.", 
    description = "Atualiza um paciente.", 
    tags = {"Paciente"}, 
    responses = {
            @ApiResponse(description = "Updated", responseCode = "200", 
                    content = @Content(schema = @Schema(implementation = Patient.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Patient> update(@PathVariable String id, @RequestBody PatientDTO patientDto) {
        var patient = new Patient();
        BeanUtils.copyProperties(patientDto, patient);
        return ResponseEntity.status(HttpStatus.OK).body(patientService.update(id, patient));
    }

    @DeleteMapping(value="/{id}")
    @Operation(summary = "Deleta um paciente.", 
    description = "Deleta um paciente.", 
    tags = {"Paciente"}, 
    responses = {
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
