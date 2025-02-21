package model;

public class Personagem {
	private int id;
	private String nome;
	private String cla;
	private String rank;
	private int naturezas;
	
	public Personagem() {
		this.id = -1;
		this.nome = "";
		this.cla = "";
		this.rank = "";
		this.naturezas = 0;
	}
	
	public Personagem(int id, String nome, String cla, String rank, int naturezas) {
		this.id = id;
		this.nome = nome;
		this.cla = cla;
		this.rank = rank;
		this.naturezas = naturezas;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCla() {
		return cla;
	}

	public void setCla(String cla) {
		this.cla = cla;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public int getNaturezas() {
		return naturezas;
	}

	public void setNaturezas(int naturezas) {
		this.naturezas = naturezas;
	}
	
	@Override
	public String toString() {
		return "Personagem [Id = " + id + ", Nome = " + nome + ", Clã = " + cla + ", Rank = " + rank + ", Naturezas de Chakra = " + naturezas + "]";
	}
}
