package com.system.unipar.controller;

import com.system.unipar.dto.AtividadeDTO;
import com.system.unipar.model.Atividade;
import com.system.unipar.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public AtividadeDTO save(@RequestBody AtividadeDTO atividadeDTO) {
        if (atividadeDTO == null) {
            throw new IllegalArgumentException("AtividadeDTO cannot be null");
        }
        try {
            Atividade atividade = new Atividade();
            atividade.setNome(atividadeDTO.getNome());
            atividade.setDescricao(atividadeDTO.getDescricao());
            
            Atividade savedAtividade = atividadeService.save(atividade);
            
            return AtividadeDTO.builder()
                    .id(savedAtividade.getId())
                    .nome(savedAtividade.getNome())
                    .descricao(savedAtividade.getDescricao())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save atividade", e);
        }
    }

    @GetMapping
    public List<Atividade> findAll() {
        try {
            return atividadeService.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all atividades", e);
        }
    }
}

