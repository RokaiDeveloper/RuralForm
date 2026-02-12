package com.system.unipar.service;

import com.system.unipar.model.AtividadeRequisito;
import com.system.unipar.model.AtividadeRequisitoItem;
import com.system.unipar.repository.AtividadeRequisitoRepository;
import com.system.unipar.repository.AtividadeRequisitoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AtividadeRequisitoService {

    private static final Logger logger = Logger.getLogger(AtividadeRequisitoService.class.getName());

    @Autowired
    private AtividadeRequisitoRepository atividadeRequisitoRepository;

    @Autowired
    private AtividadeRequisitoItemRepository atividadeRequisitoItemRepository;

    public List<AtividadeRequisito> findAll() {
        try {
            return atividadeRequisitoRepository.findAll();
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar todas as atividades requisitos", e);
            return List.of();
        }
    }

    public Optional<AtividadeRequisito> findById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido: " + id);
                return Optional.empty();
            }
            return atividadeRequisitoRepository.findById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar atividade requisito por ID: " + id, e);
            return Optional.empty();
        }
    }

    public AtividadeRequisito save(AtividadeRequisito atividadeRequisito) {
        try {
            if (atividadeRequisito == null) {
                logger.warning("Atividade Requisito nula fornecida para salvar");
                return null;
            }
            return atividadeRequisitoRepository.save(atividadeRequisito);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao salvar atividade requisito", e);
            return null;
        }
    }

    public AtividadeRequisito update(Long id, AtividadeRequisito atividadeRequisito) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para atualização: " + id);
                return null;
            }
            if (atividadeRequisito == null) {
                logger.warning("Atividade Requisito nula fornecida para atualização");
                return null;
            }
            if (atividadeRequisitoRepository.existsById(id)) {
                atividadeRequisito.setId(id);
                return atividadeRequisitoRepository.save(atividadeRequisito);
            }
            logger.warning("Atividade Requisito não encontrada para atualização com ID: " + id);
            return null;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar atividade requisito com ID: " + id, e);
            return null;
        }
    }

    public boolean deleteById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para exclusão: " + id);
                return false;
            }
            List<AtividadeRequisitoItem> itens = atividadeRequisitoItemRepository.findByAtividadeRequisitoId(id);
            if (!itens.isEmpty()) {
                logger.warning("Não é possível excluir atividade requisito pois existem itens associados: " + id);
                return false;
            }
            if (atividadeRequisitoRepository.existsById(id)) {
                atividadeRequisitoRepository.deleteById(id);
                return true;
            }
            logger.warning("Atividade Requisito não encontrada para exclusão com ID: " + id);
            return false;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao excluir atividade requisito com ID: " + id, e);
            return false;
        }
    }

    public List<AtividadeRequisito> findByAtividadeId(Long atividadeId) {
        try {
            if (atividadeId == null || atividadeId <= 0) {
                logger.warning("ID de atividade inválido fornecido: " + atividadeId);
                return List.of();
            }
            return atividadeRequisitoRepository.findByAtividadeId(atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar atividades requisitos por atividade ID: " + atividadeId, e);
            return List.of();
        }
    }

    public List<AtividadeRequisito> findByAtividadeIdAndCheckableTrue(Long atividadeId) {
        try {
            if (atividadeId == null || atividadeId <= 0) {
                logger.warning("ID de atividade inválido fornecido: " + atividadeId);
                return List.of();
            }
            return atividadeRequisitoRepository.findByAtividadeIdAndCheckableTrue(atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar atividades requisitos checkable por atividade ID: " + atividadeId, e);
            return List.of();
        }
    }

    public List<AtividadeRequisito> findByAtividadeIdAndDataDocumentoTrue(Long atividadeId) {
        try {
            if (atividadeId == null || atividadeId <= 0) {
                logger.warning("ID de atividade inválido fornecido: " + atividadeId);
                return List.of();
            }
            return atividadeRequisitoRepository.findByAtividadeIdAndDataDocumentoTrue(atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar atividades requisitos com data documento por atividade ID: " + atividadeId, e);
            return List.of();
        }
    }

    public List<AtividadeRequisito> findByAtividadeIdAndDocumentoUploadTrue(Long atividadeId) {
        try {
            if (atividadeId == null || atividadeId <= 0) {
                logger.warning("ID de atividade inválido fornecido: " + atividadeId);
                return List.of();
            }
            return atividadeRequisitoRepository.findByAtividadeIdAndDocumentoUploadTrue(atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar atividades requisitos com documento upload por atividade ID: " + atividadeId, e);
            return List.of();
        }
    }

    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return atividadeRequisitoRepository.existsById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência da atividade requisito com ID: " + id, e);
            return false;
        }
    }
}
