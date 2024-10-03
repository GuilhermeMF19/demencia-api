package com.gmf.demencia_api.controllers;

import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.domain.medicalappointment.dtos.MedicalAppointmentDTO;
import com.gmf.demencia_api.services.MedicalAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/medicalappointment/v1")
@Tag(name = "Consultas Médicas", description = "Endpoints para gerenciamento de consultas médicas.")
public class MedicalAppointmentController {

    @Autowired
    private MedicalAppointmentService medicalAppointmentService;

    @GetMapping
    @Operation(summary = "Todas as consultas médicas.", description = "Lista todas as consultas médicas.",
            tags = {"Consultas Médicas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MedicalAppointmentDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Page<MedicalAppointmentDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(medicalAppointmentService.findAll(pageable).map(MedicalAppointmentDTO::new));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Uma consulta médica.", description = "Busca uma consulta médica pelo ID.",
            tags = {"Consultas Médicas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = MedicalAppointmentDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<MedicalAppointmentDTO> findById(@PathVariable String id) {
        MedicalAppointment appointment = medicalAppointmentService.findById(id);
        MedicalAppointmentDTO appointmentResponseDTO = new MedicalAppointmentDTO(appointment);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Adiciona uma consulta médica.", description = "Adiciona uma nova consulta médica.",
            tags = {"Consultas Médicas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = MedicalAppointment.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<MedicalAppointment> create(@RequestBody MedicalAppointmentDTO appointmentDto, Authentication authentication) {
        var appointment = new MedicalAppointment();
        BeanUtils.copyProperties(appointmentDto, appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalAppointmentService.create(appointment, authentication));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualiza uma consulta médica.", description = "Atualiza uma consulta médica existente.",
            tags = {"Consultas Médicas"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = MedicalAppointment.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<MedicalAppointment> update(@PathVariable String id, @RequestBody MedicalAppointmentDTO appointmentDto) {
        var appointment = new MedicalAppointment();
        BeanUtils.copyProperties(appointmentDto, appointment);
        return ResponseEntity.status(HttpStatus.OK).body(medicalAppointmentService.update(id, appointment));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta uma consulta médica.", description = "Remove uma consulta médica pelo ID.",
            tags = {"Consultas Médicas"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        medicalAppointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
