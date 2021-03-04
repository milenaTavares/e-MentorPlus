package ementor;

import java.io.Serializable;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/* A Subclasse Aluno, filha de Pessoa, deve conter os atributos de Matrícula e Período e os métodos: Construtor padrão;
construtor para inicializar todos os atributos; setDados para inicializar todos os atributos após a criação dos objetos; 
getMatricula e getPeriodo para recuperar esses atributos; e outro método para imprimir os dados (preferencialmente utilizar 
mensagens gráficas de dialogo). */

public class Aluno extends Pessoa implements Serializable {
    //atributos
    private long matricula;
    private int periodo;

    //Método construtor padrão
    public Aluno() {
            super();
            this.matricula = 0;
            this.periodo = 0;
    }

    //Método construtor que inicializa todos os dados
    public Aluno(String nome, String dataNascimento, long cpf, String telefone, long matricula, int periodo) {
            super(nome, dataNascimento, cpf, telefone);
            this.matricula = matricula;
            this.periodo = periodo;
    }

    //Métodos que modificam os atributos após instanciar os objetos
    public void setDados (String nome, String dataNascimento, long cpf, String telefone, long matricula, int periodo) {
            super.setDados(nome, dataNascimento, cpf, telefone);
            this.matricula = matricula;
            this.periodo = periodo;
    }

    public void setMatricula(long matricula){
        this.matricula=matricula;
    }

    public void setPeriodo(int periodo){
        this.periodo=periodo;
    }

    //Métodos de acesso
    public long getMatricula() {
            return matricula;
    }
    public int getPeriodo() {
            return periodo;
    }

    //Método que imprime todos os dados desta classe
    public void imprimirDados() {
        JOptionPane.showMessageDialog(null,"Nome: " + this.nome + "\nData de Nascimento: " + 
                                     this.dataNascimento + "\nCPF: " + this.cpf + "\nContato: " + this.contato + 
                                    "\nMatrícula: " + this.matricula + "\nPeríodo: " + this.periodo,"Aluno Cadastrado!", INFORMATION_MESSAGE );
    }
}
