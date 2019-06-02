/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personas;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import redis.clients.jedis.Jedis;

/**
 *
 * @author equipo1
 */
public class Menu_Personas {
    Jedis jedis = new Jedis("192.168.56.101"); // Realizamos conexion base de datos
    Scanner in = new Scanner(System.in);
    
    public int verPersona(String id){
        List<String> com = jedis.lrange(id, 0, -1);
        if(!com.contains("Nombre")){
            return 1;
        }else{
            return 0;
        }
    }
    
    public void personaIn(String id){
        String f, s;
	System.out.println("\nDigite el Nombre");
	f = in.next();
	System.out.println("\nDigite a edad ");
	s = in.next();
	Long com = jedis.rpush(id,"Nombre",f,"Edad",s);
        System.out.println("\nTERMINA\n");
    }
    
    public void insPersona(){
        int num2;
        String id;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la cedula de la persona a insertar");
        id = in.next();
        num2 = verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==1){
                personaIn(id);
            }else{
                System.out.println("La cedula ya se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            insPersona();
        }
    }

    public void personaMo(String id){
        int limit=10, expression;
        String f;
        while(limit!=1){
            System.out.flush(); // Borrar contenido en Pantalla
            System.out.println("\n------ MENU MODIFICAR PERSONA ------\n");
            System.out.println("1) Nombre\n");
            System.out.println("2) Edad\n");
            System.out.println("3) Volver al menu anterior\n");
            expression = in.nextInt();
            switch(expression){  
                case 1:
                    System.out.flush(); // Borrar contenido en Pantalla
                    System.out.println("\n--MODIFICAR NOMBRE--\n");
                    System.out.println("\nDigite el Nombre");
                    f = in.next();
                    jedis.lset(id, 1, f);
                    System.out.println("\nTERMINA\n");
                    break;
                case 2:
                    System.out.flush(); // Borrar contenido en Pantalla
                    System.out.println("\n--MODIFICAR EDAD--\n");
                    System.out.println("\nDigite la Edad");
                    f = in.next();
                    jedis.lset(id, 3, f);
                    System.out.println("\nTERMINA\n");
                    break;
                case 3:
                    limit=1;
                    break;
            }
        }
        
    }
    
    public void modPersona(){
        int num2;
        String id;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la celuda de la persona a Modificar\n");
        id = in.next();
        num2 = verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                personaMo(id);
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            modPersona();
        }
    }
    
    public void personaDe(String id){
        jedis.del(id);
	System.out.println("Persona "+id+" ELiminada de la base de datos");
	System.out.println("TERMINA");
    }
    
    public void delPersona(){
        int num2;
        String id;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la celuda de la persona a Eliminar\n");
        id = in.next();
        num2 = verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                personaDe(id);
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            delPersona();
        }
    }
    
    public void personaCo(String id){
            System.out.println("------------------------------------");
            System.out.println("Llave:" + id);
            System.out.println(jedis.lindex(id, 1));
            System.out.println(jedis.lindex(id, 3));
            System.out.println("------------------------------------");
	System.out.println("TERMINA");
    }
    
    public void conPersona(){
        int num2;
        String id;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la celuda de la persona a Consultar\n");
        id = in.next();
        num2 = verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                personaCo(id);
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            conPersona();
        }
    }
    
    public void vertodos(){
        Set<String> com = jedis.keys("*");
        Object [] com2 = com.toArray();
        for (Object com21 : com2) {
            System.out.println("------------------------------------");
            System.out.println("Llave:" + com21);
            System.out.println(jedis.lindex(com21.toString(), 1));
            System.out.println(jedis.lindex(com21.toString(), 3));
            System.out.println("------------------------------------");
        }
    }
}
