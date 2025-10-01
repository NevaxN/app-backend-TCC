package com.app.src.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.mappers.PesquisadorMapper;
import com.app.src.models.Pesquisador;
import com.app.src.models.Usuario;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PesquisadorService {

    final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PesquisadorDTO> buscarTodos(){
        return pesquisadorRepository.findAll().stream()
                .map(PesquisadorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "pesquisadores", key = "#id")
    public PesquisadorDTO buscarPorId(Integer id){
        Pesquisador pesquisador = pesquisadorRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));
        
        return PesquisadorMapper.toDTO(pesquisador);
    }

    public PesquisadorDTO salvar(PesquisadorDTO pesquisadorDTO){
        Pesquisador pesquisador = PesquisadorMapper.toEntity(pesquisadorDTO);

        if (pesquisador.getUsuario() == null || pesquisador.getUsuario().getId() == null) {
            throw new IllegalArgumentException("ID do usuario é obrigatório.");
        }

        if (!usuarioRepository.existsById(pesquisador.getUsuario().getId())) {
            throw new NoSuchElementException("Usuario não encontrado com id: " + pesquisador.getUsuario().getId());
        }
        
        Pesquisador salvo = pesquisadorRepository.save(pesquisador);

        return PesquisadorMapper.toDTO(salvo);
    }

    public PesquisadorDTO atualizar(Integer id, Pesquisador dadosAtualizados){

        PesquisadorDTO existenteDTO = buscarPorId(id);
        Pesquisador existente = PesquisadorMapper.toEntity(existenteDTO);

        existente.setNomePesquisador(dadosAtualizados.getNomePesquisador());
        existente.setSobrenome(dadosAtualizados.getSobrenome());
        existente.setDataNascimento(dadosAtualizados.getDataNascimento());
        existente.setNomeCitacoesBibliograficas(dadosAtualizados.getNomeCitacoesBibliograficas());
        existente.setDataAtualizacao(dadosAtualizados.getDataAtualizacao());
        existente.setHoraAtualizacao(dadosAtualizados.getHoraAtualizacao());
        existente.setNacionalidade(dadosAtualizados.getNacionalidade());
        existente.setPaisNascimento(dadosAtualizados.getPaisNascimento());
        existente.setLattesId(dadosAtualizados.getLattesId());
        existente.setUsuario(dadosAtualizados.getUsuario());

        Pesquisador salvo = pesquisadorRepository.save(existente);

        return PesquisadorMapper.toDTO(salvo);
    }

    public String deletar(Integer id){
        if (!pesquisadorRepository.existsById(id)) {
            return "Pesquisador com id: " + id + " não encontrado";
        }
        pesquisadorRepository.deleteById(id);

        return "Pesquisador com id: " + id + " deletado com sucesso";
    }

    public Pesquisador converterJsonParaPesquisador(String jsonBody, Usuario usuario) {
        try {

            JsonNode root = mapper.readTree(jsonBody);
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
