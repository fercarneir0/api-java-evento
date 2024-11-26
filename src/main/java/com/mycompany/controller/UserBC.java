
package com.mycompany.controller;

import com.mycompany.model.User;
import java.util.ArrayList;
import java.util.List;


public class UserBC {
    
    public static List<User> User  = new ArrayList<User>();
   
    
    public boolean checkEmail(String email){
        return UserBC.User.stream().anyMatch(u -> u.getEmail().equals(email));
    }
    
     public boolean checkAdmin(User user){
        return user.isAdmin();
    }
    
    public void salvarUsuario(User user){
        if(checkEmail(user.getEmail())){
            UserBC.User.add(user);
            System.out.println("Usuário salvo com sucesso!");
        }else{
            System.out.println("Usuário salvo com sucesso!");
        }
    }
    
    public List<User> listarUsuarios(){
        return UserBC.User;
    }
    
    public String alterarDados(User solicitante, User usuarioAlvo){
        if(!checkAdmin(solicitante) && !solicitante.equals(usuarioAlvo)){
            return "Somente um administrador pode alterar dados";
        } else {
            return "Usuário alterado com sucesso";
        }
    }
    
    public String removerUsuarios(User email){
        if(!checkAdmin(email)){
            return "Somente um administrador pode remover um usuário";
        } else {
            return "Usuário alterado com sucesso!";
        }
    }
}
