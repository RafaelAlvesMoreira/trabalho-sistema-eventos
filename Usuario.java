package app;

public class Usuario implements java.io.Serializable {
	public String nome;
	public 	String email;
	public String cidade;
	
	public Usuario(String nome, String email, String cidade) {
		this.nome = nome;
		this.email = email;
		this.cidade = cidade;
	}

}
