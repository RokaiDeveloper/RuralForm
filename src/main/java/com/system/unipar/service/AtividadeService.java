package com.system.unipar.service;

import com.system.unipar.model.Atividade;
import com.system.unipar.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AtividadeService {

    private static final Logger logger = Logger.getLogger(AtividadeService.class.getName());

    @Autowired
    private AtividadeRepository atividadeRepository;

    public List<Atividade> findAll() {
        try {
            return atividadeRepository.findAll();
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar todas as atividades", e);
            return List.of();
        }
    }

    public Optional<Atividade> findById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido: " + id);
                return Optional.empty();
            }
            return atividadeRepository.findById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar atividade por ID: " + id, e);
            return Optional.empty();
        }
    }

    public Atividade save(Atividade atividade) {
        try {
            if (atividade == null) {
                logger.warning("Atividade nula fornecida para salvar");
                return null;
            }
            return atividadeRepository.save(atividade);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao salvar atividade", e);
            return null;
        }
    }

    public Atividade update(Long id, Atividade atividade) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para atualização: " + id);
                return null;
            }
            if (atividade == null) {
                logger.warning("Atividade nula fornecida para atualização");
                return null;
            }
            if (atividadeRepository.existsById(id)) {
                atividade.setId(id);
                return atividadeRepository.save(atividade);
            }
            logger.warning("Atividade não encontrada para atualização com ID: " + id);
            return null;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar atividade com ID: " + id, e);
            return null;
        }
    }

    public boolean deleteById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para exclusão: " + id);
                return false;
            }
            if (atividadeRepository.existsById(id)) {
                atividadeRepository.deleteById(id);
                return true;
            }
            logger.warning("Atividade não encontrada para exclusão com ID: " + id);
            return false;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao excluir atividade com ID: " + id, e);
            return false;
        }
    }

    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return atividadeRepository.existsById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência da atividade com ID: " + id, e);
            return false;
        }
    }
}
