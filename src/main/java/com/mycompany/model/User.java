package com.mycompany.model;

public class User {
    private String nome, email, senha, telefone;
    boolean admin;

    public User() {
    }
    
    public User(String nome, String email, String senha, String telefone, boolean administrador){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.admin = administrador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdministrador(boolean admin) {
        this.admin = admin;
    }
   
}
