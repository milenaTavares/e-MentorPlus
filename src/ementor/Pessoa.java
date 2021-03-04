package ementor;

import java.io.Serializable;

/**
A Classe mãe Pessoa deve possuir os atributos de Nome, Data de Nascimento, CPF e
Telefone. Em relação aos métodos, deve ser implementado o método construtor padrão e
método construtor para inicializar todos os atributos.
 */
public class Pessoa implements Serializable{
	//atributos
	protected String nome;
	protected String dataNascimento;
	protected long cpf;
	protected String contato;
		
	//Método construtor padrão
	public Pessoa () {
		this.nome = "";
		this.dataNascimento = "";
		this.cpf = 0;
		this.contato = "";
	}
	
	//Método construtor que inicializa todos os campos
	public Pessoa(String nome, String dataNascimento, long cpf, String telefone) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.contato = telefone;
	}
	
        //Método que modifica os atributos após instanciar os objetos
	public void setDados(String nome, String dataNascimento, long cpf, String telefone) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.contato = telefone;
	}
    
    //Métodos de acesso
    public String getNome() {
        return nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public long getCpf() {
        return cpf;
    }

    public String getContato() {
        return contato;
    }
        
        
}

