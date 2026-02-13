package com.system.unipar.service;

import com.system.unipar.model.AtividadeRequisitoItem;
import com.system.unipar.repository.AtividadeRequisitoItemRepository;
import com.system.unipar.repository.AtividadeRequisitoRepository;
import com.system.unipar.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AtividadeRequisitoItemService {

    private static final Logger logger = Logger.getLogger(AtividadeRequisitoItemService.class.getName());

    @Autowired
    private AtividadeRequisitoItemRepository atividadeRequisitoItemRepository;

    @Autowired
    private AtividadeRequisitoRepository atividadeRequisitoRepository;

    @Autowired
    private RelatorioRepository relatorioRepository;

    public List<AtividadeRequisitoItem> findAll() {
        try {
            return atividadeRequisitoItemRepository.findAll();
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar todos os itens de atividades requisitos", e);
            return List.of();
        }
    }

    public Optional<AtividadeRequisitoItem> findById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido: " + id);
                return Optional.empty();
            }
            return atividadeRequisitoItemRepository.findById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar item de atividade requisito por ID: " + id, e);
            return Optional.empty();
        }
    }

    public AtividadeRequisitoItem save(AtividadeRequisitoItem item) {
        try {
            if (item == null) {
                logger.warning("Item de atividade requisito nulo fornecido para salvar");
                return null;
            }
            if (!validateRelationships(item)) {
                return null;
            }
            return atividadeRequisitoItemRepository.save(item);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao salvar item de atividade requisito", e);
            return null;
        }
    }

    public AtividadeRequisitoItem update(Long id, AtividadeRequisitoItem item) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para atualização: " + id);
                return null;
            }
            if (item == null) {
                logger.warning("Item de atividade requisito nulo fornecido para atualização");
                return null;
            }
            if (!validateRelationships(item)) {
                return null;
            }
            if (atividadeRequisitoItemRepository.existsById(id)) {
                item.setId(id);
                return atividadeRequisitoItemRepository.save(item);
            }
            logger.warning("Item de atividade requisito não encontrado para atualização com ID: " + id);
            return null;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar item de atividade requisito com ID: " + id, e);
            return null;
        }
    }

    public boolean deleteById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para exclusão: " + id);
                return false;
            }
            if (atividadeRequisitoItemRepository.existsById(id)) {
                atividadeRequisitoItemRepository.deleteById(id);
                return true;
            }
            logger.warning("Item de atividade requisito não encontrado para exclusão com ID: " + id);
            return false;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao excluir item de atividade requisito com ID: " + id, e);
            return false;
        }
    }

    public List<AtividadeRequisitoItem> findByAtividadeRequisitoId(Long atividadeRequisitoId) {
        try {
            if (atividadeRequisitoId == null || atividadeRequisitoId <= 0) {
                logger.warning("ID de atividade requisito inválido fornecido: " + atividadeRequisitoId);
                return List.of();
            }
            return atividadeRequisitoItemRepository.findByAtividadeRequisitoId(atividadeRequisitoId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar itens por atividade requisito ID: " + atividadeRequisitoId, e);
            return List.of();
        }
    }

    public List<AtividadeRequisitoItem> findByRelatorioId(Long relatorioId) {
        try {
            if (relatorioId == null || relatorioId <= 0) {
                logger.warning("ID de relatório inválido fornecido: " + relatorioId);
                return List.of();
            }
            return atividadeRequisitoItemRepository.findByRelatorioId(relatorioId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar itens por relatório ID: " + relatorioId, e);
            return List.of();
        }
    }

    public List<AtividadeRequisitoItem> findByStatus(String status) {
        try {
            if (status == null || status.trim().isEmpty()) {
                logger.warning("Status inválido fornecido: " + status);
                return List.of();
            }
            return atividadeRequisitoItemRepository.findByStatus(status);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar itens por status: " + status, e);
            return List.of();
        }
    }

    public List<AtividadeRequisitoItem> findByAtividadeRequisitoIdAndRelatorioId(Long atividadeRequisitoId, Long relatorioId) {
        try {
            if (atividadeRequisitoId == null || atividadeRequisitoId <= 0 || relatorioId == null || relatorioId <= 0) {
                logger.warning("IDs inválidos fornecidos - atividade requisito: " + atividadeRequisitoId + ", relatório: " + relatorioId);
                return List.of();
            }
            return atividadeRequisitoItemRepository.findByAtividadeRequisitoIdAndRelatorioId(atividadeRequisitoId, relatorioId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar itens por atividade requisito ID e relatório ID: " + atividadeRequisitoId + ", " + relatorioId, e);
            return List.of();
        }
    }

    public List<AtividadeRequisitoItem> findByAtividadeId(Long atividadeId) {
        try {
            if (atividadeId == null || atividadeId <= 0) {
                logger.warning("ID de atividade inválido fornecido: " + atividadeId);
                return List.of();
            }
            return atividadeRequisitoItemRepository.findByAtividadeId(atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar itens por atividade ID: " + atividadeId, e);
            return List.of();
        }
    }

    private boolean validateRelationships(AtividadeRequisitoItem item) {
        try {
            if (item.getAtividadeRequisitoId() != null && 
                !atividadeRequisitoRepository.existsById(item.getAtividadeRequisitoId())) {
                logger.warning("Atividade Requisito não encontrado com ID: " + item.getAtividadeRequisitoId());
                return false;
            }

            if (item.getRelatorioId() != null && 
                !relatorioRepository.existsById(item.getRelatorioId())) {
                logger.warning("Relatório não encontrado com ID: " + item.getRelatorioId());
                return false;
            }
            return true;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao validar relacionamentos do item", e);
            return false;
        }
    }

    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return atividadeRequisitoItemRepository.existsById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência do item de atividade requisito com ID: " + id, e);
            return false;
        }
    }
}
