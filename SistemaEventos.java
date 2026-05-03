package app;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaEventos {
	public static void
	main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ArrayList<Evento> lista = new ArrayList<>();
		
		System.out.println("---CADASTRO DE USUARIO---");
		System.out.print("Nome do Estudante: ");
		String nomeU = teclado.nextLine();
		System.out.print("Cidade onde reside:" );
		String cidadeU = teclado.nextLine();
		
		// 1. Criar o objeto Usuario com os dados lidos
		Usuario user = new Usuario(nomeU, "estudante@anima.com", cidadeU);
		
		System.out.println("\n---CADASTRAR NOVO EVENTO ---");
		System.out.print("Nome do Evento: ");
		String nomeE = teclado.nextLine();
		
		System.out.print("Endereco: ");
		String endE = teclado.nextLine();
		
		System.out.print("Categoria(Show/Festa/Palestra): ");
		String catE = teclado.nextLine();
		
		System.out.print("Descricao: ");
		String descE = teclado.nextLine();
		
		//2. Criar o evento e adicionar na lista
		Evento novo = new Evento(nomeE, endE, catE, LocalDateTime.now().plusDays(2), descE);
		lista.add(novo);
		
		//3. SALVAR NO ARQUIVO (Exigência do enunciado)
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("evento.dat")))
		{
			
			oos.writeObject(lista);
			
			System.out.println("\n[OK] Dados salvos com sucesso no arquivo evento.dat!");
		System.out.println("\n--- CONFIRMACAO DE PRESENCA ---");
		System.out.println("Usuario: " + user.nome + " | Evento: " + novo.nome);
		System.out.println("Deseja cancelar sua participacao? (S/N): ");
		String resposta = teclado.nextLine();
		if (resposta.equalsIgnoreCase("S"))
		{
			lista.remove(novo);
			try (ObjectOutputStream oosAlt = new ObjectOutputStream(new FileOutputStream("evento.dat")))
			{
				
				oosAlt.writeObject(lista);
				
				System.out.println("Particpacao cancelada com sucesso!");
			} catch
			(IOException e) {
				
				System.out.println("Erro ao atualizar arquivo.");
			}
		} else {
			System.out.println("Presenca confirmada!");
		}
		System.out.println("\n--- RESUMO DO CADASTRO ---");
		System.out.println("Estudante: " + user.nome + " de " + user.cidade);
		System.out.println("Evento: " + novo.nome + " em " + novo.endereco);
		teclado.close();
		} catch (IOException e)
		{
			System.out.println("Erro ao processar arquivbo: " + e.getMessage());
		}
	}// Fecha o main
} // Fecha a classe 		}
