
package ementor;

/**
A subclasse Usuário deve possuir os atributos: Nome do Usuario, Senha e Nivel de Acesso
(de 1 a 3 – para futuras implementações); com os métodos: construtor padrão, setDados,
getNomoUsuario; getSenha.
 */
public class Usuario extends Pessoa{
    //Atributos
    private String nomeUsuario;
    private int senha;
    private int nívelAcesso;

    //Contrutor Padrão
    public Usuario() {
        super();
        this.nomeUsuario = "";
        this.senha = 0;
        this.nívelAcesso = 0;
    }
    
    //Métodos Modificadores
    public void setDados(String nome, String dataNascimento, long cpf, String telefone) {
        super.setDados(nome, dataNascimento, cpf, telefone);
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.nívelAcesso = nívelAcesso;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }
    
    
    

    //Métodos Acessores
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public int getSenha() {
        return senha;
    }
    
    
}
