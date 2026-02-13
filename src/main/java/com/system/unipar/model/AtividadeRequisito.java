package com.system.unipar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "atividade_requisito")
public class AtividadeRequisito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "atividade_id")
    private Long atividadeId;

    @Column(name = "checkable")
    private boolean checkable;

    @Column(name = "data_documento")
    private boolean dataDocumento;

    @Column(name = "documento_upload")
    private boolean documentoUpload;

    @Column(name = "descricao_requisito")
    private String descricaoRequisito;
}
