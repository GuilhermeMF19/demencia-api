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

import com.gmf.demencia_api.domain.test.Test;
import com.gmf.demencia_api.domain.test.dtos.CummingsDTO;
import com.gmf.demencia_api.domain.test.dtos.KatzDTO;
import com.gmf.demencia_api.domain.test.dtos.LawtonDTO;
import com.gmf.demencia_api.domain.test.dtos.MeemDTO;
import com.gmf.demencia_api.domain.test.dtos.TestDTO;
import com.gmf.demencia_api.services.TestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/test/v1")
@Tag(name = "Testes", description = "Endpoints para gerenciamento de testes.")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value="/{id}")
    @Operation(summary = "Um teste.", description = "Um teste.", 
    tags = {"Testes"}, 
    responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                    content = @Content(schema = @Schema(implementation = TestDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<TestDTO> findById(@PathVariable String id) {
        Test test = testService.findById(id);
        TestDTO testResponseDTO = new TestDTO(test);
        return ResponseEntity.status(HttpStatus.OK).body(testResponseDTO);
    }
    
    @PostMapping("/katz")
    @Operation(summary = "Adiciona o teste Escala de Katz.", 
               description = "Se o total é igual a 0, a pessoa é independente para todas as atividades.\n\n" +
                       "Se o total é maior que 0 e menor que 6, a pessoa é dependente para um número específico de atividades. O número de atividades dependentes será indicado pelo valor total.\n\n" +
                       "Se o total é 6, a pessoa é dependente para todas as atividades.", 
               tags = {"Testes"}, 
               responses = {
                   @ApiResponse(description = "Success", responseCode = "201", 
                               content = @Content(schema = @Schema(implementation = TestDTO.class))),
                   @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
               })
    public ResponseEntity<TestDTO> createKatzTest(@RequestBody KatzDTO katzDTO, Authentication authentication) {
        Test test = testService.createKatzTest(katzDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TestDTO(test.getType(), test.getResult()));
    }

    @PostMapping("/lawton")
    @Operation(summary = "Adiciona o teste Escala de Lawton.", 
               description = "Esta escala avalia o desempenho do idoso em relação às atividades instrumentais a fim de verificar a sua independência funcional.\n" +
                       "\n**Sem Ajuda** - 3\n" +
                       "\n**Ajuda Parcial** - 2\n" +
                       "\n**Não Consegue** - 1\n"
                       + "\n**Valor dos resultados:**\n"
                       + "\nIndependente: Total >= 25\n"
                       + "\nDependência Leve: 21 <= Total < 25\n"
                       + "\nDependência Moderada: 16 <= Total <= 20\n"
                       + "\nDependência Grave: 10 <= Total <= 15\n"
                       + "\nDependência Total: Total < 10\n",                                                                                           
               tags = {"Testes"}, 
               responses = {
                   @ApiResponse(description = "Success", responseCode = "201", 
                               content = @Content(schema = @Schema(implementation = TestDTO.class))),
                   @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
               })
    public ResponseEntity<TestDTO> createLawtonTest(@RequestBody LawtonDTO lawtonDTO, Authentication authentication) {
        Test test = testService.createLawtonTest(lawtonDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TestDTO(test.getType(), test.getResult()));
    }

    @PostMapping("/minimental")
    @Operation(summary = "Adiciona o teste Mini Exame do Estado Mental.", 
               description = "**Mini Exame do Estado Mental**\n\n" +
                       "**Normal**: Acima de 27 pontos.\n\n" +
                       "**Demência**: Menor ou igual a 24 pontos.\n\n" +
                       "*Em caso de menos de 4 anos de escolaridade, o ponto de corte passa para 17, em vez de 24.*\n\n" + "O exame é composto pelos seguintes tópicos:\n\n" +
                       "**1. Orientação Espacial (0-5 pontos)**\n" +
                       "Avalia a capacidade de orientação no espaço, incluindo o entendimento do ambiente e localização.\n\n" +
                       "**2. Orientação Espacial 2 (0-5 pontos)**\n" +
                       "Avalia aspectos adicionais da orientação espacial, complementando o primeiro item.\n\n" +
                       "**3. Repita as palavras (0-3 pontos)**\n" +
                       "Testa a capacidade de repetir palavras apresentadas pelo avaliador.\n\n" +
                       "**4. Cálculo (0-5 pontos)**\n" +
                       "Avalia a habilidade de realizar cálculos simples, como adição e subtração.\n\n" +
                       "**5. Memorização (0-3 pontos)**\n" +
                       "Testa a capacidade de recordar informações após um curto período de tempo.\n\n" +
                       "**6. Linguagem (0-2 pontos)**\n" +
                       "Avalia a capacidade de compreensão e expressão verbal.\n\n" +
                       "**7. Linguagem (1 ponto)**\n" +
                       "Questão específica para avaliar um aspecto da linguagem com um valor pontual.\n\n" +
                       "**8. Linguagem (0-3 pontos)**\n" +
                       "Outra questão relacionada à linguagem, com uma faixa de pontos.\n\n" +
                       "**9. Linguagem (1 ponto)**\n" +
                       "Questão adicional para avaliação de linguagem com um valor específico.\n\n" +
                       "**10. Linguagem (1 ponto)**\n" +
                       "Outra questão pontual relacionada à linguagem.\n\n" +
                       "**11. Linguagem (1 ponto)**\n" +
                       "Mais uma questão pontual para avaliar aspectos da linguagem.", 
               tags = {"Testes"}, 
               responses = {
                   @ApiResponse(description = "Success", responseCode = "201", 
                               content = @Content(schema = @Schema(implementation = TestDTO.class))),
                   @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
               })
    public ResponseEntity<TestDTO> createMiniMentalTest(@RequestBody MeemDTO meemDTO, Authentication authentication) {
        Test test = testService.createMiniMentalTest(meemDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TestDTO(test.getType(), test.getResult()));
    }

    @PostMapping("/cummings")
    @Operation(summary = "Adiciona o teste Inventário de Cummings.", 
               description = "Número 1 para “sim”,se o sintoma esteve presente nos últimos 30 dias.\r\n"
               		+ "        Caso contrário, assinale a resposta 0 para “não”. Resposta referente a quantidade de sintomas positivos.", 
               tags = {"Testes"}, 
               responses = {
                   @ApiResponse(description = "Success", responseCode = "201", 
                               content = @Content(schema = @Schema(implementation = TestDTO.class))),
                   @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                   @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                   @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
               })
    public ResponseEntity<TestDTO> createCummingsTest(@RequestBody CummingsDTO cummingsDTO, Authentication authentication) {
        Test test = testService.createCummingsTest(cummingsDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TestDTO(test.getType(), test.getResult()));
    }
    
    @GetMapping
    @Operation(summary = "Todos os testes.", description = "Todos os testes.", 
    tags = {"Testes"}, 
    responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TestDTO.class))
                                    )
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Page<TestDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(testService.findAll(pageable).map(TestDTO::new));
    }

    @PutMapping(value="/{id}")
    @Operation(summary = "Atualiza um teste.", 
    description = "Atualiza um teste.", 
    tags = {"Testes"}, 
    responses = {
            @ApiResponse(description = "Updated", responseCode = "200", 
                    content = @Content(schema = @Schema(implementation = Test.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Test> update(@PathVariable String id, @RequestBody TestDTO testDto) {
        var test = new Test();
        BeanUtils.copyProperties(testDto, test);
        return ResponseEntity.status(HttpStatus.OK).body(testService.update(id, test));
    }

    @DeleteMapping(value="/{id}")
    @Operation(summary = "Deleta um teste.", 
    description = "Deleta um teste.", 
    tags = {"Testes"}, 
    responses = {
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        testService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}