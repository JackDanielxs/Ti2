package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Personagem;

public class PersonagemDAO extends DAO{
	
	public PersonagemDAO() {
		super();
		conectar();
	}
	public void finalize() {
		close();
	}
	
	public int recuperarId() {
		int codigo = 0;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select id + 1 as id from personagem order by id desc limit 1";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	codigo = rs.getInt("id");
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return codigo;
	}
	
	public boolean insert(Personagem p) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO personagem (id, nome, cla, rank, naturezas) "
				       + "VALUES (" + p.getId() + ", '" + p.getNome() + "', '" + p.getCla() + "', '" + p.getRank() + "', " + p.getNaturezas() + ");";
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Personagem get(int id) {
		Personagem per = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM personagem WHERE id = " + id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	per = new Personagem(rs.getInt("id"), rs.getString("nome"), rs.getString("cla"), rs.getString("rank"), rs.getInt("naturezas"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return per;
	}
	
	public List<Personagem> get() {
		return get("");
	}

	
	public List<Personagem> getOrderById() {
		return get("id");		
	}
	
	
	public List<Personagem> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Personagem> getOrderByCla() {
		return get("cla");		
	}
	
	public List<Personagem> getOrderByRank() {
		return get("rank");		
	}
	
	public List<Personagem> getOrderByNaturezas() {
		return get("naturezas");		
	}
	
	private List<Personagem> get(String orderBy) {	
		
		List<Personagem> personagens = new ArrayList<Personagem>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM personagem" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Personagem u = new Personagem(rs.getInt("id"), rs.getString("nome"), rs.getString("cla"), rs.getString("rank"), rs.getInt("naturezas"));
	        	personagens.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return personagens;
	}
	
	public boolean update(Personagem p) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE personagem SET nome = '" + p.getNome() + "', cla = '"  
				       + p.getCla() + "', rank = '" + p.getRank() + "', naturezas = " + p.getNaturezas() + 
				       " WHERE id = " + p.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM personagem WHERE id = " + id;
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}
