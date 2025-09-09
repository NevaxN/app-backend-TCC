package com.app.src.mappers;

import com.app.src.dto.TrabalhoEventoDTO;
import com.app.src.models.TrabalhoEvento;

public class TrabalhoEventoMapper {

    public static TrabalhoEventoDTO toDTO(TrabalhoEvento trabalhoEvento) {
        if (trabalhoEvento == null) {
            return null;
        }

        return new TrabalhoEventoDTO(
                trabalhoEvento.getSequenciaProducao(),
                trabalhoEvento.getPesquisador(),
                trabalhoEvento.getAutores(),
                trabalhoEvento.getAno(),
                trabalhoEvento.getDestaque(),
                trabalhoEvento.getId(),
                trabalhoEvento.getTitulo(),
                trabalhoEvento.getClassificacaoEvento(),
                trabalhoEvento.getNomeEvento(),
                trabalhoEvento.getCidadeEvento()
        );
    }

    public static TrabalhoEvento toEntity(TrabalhoEventoDTO dto) {
        if (dto == null) {
            return null;
        }

        TrabalhoEvento trabalhoEvento = new TrabalhoEvento();
        trabalhoEvento.setSequenciaProducao(dto.sequenciaProducao());
        trabalhoEvento.setPesquisador(dto.pesquisador());
        trabalhoEvento.setAutores(dto.autores());
        trabalhoEvento.setAno(dto.ano());
        trabalhoEvento.setDestaque(dto.destaque());
        trabalhoEvento.setId(dto.id());
        trabalhoEvento.setTitulo(dto.titulo());
        trabalhoEvento.setClassificacaoEvento(dto.classificacaoEvento());
        trabalhoEvento.setNomeEvento(dto.nomeEvento());
        trabalhoEvento.setCidadeEvento(dto.cidadeEvento());

        return trabalhoEvento;
    }
}
