package com.app.src.services;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.app.src.models.*;
import com.app.src.repositories.*;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlService {

    @Autowired
    DetectEncodeService detectEncodeServiceService;

    @Autowired
    PesquisadorService pesquisadorService;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    FormacaoAcademicaService formacaoAcademicaService;

    @Autowired
    AtuacaoProfissionalService atuacaoProfissionalService;

    @Autowired
    ProducaoBibliograficaService producaoBibliograficaService;

    @Autowired
    OrientacaoService orientacaoService;

    @Autowired
    PremiacaoService premiacaoService;

    @Autowired
    IdiomaService idiomaService;

    final ObjectMapper mapper = new ObjectMapper();

    private final UsuarioRepository usuarioRepository;
    private final PesquisadorRepository pesquisadorRepository;
    private final EnderecoRepository enderecoRepository;
    private final IdiomaRepository idiomaRepository;
    private final PremiacaoRepository premiacaoRepository;
    private final FormacaoAcademicaRepository formacaoAcademicaRepository;
    private final OrientacaoRepository orientacaoRepository;


    public XmlService(ArtigoRepository artigoRepository, TrabalhoEventoRepository trabalhoEventoRepository, LivroRepository livroRepository, CapituloRepository capituloRepository, CapituloRepository capituloRepository1, UsuarioRepository usuarioRepository, PesquisadorRepository pesquisadorRepository, EnderecoRepository enderecoRepository, IdiomaRepository idiomaRepository, PremiacaoRepository premiacaoRepository, FormacaoAcademicaRepository formacaoAcademicaRepository, AtuacaoProfissionalRepository atuacaoProfissionalRepository, OrientacaoRepository orientacaoRepository, ProjetoPesquisaRepository projetoPesquisaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pesquisadorRepository = pesquisadorRepository;
        this.enderecoRepository = enderecoRepository;
        this.idiomaRepository = idiomaRepository;
        this.premiacaoRepository = premiacaoRepository;
        this.formacaoAcademicaRepository = formacaoAcademicaRepository;
        this.orientacaoRepository = orientacaoRepository;
    }

    public String processarXml(MultipartFile xml, Integer usuarioId) {
        try {
            byte[] conteudoBytes = xml.getBytes();
            String encoding = detectEncodeServiceService.detectEncoding(conteudoBytes);
            String conteudo = new String(conteudoBytes, Charset.forName(encoding));

            // XML -> JSON
            JSONObject jsonObject = XML.toJSONObject(conteudo);
            String jsonString = jsonObject.toString();

            // Envia JSON para Flask
            RestTemplate restTemplate = new RestTemplate();
            String flaskUrl = "http://keyword-extractor:5000/analyze";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("conteudo_xml", jsonString);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> flaskResponse = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

            String flaskJson = flaskResponse.getBody();

            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            
            Pesquisador pesquisador = pesquisadorService.converterJsonParaPesquisador(flaskJson, usuario);
            Pesquisador pesquisadorSalvo = pesquisadorRepository.save(pesquisador);

            enderecoRepository.saveAll(enderecoService.converterJsonParaEndereco(flaskJson, pesquisadorSalvo));
            
            formacaoAcademicaRepository.saveAll(formacaoAcademicaService.converterJsonParaFormacaoAcademica(flaskJson, pesquisadorSalvo));

            atuacaoProfissionalService.converterJsonParaAtuacaoProfissional(flaskJson, pesquisadorSalvo);

            producaoBibliograficaService.converterJsonParaProducaoBibliografica(flaskJson, pesquisadorSalvo);
            
            orientacaoRepository.saveAll(orientacaoService.converterJsonParaOrientacao(flaskJson, pesquisadorSalvo));
            
            premiacaoRepository.saveAll(premiacaoService.converterJsonParaPremiacao(flaskJson, pesquisadorSalvo));
            
            idiomaRepository.saveAll(idiomaService.converterJsonParaIdioma(flaskJson, pesquisadorSalvo));

            return flaskResponse.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar XML: " + e.getMessage(), e);
        }
    }    
}
