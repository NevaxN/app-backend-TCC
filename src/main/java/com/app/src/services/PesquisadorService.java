package com.app.src.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.mappers.PesquisadorMapper;
import com.app.src.models.Pesquisador;
import com.app.src.models.Usuario;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.TagRepository;
import com.app.src.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PesquisadorService extends GenericCrudService<Pesquisador, PesquisadorDTO, Integer, PesquisadorRepository> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TagRepository tagRepository;

    public PesquisadorService(PesquisadorRepository repository, PesquisadorMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "pesquisadores", key = "#id")
    public PesquisadorDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public PesquisadorDTO salvar(PesquisadorDTO pesquisadorDTO){
        Pesquisador pesquisador = mapper.toEntity(pesquisadorDTO);

        if (pesquisador.getUsuario() == null || pesquisador.getUsuario().getId() == null) {
            throw new IllegalArgumentException("ID do usuario é obrigatório.");
        }

        if (!usuarioRepository.existsById(pesquisador.getUsuario().getId())) {
            throw new NoSuchElementException("Usuario não encontrado com id: " + pesquisador.getUsuario().getId());
        }

        return super.salvar(pesquisadorDTO);
    }

    public PesquisadorDTO atualizar(Integer id, PesquisadorDTO dadosAtualizados){

        Pesquisador existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado com id: " + id));

        ((PesquisadorMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Pesquisador salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }

    public List<Pesquisador> buscarPesquisadoresPorTag(String nomeTag){
        return tagRepository.findPesquisadoresByTagName(nomeTag);
    }

    public Pesquisador converterJsonParaPesquisador(String jsonBody, Usuario usuario) {
        try {

            JsonNode root = objectMapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador");

            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HHmmss");

            Pesquisador pesquisador = new Pesquisador();
            pesquisador.setUsuario(usuario);
            pesquisador.setLattesId(dados.get("numero_identificador").asLong());

            pesquisador.setDataNascimento(LocalDate.parse(dados.get("data_nascimento").asText(), dataFormatter));
            pesquisador.setDataAtualizacao(LocalDate.parse(dados.get("data_atualizacao").asText(), dataFormatter));
            pesquisador.setHoraAtualizacao(LocalTime.parse(dados.get("hora_atualizacao").asText(), horaFormatter));

            pesquisador.setNomeCitacoesBibliograficas(dados.get("citacoes_bibliograficas").asText());
            pesquisador.setNacionalidade(dados.get("nacionalidade").asText());
            pesquisador.setPaisNascimento(dados.get("pais_nascimento").asText());

            String[] nomePartes = dados.get("nome_completo").asText().split(" ", 2);
            pesquisador.setNomePesquisador(nomePartes[0]);
            pesquisador.setSobrenome(nomePartes.length > 1 ? nomePartes[1] : "");

            return pesquisador;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Pesquisador", e);
        }
    }
}
