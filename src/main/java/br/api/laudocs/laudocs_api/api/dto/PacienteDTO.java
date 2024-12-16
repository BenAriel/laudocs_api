package br.api.laudocs.laudocs_api.api.dto;

import java.time.LocalDate;

import br.api.laudocs.laudocs_api.domain.entities.Paciente;

public class PacienteDTO {

    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private LocalDate dataNasc;

    public PacienteDTO() {
    }

    public static PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCPF());
        dto.setIdade(paciente.getIdade());
        dto.setDataNasc(paciente.getDataNasc());
        return dto;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
}
