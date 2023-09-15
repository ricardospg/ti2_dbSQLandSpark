package Dao;

import models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends DAO {
	public UsuarioDAO() {
		super();
		conectar();
		}
	
	public void finaliza() {
		close();
	}
	
	/* O método recebe um usuario */
	public boolean insert(Usuario usuario) {
		//Status da conexão:
		boolean status = false;
		try {
			Statement st = conexao.createStatement(); /* Cria a conexão*/
			
			/* Query Insert */
			String sql = "INSERT INTO usuario (codigo, nome, email, idade) "			 
					+ "VALUES ("+usuario.getCodigo()+ ", '" + usuario.getNome() + "', '"
					+ usuario.getEmail() + "', '" + usuario.getIdade() + "');";
			
			System.out.println(sql);
			st.executeUpdate(sql); /* Executa */
			st.close(); /* Fecha conexão */
			status = true;
			
		} catch (SQLException u) { /* Tratamento de excessão */
			throw new RuntimeException(u);
			
		}
		
		return status;
	}//fim insert
	
	public Usuario get(int codigo) {
		Usuario Usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario WHERE codigo="+codigo;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 Usuario = new Usuario(rs.getInt("codigo"), rs.getString("nome"), rs.getString("email"), rs.getInt("idade") );
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return Usuario;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<Usuario> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Usuario> getOrderByIdade() {
		return get("idade");		
	}
	
	
	private List<Usuario> get(String orderBy) {
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario p = new Usuario(rs.getInt("codigo"), rs.getString("nome"), rs.getString("email"), rs.getInt("idade"));
	            Usuarios.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return Usuarios;
	}
	
	/* Exclui um usuario pelo código */
	public boolean delete(int codigo) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			/* Exclui o usuario correspondente da tabela */
			String sql = "DELETE FROM usuario WHERE codigo = " + codigo;
			System.out.println(sql);
			
			st.executeUpdate(sql);
			st.close();
			
			status = true;
		} catch (SQLException u) { /* Tratamento de excessão */
			throw new RuntimeException(u);
		}
		
		return status;
	}// fim delete
	
	
	
	/* O método recebe um objeto CLiente que contém os dados novos. */
	public boolean update(Usuario usuario) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET codigo = '" + usuario.getCodigo() + "', nome = '"
			+ usuario.getNome() + "', email = '" + usuario.getEmail() + "'"
			+ " WHERE codigo = " + usuario.getCodigo();
			
			System.out.println(sql);
			
			st.executeUpdate(sql);
			st.close();
			
			status = true;
			
		} catch (SQLException u) { /* Tratamento de excessão */
			throw new RuntimeException(u);
		}
		
		return status;
	}
}