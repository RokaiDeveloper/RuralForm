package com.system.unipar.repository;

import com.system.unipar.model.AtividadeRequisitoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRequisitoItemRepository extends JpaRepository<AtividadeRequisitoItem, Long> {
    
    List<AtividadeRequisitoItem> findByAtividadeRequisitoId(Long atividadeRequisitoId);
    
    List<AtividadeRequisitoItem> findByRelatorioId(Long relatorioId);
    
    List<AtividadeRequisitoItem> findByStatus(String status);
    
    @Query("SELECT ari FROM AtividadeRequisitoItem ari WHERE ari.atividadeRequisitoId = :atividadeRequisitoId AND ari.relatorioId = :relatorioId")
    List<AtividadeRequisitoItem> findByAtividadeRequisitoIdAndRelatorioId(@Param("atividadeRequisitoId") Long atividadeRequisitoId,
                                                                           @Param("relatorioId") Long relatorioId);
    
    @Query("SELECT ari FROM AtividadeRequisitoItem ari JOIN AtividadeRequisito ar ON ari.atividadeRequisitoId = ar.id WHERE ar.atividadeId = :atividadeId")
    List<AtividadeRequisitoItem> findByAtividadeId(@Param("atividadeId") Long atividadeId);
}
