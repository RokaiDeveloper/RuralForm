package com.system.unipar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "atividade_requisito_item")
public class AtividadeRequisitoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "atividade_requisito_id")
    private Long atividadeRequisitoId;

    @Lob
    @Column(name = "documento", nullable = true)
    private byte[] documento;

    @Column(name = "data_documento", nullable = true)
    private Date dataDocumento;

    @Column(name = "checked", nullable = true, columnDefinition = "boolean default true")
    private boolean check;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "relatorio_id")
    private Long relatorioId;

    @Column(name = "observacao")
    private String observacao;
}
