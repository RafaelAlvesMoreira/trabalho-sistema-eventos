package app;

public class Usuario implements java.io.Serializable {
    public String nome;
    public String email;
    public String cidade;
    public int idade;

    public Usuario(String nome, String email, String cidade, int idade) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.idade = idade;
    }
}
