
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
    
    public String salvarUsuario(User user){
        if(checkEmail(user.getEmail())){
            UserBC.User.add(user);
            return "Usuário cadastrado com sucesso";
        }else{
            return "Já existe um usuário cadastrado com esse e-mail!";
        }
    }
    
    public List<User> listarUsuarios(){
        return UserBC.User;
    }
    
    public String alterarDados(User user){
        if(!checkAdmin(user) && User){
            return "Somente um administrador pode alterar dados"
        } 
    } 
}
