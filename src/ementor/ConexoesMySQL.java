package ementor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;



public class ConexoesMySQL {

    private String caminho = "localhost";
    private String porta = "3306";
    private String nome = "ementorPlus";
    private String usuario = "root";
    private String senha = "admin";
    private String FusoHorario = "?useTimezone=true&serverTimezone=UTC";
              
    private String URL ="jdbc:mysql://"+caminho+":"+porta+"/"+nome+FusoHorario;     
    
    //Conexão com o Banco de Dados
    public Connection realizaConexaoMySQL(){
        try{
            return DriverManager.getConnection(URL,usuario,senha); //Estabelece a conexão via conector j
            
        }
        catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          return null;
        }        
    }
    
    //Desconecta o Banco de Dados
    public void desconectaMySQL(Connection conexao){
        try{
           if (conexao != null) 
             conexao.close();
            
        }
        catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }   
    }
    
    //Inserir novo aluno no Banco de Dados 
    public void insereAlunoNoMySQL(String nome, long CPF, String contato,String Data, long matricula, int periodo){
        Connection conexao = realizaConexaoMySQL();
        String sql_pessoa = "insert into pessoa (CPF, Nome, Telefone, DataNascimento) values (?,?,?,?)";
        String sql_aluno = "insert into aluno (Matricula,CPF_Pessoa,Periodo) values (?,?,?)";
        
        //inserção dos daddo nas Tabelas do MySQL
        try{
            PreparedStatement Atuador_pessoa = conexao.prepareStatement(sql_pessoa);
            PreparedStatement Atuador_aluno = conexao.prepareStatement(sql_aluno);
            
            //Seção para setar os campos no atuador 
            Atuador_pessoa.setLong(1, CPF);
            Atuador_pessoa.setString(2, nome);
            Atuador_pessoa.setString(3, contato); 
            Atuador_pessoa.setString(4, Data);
            
            //comando insert into "físico" no MySQL
            Atuador_pessoa.execute();
            
            Atuador_aluno.setLong(1,matricula);
            Atuador_aluno.setLong(2, CPF);
            Atuador_aluno.setInt(3, periodo);   
            
            Atuador_aluno.execute();
            JOptionPane.showMessageDialog(null,"Cadastro Realizado com Sucesso","Salvar",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        
        desconectaMySQL(conexao);
    }
    
    
    //Inserir novo professor no Banco de Dados 
    public void insereProfessorNoMySQL(String nome, long CPF, String contato, String Data, long ID, String admissao, double salario){
        Connection conexao = realizaConexaoMySQL();
        String sql_pessoa = "insert into pessoa (CPF, Nome, Telefone, DataNascimento) values (?,?,?,?)";
        String sql_professor = "insert into professor (ID,CPFPessoa,DataAdmissao, SalarioBruto) values (?,?,?,?)";
        
        //inserção dos daddo nas Tabelas do MySQL
        try{
            PreparedStatement Atuador_pessoa = conexao.prepareStatement(sql_pessoa);
            PreparedStatement Atuador_professor = conexao.prepareStatement(sql_professor);
            
            //Seção para setar os campos no atuador 
            Atuador_pessoa.setLong(1, CPF);
            Atuador_pessoa.setString(2, nome);
            Atuador_pessoa.setString(3, contato); 
            Atuador_pessoa.setString(4, Data);
            
            //comando insert into "físico" no MySQL
            Atuador_pessoa.execute();
            
            Atuador_professor.setLong(1,ID);
            Atuador_professor.setLong(2, CPF);
            Atuador_professor.setString(3, admissao);
            Atuador_professor.setDouble(4, salario);   
            
            Atuador_professor.execute();
            JOptionPane.showMessageDialog(null,"Cadastro Realizado com Sucesso","Salvar",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
        }
        
        desconectaMySQL(conexao);
    }
    
    //Função que recupera dados do Banco
    public ArrayList<Aluno> recuperaDadosDoMySQL(String tipoOrdenacao){
        Connection conexao = realizaConexaoMySQL();//Estabelece conexão
        ArrayList<Aluno> Academico = new ArrayList();//Cria uma lista chamada Academico do Tipo Aluno
        
        try{            
            String sql_selecao_aluno = "SELECT *FROM pessoa, aluno "
                                       +"WHERE pessoa.CPF=aluno.CPF_Pessoa ORDER BY "+tipoOrdenacao+";";
            
            PreparedStatement atuador_selecao_aluno = conexao.prepareStatement(sql_selecao_aluno);
            ResultSet ResultadoSelecao = atuador_selecao_aluno.executeQuery(); 
            
            //percorrer todas as linhas resultantes da seleção
                while(ResultadoSelecao.next()){
                    //Cria objeto aluno
                    Aluno ObjetoAluno = new Aluno();
                    //Inserir em cada atributo do objetoAluno os valores dos campos do MySQL
                    ObjetoAluno.cpf = ResultadoSelecao.getLong("CPF");
                    ObjetoAluno.nome = ResultadoSelecao.getString("Nome");
                    ObjetoAluno.dataNascimento = ResultadoSelecao.getString("DataNascimento");
                    ObjetoAluno.contato = ResultadoSelecao.getString("Telefone");
                    ObjetoAluno.setMatricula(ResultadoSelecao.getLong("Matricula"));
                    ObjetoAluno.setPeriodo(ResultadoSelecao.getInt("Periodo"));

                    Academico.add(ObjetoAluno);//Adiciona à Lista o Objeto Atual        

                }
                ResultadoSelecao.close();
                atuador_selecao_aluno.close();                   
                    
            }
            catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }
        desconectaMySQL(conexao); //Fecha a conexão do Banco de Dados
        return Academico;
    }
    
     //Função que recupera dados do Banco
    public ArrayList<Professor> recuperaDadosProfessorDoMySQL(String tipoOrdenacao){
        Connection conexao = realizaConexaoMySQL();//Estabelece conexão
        ArrayList<Professor> professores = new ArrayList<Professor>();
        
        try {
            String sql_query = "SELECT * FROM pessoa,professor WHERE pessoa.CPF=professor.CPFPessoa ORDER BY "+tipoOrdenacao+";";
            
            PreparedStatement atuador_listar_prof = conexao.prepareStatement(sql_query);
            ResultSet resultadoSelecao = atuador_listar_prof.executeQuery();
            
            //percorrer todas as linhas resultantes da seleção
            while(resultadoSelecao.next()) {
                Professor prof = new Professor();
                //Inserir em cada atributo do objetoProfessor os valores dos campos do MySQL
                prof.setDados(resultadoSelecao.getString("Nome"),
                              resultadoSelecao.getString("DataNascimento"),
                              resultadoSelecao.getLong("CPF"),
                              resultadoSelecao.getString("Telefone"),
                              resultadoSelecao.getInt("ID"),
                              resultadoSelecao.getString("DataAdmissao"),
                              resultadoSelecao.getDouble("SalarioBruto"));
                professores.add(prof);  //Adiciona à Lista o Objeto Atual 
            }
            
            resultadoSelecao.close();
            atuador_listar_prof.close();
        }
        catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        //Fecha a conexão do Banco de Dados
        desconectaMySQL(conexao);
        
        
        return professores;
    }
    

    //Funções Utilizadas na busca e alteração de dados de Aluno
    public Aluno BuscarAlunoNoMySQL(String Matricula){
        //Realiza a conexão com o banco de dados
        Connection conexao = realizaConexaoMySQL();
        Aluno Academico = new Aluno();
        Academico = null;
        
        try{ 
            String sql_selecao_aluno = "SELECT *FROM ementorPlus.pessoa, ementorPlus.aluno where pessoa.CPF=aluno.CPF_Pessoa and aluno.Matricula='"+Matricula+"';";     
        
            PreparedStatement atuador_selecao_aluno = conexao.prepareStatement(sql_selecao_aluno);
            ResultSet ResultadoSelecao = atuador_selecao_aluno.executeQuery();
            
            while(ResultadoSelecao.next()){
                Aluno Obj = new Aluno();
                //Inserir em cada atributo do obj os valores dos campos do MySQL
                Obj.cpf = ResultadoSelecao.getLong("CPF");
                Obj.nome = ResultadoSelecao.getString("Nome");
                Obj.dataNascimento = ResultadoSelecao.getString("DataNascimento");
                Obj.contato = ResultadoSelecao.getString("Telefone");
                Obj.setMatricula(ResultadoSelecao.getLong("Matricula"));
                Obj.setPeriodo(ResultadoSelecao.getInt("Periodo"));
                
                Academico = Obj;
            }
            
            ResultadoSelecao.close();
            atuador_selecao_aluno.close();
        }
        catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        
        desconectaMySQL(conexao);
        return Academico;
    }
    
    public void atualizarDadosAlunoNoMySQL(Aluno aluno){
        Connection conexao = realizaConexaoMySQL();
        
        String sql = "UPDATE ementorPlus.pessoa, ementorPlus.aluno "
                     +"SET pessoa.Nome='"+aluno.getNome()+"', "
                     +"pessoa.DataNascimento='"+aluno.getDataNascimento()+"', "
                     +"pessoa.Telefone='"+aluno.getContato()+"', "
                     +"aluno.Periodo="+aluno.getPeriodo()+" "
                     +"WHERE pessoa.CPF=aluno.CPF_Pessoa and Matricula="+aluno.getMatricula()+";";
    
        try{
            PreparedStatement atuador = conexao.prepareStatement(sql);
            atuador.executeUpdate();
            JOptionPane.showMessageDialog(null,"Alterações realizadas com Sucesso","Busca",JOptionPane.INFORMATION_MESSAGE); 
        
            atuador.close();
        }
        catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        
        desconectaMySQL(conexao);
    }
    
    
    //Funções Utilizadas na busca e alteração de dados de Professor 
    public Professor BuscarProfessorNoMySQL(String ID){
        Connection conexao = realizaConexaoMySQL();
        Professor Prof = null;
        
        try{            
            String sql_selecao_professor = "SELECT *FROM ementorPlus.pessoa, ementorPlus.professor "
                                           +"WHERE pessoa.CPF=professor.CPFPessoa and professor.ID='"+ID+"';";     
        
            PreparedStatement atuador_selecao_professor = conexao.prepareStatement(sql_selecao_professor);
            ResultSet ResultadoSelecao = atuador_selecao_professor.executeQuery();
            
            while(ResultadoSelecao.next()){
                Professor Obj = new Professor();
                
                Obj.cpf = ResultadoSelecao.getLong("CPF");
                Obj.nome = ResultadoSelecao.getString("Nome");
                Obj.dataNascimento = ResultadoSelecao.getString("DataNascimento");
                Obj.contato = ResultadoSelecao.getString("Telefone");
                Obj.setID(ResultadoSelecao.getInt("ID"));
                Obj.setDataAdmissao(ResultadoSelecao.getString("DataAdmissao"));
                Obj.setSalarioBruto(ResultadoSelecao.getDouble("SalarioBruto"));
                
                Prof = Obj;
            }
        ResultadoSelecao.close();
        atuador_selecao_professor.close();
        
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        
        desconectaMySQL(conexao);
        return Prof;
    }
    
     public void atualizarDadosProfessorNoMySQL(Professor prof){
        Connection conexao = realizaConexaoMySQL();
        String sql = "UPDATE ementorPlus.pessoa, ementorPlus.professor "
                     +"SET pessoa.Nome='"+prof.getNome()+"', "
                     +"pessoa.DataNascimento='"+prof.getDataNascimento()+"', "
                     +"pessoa.Telefone='"+prof.getContato()+"', "
                     +"professor.SalarioBruto="+prof.getSalarioBruto()+", "
                     +"professor.DataAdmissao='"+prof.getDataAdmissao()+"' "     
                     +"WHERE pessoa.CPF=professor.CPFPessoa and professor.ID="+prof.getID()+";";
    
        try{
            PreparedStatement atuador = conexao.prepareStatement(sql);
            atuador.executeUpdate();
            JOptionPane.showMessageDialog(null,"Alterações realizadas com Sucesso","Busca",JOptionPane.INFORMATION_MESSAGE); 
        
            atuador.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        
        desconectaMySQL(conexao);
    }
     
     //Função que realiza a verificação do Usuário na tela de login
    public boolean VerificarUsuarioNoMySQL(String login, String senha){
        Connection conexao = realizaConexaoMySQL();
        Usuario User = null;
        
        try{            
            String sql_selecao_user = "SELECT *FROM ementorPlus.pessoa, ementorPlus.usuario "
                                      +"WHERE pessoa.CPF=usuario.CPFPessoa and usuario.NomeUsuario='"+login+"';";     
        
            PreparedStatement atuador_selecao_user = conexao.prepareStatement(sql_selecao_user);
            ResultSet ResultadoSelecao = atuador_selecao_user.executeQuery();
            
            while(ResultadoSelecao.next()){
                Usuario Obj = new Usuario();
                
                Obj.cpf = ResultadoSelecao.getLong("CPF");
                Obj.nome = ResultadoSelecao.getString("Nome");
                Obj.dataNascimento = ResultadoSelecao.getString("DataNascimento");
                Obj.contato = ResultadoSelecao.getString("Telefone");
                Obj.setNomeUsuario(ResultadoSelecao.getString("NomeUsuario"));
                Obj.setSenha(ResultadoSelecao.getInt("Senha"));
                
                User = Obj;
            }
            
            ResultadoSelecao.close();
            atuador_selecao_user.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE); 
        }
        
        desconectaMySQL(conexao);
        
        if(User!=null && User.getSenha() == Integer.parseInt(senha))
            return true;
        else
            return false;
    }

}
