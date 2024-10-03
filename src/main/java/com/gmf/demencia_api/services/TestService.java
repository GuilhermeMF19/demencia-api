package com.gmf.demencia_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmf.demencia_api.domain.test.Test;
import com.gmf.demencia_api.domain.test.dtos.CummingsDTO;
import com.gmf.demencia_api.domain.test.dtos.KatzDTO;
import com.gmf.demencia_api.domain.test.dtos.LawtonDTO;
import com.gmf.demencia_api.domain.test.dtos.MeemDTO;
import com.gmf.demencia_api.domain.test.enums.TestType;
import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.exceptions.UserNotFoundException;
import com.gmf.demencia_api.repositories.TestRepository;
import com.gmf.demencia_api.repositories.UserRepository;
import com.gmf.demencia_api.repositories.MedicalAppointmentRepository;

import jakarta.transaction.Transactional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalAppointmentRepository medicalAppointmentRepository;

    public Page<Test> findAll(Pageable pageable) {
        return testRepository.findAll(pageable);
    }

    public Test findById(String id) {
        Optional<Test> obj = testRepository.findById(id);
        return obj.orElseThrow(() -> new NotFoundException(id));
    }
  
    @Transactional
    public Test create(Test obj, Authentication authentication) {
        String login = authentication.getName();
        User createdBy = (User) userRepository.findByLogin(login);

        if (createdBy == null) {
            throw new UserNotFoundException(login);
        }

        MedicalAppointment medicalAppointment = medicalAppointmentRepository
                .findById(obj.getMedicalAppointment().getId())
                .orElseThrow(() -> new NotFoundException("MedicalAppointment not found"));

        Test test = new Test(obj.getType(), obj.getResult(), medicalAppointment);
        return testRepository.save(test);
    }

    @Transactional
    public Test createKatzTest(KatzDTO katzDTO, Authentication authentication) {
        int score = calculateKatzScore(katzDTO);
        Test test = new Test();
        
        MedicalAppointment medicalAppointment = medicalAppointmentRepository
        	    .findById(katzDTO.getMedicalAppointmentId())
        	    .orElseThrow(() -> new NotFoundException("MedicalAppointment not found"));
        
        test.setType(TestType.ESCALA_DE_KATZ);
        test.setResult("Score: " + score);
        test.setMedicalAppointment(medicalAppointment);
        return create(test, authentication);
    }

    @Transactional
    public Test createLawtonTest(LawtonDTO lawtonDTO, Authentication authentication) {
        int score = calculateLawtonScore(lawtonDTO);
        Test test = new Test();
        
        MedicalAppointment medicalAppointment = medicalAppointmentRepository
        	    .findById(lawtonDTO.getMedicalAppointmentId())
        	    .orElseThrow(() -> new NotFoundException("MedicalAppointment not found"));
        
        test.setType(TestType.ESCALA_DE_LAWTON);
        test.setResult("Score: " + score);
        test.setMedicalAppointment(medicalAppointment);
        return create(test, authentication);
    }

    @Transactional
    public Test createMiniMentalTest(MeemDTO miniMentalDTO, Authentication authentication) {
        int score = calculateMiniMentalScore(miniMentalDTO);
        Test test = new Test();
        
        MedicalAppointment medicalAppointment = medicalAppointmentRepository
        	    .findById(miniMentalDTO.getMedicalAppointmentId())
        	    .orElseThrow(() -> new NotFoundException("MedicalAppointment not found"));
        
        test.setType(TestType.MINI_EXAME_DO_ESTADO_MENTAL);
        test.setResult("Score: " + score);
        test.setMedicalAppointment(medicalAppointment);
        return create(test, authentication);
    }

    @Transactional
    public Test createCummingsTest(CummingsDTO cummingsDTO, Authentication authentication) {
        int score = calculateCummingsScore(cummingsDTO);
        Test test = new Test();
        
        MedicalAppointment medicalAppointment = medicalAppointmentRepository
        	    .findById(cummingsDTO.getMedicalAppointmentId())
        	    .orElseThrow(() -> new NotFoundException("MedicalAppointment not found"));
        
        test.setType(TestType.INVENTARIO_DE_CUMMINGS);
        test.setResult("Score: " + score);
        test.setMedicalAppointment(medicalAppointment);
        return create(test, authentication);
    }

    @Transactional
    public void deleteById(String id) {
        if (!testRepository.existsById(id)) {
            throw new NotFoundException(id);
        }

        try {
            testRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id);
        }
    }

    @Transactional
    public Test update(String id, Test obj) {
        Optional<Test> optionalEntity = testRepository.findById(id);
        Test entity = optionalEntity.orElseThrow(() -> new NotFoundException(id));

        updateData(entity, obj);
        return testRepository.save(entity);
    }

    private void updateData(Test entity, Test obj) {
        if (obj.getType() != null) {
            entity.setType(obj.getType());
        }
        if (obj.getResult() != null) {
            entity.setResult(obj.getResult());
        }
        if (obj.getMedicalAppointment() != null) {
            entity.setMedicalAppointment(obj.getMedicalAppointment());
        }
    }
    
    private int calculateKatzScore(KatzDTO katzDTO) {
        return katzDTO.getBanho() + katzDTO.getVestir() + katzDTO.getUsarBanheiro() +
               katzDTO.getMobilidade() + katzDTO.getContinencia() + katzDTO.getAlimentacao();
    }

    private int calculateLawtonScore(LawtonDTO lawtonDTO) {
        return lawtonDTO.getUsarTelefone() + lawtonDTO.getFazerCompras() + lawtonDTO.getPrepararRefeicoes() +
               lawtonDTO.getCuidarDaCasa() + lawtonDTO.getLavarRoupas() + lawtonDTO.getUsarTransporte() +
               lawtonDTO.getAdministrarMedicamentos() + lawtonDTO.getGerenciarFinancas();
    }

    private int calculateMiniMentalScore(MeemDTO meemDTO) {
        return meemDTO.getOrientacaoEspacial() + 
               meemDTO.getOrientacaoEspacial2() +
               meemDTO.getRepeticaoPalavras() + 
               meemDTO.getCalculo() + 
               meemDTO.getMemorizacao() +
               meemDTO.getLinguagem1() + 
               meemDTO.getLinguagem2() + 
               meemDTO.getLinguagem3() + 
               meemDTO.getLinguagem4() + 
               meemDTO.getLinguagem5() + 
               meemDTO.getLinguagem6();  
    }


    private int calculateCummingsScore(CummingsDTO cummingsDTO) {
        return cummingsDTO.getDelirios() + cummingsDTO.getAlucinacoes() + cummingsDTO.getAgitacaoAgressao() +
               cummingsDTO.getDepressaoDisforia() + cummingsDTO.getAnsiedade() + cummingsDTO.getEuforia() +
               cummingsDTO.getApatiaIndiferenca() + cummingsDTO.getDesinibicao() + 
               cummingsDTO.getIrritabilidadeLabilidade() + cummingsDTO.getComportamentoMotorAberrante() +
               cummingsDTO.getDisturbiosSonoVigilia() + cummingsDTO.getDisturbiosApetiteAlimentacao();
    }
}
