package alterardados;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlterarDados {
    
    static final String banco = "jdbc:mysql://localhost:3306/eletronicos";

    public static void main(String[] args) {

        Connection conexao = null;
        Statement consulta = null;
        ResultSet resultados = null;
        PreparedStatement minhaalteracao=null;
        
        try {
            conexao = DriverManager.getConnection(banco,"root","");
            consulta = conexao.createStatement();
            resultados = consulta.executeQuery("Select * from monitores");
            ResultSetMetaData colunas = resultados.getMetaData();
            
            int numeroColunas = colunas.getColumnCount();
            System.out.println("informações do Banco de dados");
            minhaalteracao = conexao.prepareStatement("update monitores set valor = ? where codigo = ?");
            
            int cod = Integer.parseInt(JOptionPane.showInputDialog("Qual o código do monitor a ser alterado"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog(null,"Digite o novo valor do monitor"));
            minhaalteracao.setDouble(1, valor);
            minhaalteracao.setInt(2, cod);
            minhaalteracao.executeUpdate();
            
            while (resultados.next()){
                for (int i=1; i <= numeroColunas; i++ )
                System.out.println("dados " + resultados.getObject(i));
                System.out.println();
                System.out.println("Dados alterados com sucesso");
            }
        } 
        catch (SQLException erro){
            erro.printStackTrace();
        }
        finally {
            try {
                resultados.close();
                consulta.close();
                conexao.close();
            }
            catch (Exception erronovo) {
                erronovo.printStackTrace();
            }   
        }
    }
}