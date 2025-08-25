package com.app.src.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empresas")
public class Empresa {
           
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nomeRegistro", nullable = false)
    private String nomeRegistro;

    @Column(name = "nomeComercial", nullable = false)
    private String nomeComercial;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "numeroEndereco", nullable = false)
    private String numeroEndereco;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "site", nullable = false)
    private String site;

    @Column(name = "setor", nullable = false)
    private String setor;

    @Column(name = "frase", nullable = false)
    private String frase;

    @Column(name = "textoEmpresa", nullable = false)
    private String textoEmpresa;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    public Empresa() {}

    public Empresa(String nomeRegistro, String nomeComercial, String cnpj,
    String numeroEndereco, String bairro, String cidade, String estado, String cep, 
    String telefone, String email, String site, String setor, String frase, String textoEmpresa, String logradouro){
        this.nomeRegistro = nomeRegistro;
        this.nomeComercial = nomeComercial;
        this.cnpj = cnpj;
        this.numeroEndereco = numeroEndereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.telefone = telefone;
        this.email = email;
        this.site = site;
        this.setor = setor;
        this.frase = frase;
        this.textoEmpresa = textoEmpresa;
        this.logradouro = logradouro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeRegistro() {
        return nomeRegistro;
    }

    public void setNomeRegistro(String nomeRegistro) {
        this.nomeRegistro = nomeRegistro;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getTextoEmpresa() {
        return textoEmpresa;
    }

    public void setTextoEmpresa(String textoEmpresa) {
        this.textoEmpresa = textoEmpresa;
    }

    public String getLogradouro(){
        return logradouro;
    }

    public void setLogradouro(String logradouro){
        this.logradouro = logradouro;
    }
}
