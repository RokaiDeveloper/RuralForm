package com.system.unipar.service;

import com.system.unipar.model.Relatorio;
import com.system.unipar.model.AtividadeRequisitoItem;
import com.system.unipar.model.AtividadeRequisito;
import com.system.unipar.dto.RelatorioCompiladoDTO;
import com.system.unipar.repository.RelatorioRepository;
import com.system.unipar.repository.AtividadeRequisitoItemRepository;
import com.system.unipar.repository.AtividadeRequisitoRepository;
import com.system.unipar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RelatorioService {

    private static final Logger logger = Logger.getLogger(RelatorioService.class.getName());

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private AtividadeRequisitoItemRepository atividadeRequisitoItemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtividadeRequisitoRepository atividadeRequisitoRepository;

    public List<Relatorio> findAll() {
        try {
            return relatorioRepository.findAll();
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar todos os relatórios", e);
            return List.of();
        }
    }

    public Optional<Relatorio> findById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido: " + id);
                return Optional.empty();
            }
            return relatorioRepository.findById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar relatório por ID: " + id, e);
            return Optional.empty();
        }
    }

    public Relatorio save(Relatorio relatorio) {
        try {
            if (relatorio == null) {
                logger.warning("Relatório nulo fornecido para salvar");
                return null;
            }
            if (!usuarioRepository.existsById(relatorio.getUsuarioId())) {
                logger.warning("Usuário não encontrado com ID: " + relatorio.getUsuarioId());
                return null;
            }
            return relatorioRepository.save(relatorio);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao salvar relatório", e);
            return null;
        }
    }

    public Relatorio update(Long id, Relatorio relatorio) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para atualização: " + id);
                return null;
            }
            if (relatorio == null) {
                logger.warning("Relatório nulo fornecido para atualização");
                return null;
            }
            if (!usuarioRepository.existsById(relatorio.getUsuarioId())) {
                logger.warning("Usuário não encontrado com ID: " + relatorio.getUsuarioId());
                return null;
            }
            if (relatorioRepository.existsById(id)) {
                relatorio.setId(id);
                return relatorioRepository.save(relatorio);
            }
            logger.warning("Relatório não encontrado para atualização com ID: " + id);
            return null;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar relatório com ID: " + id, e);
            return null;
        }
    }

    public boolean deleteById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warning("ID inválido fornecido para exclusão: " + id);
                return false;
            }
            List<AtividadeRequisitoItem> itens = atividadeRequisitoItemRepository.findByRelatorioId(id);
            if (!itens.isEmpty()) {
                logger.warning("Não é possível excluir relatório pois existem itens associados: " + id);
                return false;
            }
            if (relatorioRepository.existsById(id)) {
                relatorioRepository.deleteById(id);
                return true;
            }
            logger.warning("Relatório não encontrado para exclusão com ID: " + id);
            return false;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao excluir relatório com ID: " + id, e);
            return false;
        }
    }

    public List<Relatorio> findByUsuarioId(Long usuarioId) {
        try {
            if (usuarioId == null || usuarioId <= 0) {
                logger.warning("ID de usuário inválido fornecido: " + usuarioId);
                return List.of();
            }
            return relatorioRepository.findByUsuarioId(usuarioId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar relatórios por usuário ID: " + usuarioId, e);
            return List.of();
        }
    }

    public List<Relatorio> findByAtividadeId(Long atividadeId) {
        try {
            if (atividadeId == null || atividadeId <= 0) {
                logger.warning("ID de atividade inválido fornecido: " + atividadeId);
                return List.of();
            }
            return relatorioRepository.findByAtividadeId(atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar relatórios por atividade ID: " + atividadeId, e);
            return List.of();
        }
    }

    public List<Relatorio> findByUsuarioIdAndAtividadeId(Long usuarioId, Long atividadeId) {
        try {
            if (usuarioId == null || usuarioId <= 0 || atividadeId == null || atividadeId <= 0) {
                logger.warning("IDs inválidos fornecidos - usuário: " + usuarioId + ", atividade: " + atividadeId);
                return List.of();
            }
            return relatorioRepository.findByUsuarioIdAndAtividadeId(usuarioId, atividadeId);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao buscar relatórios por usuário ID e atividade ID: " + usuarioId + ", " + atividadeId, e);
            return List.of();
        }
    }

    public Relatorio createWithItems(Relatorio relatorio) {
        try {
            if (relatorio == null) {
                logger.warning("Relatório nulo fornecido para salvar");
                return null;
            }
            if (!usuarioRepository.existsById(relatorio.getUsuarioId())) {
                logger.warning("Usuário não encontrado com ID: " + relatorio.getUsuarioId());
                return null;
            }
            
            Relatorio savedRelatorio = relatorioRepository.save(relatorio);
            
            List<AtividadeRequisito> requisitos = atividadeRequisitoRepository.findByAtividadeId(relatorio.getAtividadeId());
            
            for (AtividadeRequisito requisito : requisitos) {
                AtividadeRequisitoItem item = new AtividadeRequisitoItem();
                item.setAtividadeRequisitoId(requisito.getId());
                item.setRelatorioId(savedRelatorio.getId());
                item.setCheck(true);
                item.setAprovado(false);
                
                // Regra 1: Se documentoUpload = true → status = PENDENTE_VALIDACAO
                if (requisito.isDocumentoUpload()) {
                    item.setStatus("PENDENTE_VALIDACAO");
                } else {
                    item.setStatus("PENDENTE_VALIDACAO");
                }
                
                atividadeRequisitoItemRepository.save(item);
            }
            
            return savedRelatorio;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao salvar relatório com itens", e);
            return null;
        }
    }

    public RelatorioCompiladoDTO compilarRelatorio(Long relatorioId) {
        try {
            if (relatorioId == null || relatorioId <= 0) {
                logger.warning("ID de relatório inválido fornecido: " + relatorioId);
                return null;
            }
            
            Optional<Relatorio> relatorioOpt = relatorioRepository.findById(relatorioId);
            if (relatorioOpt.isEmpty()) {
                logger.warning("Relatório não encontrado com ID: " + relatorioId);
                return null;
            }
            
            Relatorio relatorio = relatorioOpt.get();
            List<AtividadeRequisitoItem> itens = atividadeRequisitoItemRepository.findByRelatorioId(relatorioId);
            List<String> pendencias = new ArrayList<>();
            boolean compilado = true;
            
            Date dataAtual = new Date();
            
            for (AtividadeRequisitoItem item : itens) {
                Optional<AtividadeRequisito> requisitoOpt = atividadeRequisitoRepository.findById(item.getAtividadeRequisitoId());
                if (requisitoOpt.isEmpty()) {
                    continue;
                }
                
                AtividadeRequisito requisito = requisitoOpt.get();
                
                // Regra 2: Validar data de documento
                if (requisito.isDataDocumento() && item.getDataDocumento() != null) {
                    if (item.getDataDocumento().before(dataAtual)) {
                        item.setStatus("VENCIDO");
                        item.setAprovado(false);
                        atividadeRequisitoItemRepository.save(item);
                    }
                }
                
                // Regra 3: Validar itens obrigatórios
                if (requisito.isObrigatorio()) {
                    if (item.getStatus() == null || 
                        item.getStatus().equals("PENDENTE_VALIDACAO") ||
                        item.getStatus().equals("VENCIDO")) {
                        pendencias.add("Item obrigatório não validado: " + requisito.getId());
                        compilado = false;
                    }
                }
            }
            
            RelatorioCompiladoDTO dto = new RelatorioCompiladoDTO();
            dto.setId(relatorio.getId());
            dto.setUsuarioId(relatorio.getUsuarioId());
            dto.setAtividadeId(relatorio.getAtividadeId());
            dto.setItens(itens);
            dto.setCompilado(compilado);
            dto.setPendencias(pendencias);
            
            return dto;
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao compilar relatório ID: " + relatorioId, e);
            return null;
        }
    }

    public boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                return false;
            }
            return relatorioRepository.existsById(id);
        } catch (DataAccessException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência do relatório com ID: " + id, e);
            return false;
        }
    }
}
