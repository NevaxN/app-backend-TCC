package com.app.src.mappers;

/**
 * Interface genérica para mapeadores que convertem entre Entidade e DTO.
 * @param <E> A classe da Entidade
 * @param <D> A classe do DTO
 */
public interface GenericMapper<E, D> {
    D toDTO(E entity);

    E toEntity(D dto);
}
