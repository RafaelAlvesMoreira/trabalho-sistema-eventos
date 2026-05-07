package app;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	public String nome;
	public String endereco;
	public String categoria;
	public LocalDateTime horario;
	public String descricao;
	public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
		this.nome = nome;
		this.endereco = endereco;
		this.categoria = categoria;
		this.horario = horario;
		this.descricao = descricao;}
	public String getStatus() {
		LocalDateTime agora = LocalDateTime.now();
		if
		(this.horario.isBefore(agora))
		{
			return "Este evento ja ocorreu.";
		} else {
			return "Evento futuro (ainda vai ocorrer).";
		} //				
	} // <--.
}