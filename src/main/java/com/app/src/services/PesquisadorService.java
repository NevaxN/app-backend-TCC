package com.app.src.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    // NOVO: Buscar pesquisador por ID do usuário
    public PesquisadorDTO buscarPorUsuarioId(Integer usuarioId) {
        Pesquisador pesquisador = repository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado para o usuário: " + usuarioId));
        return mapper.toDTO(pesquisador);
    }

    @Override
    public PesquisadorDTO salvar(PesquisadorDTO pesquisadorDTO){
        Integer usuarioId = pesquisadorDTO.getUsuario().getId();

        // Tenta encontrar um pesquisador EXISTENTE para este usuário
        Optional<Pesquisador> pesquisadorExistenteOpt = repository.findByUsuarioId(usuarioId);

        Pesquisador pesquisadorParaSalvar;

        if (pesquisadorExistenteOpt.isPresent()) {
            pesquisadorParaSalvar = pesquisadorExistenteOpt.get();
            System.out.println("LOG: /salvarPesquisador - Encontrou ID " + pesquisadorParaSalvar.getId() + ". ATUALIZANDO.");

        } else {
            pesquisadorParaSalvar = new Pesquisador();
            System.out.println("LOG: /salvarPesquisador - Não encontrou. CRIANDO NOVO.");

            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));
            pesquisadorParaSalvar.setUsuario(usuario);
        }

        pesquisadorParaSalvar.setNomePesquisador(pesquisadorDTO.getNomePesquisador());
        pesquisadorParaSalvar.setSobrenome(pesquisadorDTO.getSobrenome());
        pesquisadorParaSalvar.setDataAtualizacao(pesquisadorDTO.getDataAtualizacao());
        pesquisadorParaSalvar.setHoraAtualizacao(pesquisadorDTO.getHoraAtualizacao());
        pesquisadorParaSalvar.setNacionalidade(pesquisadorDTO.getNacionalidade());
        pesquisadorParaSalvar.setPaisNascimento(pesquisadorDTO.getPaisNascimento());
        pesquisadorParaSalvar.setNomeCitacoesBibliograficas(pesquisadorDTO.getNomeCitacoesBibliograficas());
        pesquisadorParaSalvar.setLattesId(pesquisadorDTO.getLattesId());

        Pesquisador salvo = repository.save(pesquisadorParaSalvar);

        return mapper.toDTO(salvo);
    }

    // NOVO: Método específico para atualizar perfil (apenas campos editáveis)
    public PesquisadorDTO atualizarPerfil(Integer id, PesquisadorDTO dadosAtualizados) {
        Pesquisador existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado com id: " + id));

        // Atualiza apenas os campos permitidos para edição
        if (dadosAtualizados.getNomePesquisador() != null) {
            existente.setNomePesquisador(dadosAtualizados.getNomePesquisador());
        }
        if (dadosAtualizados.getSobrenome() != null) {
            existente.setSobrenome(dadosAtualizados.getSobrenome());
        }
        if (dadosAtualizados.getDataNascimento() != null) {
            existente.setDataNascimento(dadosAtualizados.getDataNascimento());
        }
        if (dadosAtualizados.getNacionalidade() != null) {
            existente.setNacionalidade(dadosAtualizados.getNacionalidade());
        }
        if (dadosAtualizados.getPaisNascimento() != null) {
            existente.setPaisNascimento(dadosAtualizados.getPaisNascimento());
        }
        if (dadosAtualizados.getNomeCitacoesBibliograficas() != null) {
            existente.setNomeCitacoesBibliograficas(dadosAtualizados.getNomeCitacoesBibliograficas());
        }

        // Atualiza data e hora de atualização
        existente.setDataAtualizacao(LocalDate.now());
        existente.setHoraAtualizacao(LocalTime.now());

        Pesquisador salvo = repository.save(existente);

        return mapper.toDTO(salvo);
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
    public PesquisadorDTO buscarPorIdComTratamento(Integer id) {
        try {
            Pesquisador pesquisador = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado com id: " + id));

            PesquisadorDTO dto = mapper.toDTO(pesquisador);

            // Garantir que os campos obrigatórios não sejam nulos
            if (dto.getNomePesquisador() == null) dto.setNomePesquisador("");
            if (dto.getSobrenome() == null) dto.setSobrenome("");
            if (dto.getPaisNascimento() == null) dto.setPaisNascimento("Não informado");

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pesquisador: " + e.getMessage(), e);
        }
    }
}
