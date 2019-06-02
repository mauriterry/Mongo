/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comidas;

import java.util.Scanner;
import redis.clients.jedis.Jedis;
import Personas.Menu_Personas;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author equipo1
 */
public class Menu_Comidas {
    
    Jedis jedis = new Jedis("192.168.56.101"); // Realizamos conexion base de datos
    Scanner sc = new Scanner(System.in);
    Menu_Personas av = new Menu_Personas();
    
    public int comPersona(String id, String per){
            for (int y = 0; y < jedis.llen(id); y++) {
                List<String> com32 = jedis.lrange(id, y, y);
                String jkl=com32.toString();
                if (jkl.equals(per)) {
                    return 1;
                }
            }
        return 0;
    }
            
    public void inComida(String id){
        String f, s, co;
	System.out.println("\nDigite la fecha DD/MM/AAAA");
	f = sc.next();
	System.out.println("\nDigite la hora 00:00 am/pm");
	s = sc.next();
	System.out.println("\nDigite la comida");
	co = sc.next();
	Long com = jedis.rpush(id,f,s,co);
        System.out.println("\nTERMINA\n");
	System.out.println(com);
    }
    
    public void comidaIn(){
        int num2;
        String id;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la cedula de la persona a quien va añadir la comida");
        id = sc.next();
        num2 = av.verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                inComida(id);
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            comidaIn();
        }
    }

    
    public void comidaMo(String id,String per){
        int xy = 0;
        for (int y = 0; y < jedis.llen(id); y++) {
                List<String> com32 = jedis.lrange(id, y, y);
                String jkl=com32.toString();
                if (jkl.equals(per)) {
                    xy=y;
                }
            }
        int limit=10, expression;
        String f;
        while(limit!=1){
            System.out.flush(); // Borrar contenido en Pantalla
            System.out.println("\n------ MENU MODIFICAR COMIDA ------\n");
            System.out.println("1) Hora\n");
            System.out.println("2) Comida\n");
            System.out.println("3) Volver al menu anterior\n");
            expression = sc.nextInt();
            switch(expression){  
                case 1:
                    System.out.flush(); // Borrar contenido en Pantalla
                    System.out.println("\n--MODIFICAR HORA--\n");
                    System.out.println("\nDigite la Hora");
                    f = sc.next();
                    jedis.lset(id, xy+1, f);
                    System.out.println("\nTERMINA\n");
                    String id1 = sc.next();
                    break;
                case 2:
                    System.out.flush(); // Borrar contenido en Pantalla
                    System.out.println("\n--MODIFICAR COMIDA--\n");
                    System.out.println("\nDigite la Comida");
                    f = sc.next();
                    jedis.lset(id, xy+2, f);
                    System.out.println("\nTERMINA\n");
                     id1 = sc.next();
                    break;
                case 3:
                    limit=1;
                    break;
            }
        }
        
    }
    
    public void modComida(){
        int num2;
        String id,per;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la celuda de la persona a que va modificar la comida\n");
        id = sc.next();
        num2 = av.verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                System.out.println("Ingrese dia");
                int a = sc.nextInt();
                System.out.println("Ingrese mes");
                int b = sc.nextInt();
                System.out.println("Ingrese año");
                int c = sc.nextInt();
                String h ="["+a+"/"+b+"/"+c+"]";
                num2 = comPersona(id,h);
                if(num2==1){
                    comidaMo(id,h); //Pendiente por realizar
                }else{
                    System.out.println("La comida digitada no se encuentra en la base de datos");
	            System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas");
                }
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            modComida();
        }
    }
    
    public void comidaDe(String id,String per){
        String val1,val2,val3;
        int xy = 0;
        for (int y = 0; y < jedis.llen(id); y++) {
                List<String> com32 = jedis.lrange(id, y, y);
                String jkl=com32.toString();
                if (jkl.equals(per)) {
                    xy=y;
                }
            }
        val1 = jedis.lindex(id, xy);
        val2 = jedis.lindex(id, xy+1);
        val3 = jedis.lindex(id, xy+2);
        jedis.lrem(id, xy, val1);
        jedis.lrem(id, xy+1, val2);
        jedis.lrem(id, xy+2, val3);
        System.out.println("Comida #"+id+" Eliminada de la base de datos\n");
        System.out.println("Termina");
    }
    
    public void delComida(){
        int num2;
        String id,per;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la celuda de la persona a que va eliminar la comida\n");
        id = sc.next();
        num2 = av.verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                System.out.println("Ingrese dia");
                int a = sc.nextInt();
                System.out.println("Ingrese mes");
                int b = sc.nextInt();
                System.out.println("Ingrese año");
                int c = sc.nextInt();
                String h ="["+a+"/"+b+"/"+c+"]";
                num2 = comPersona(id,h);
                if(num2==1){
                    comidaDe(id,h); //Pendiente por realizar
                }else{
                    System.out.println("La comida digitada no se encuentra en la base de datos");
	            System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas");
                }
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            modComida();
        }
    }
    
    public void conComida(){
        int num2;
        String id,per;
        System.out.flush(); // Borrar contenido en Pantalla
        System.out.println("Digite la celuda de la persona a que va consultar la comida\n");
        id = sc.next();
        num2 = av.verPersona(id);
        String exp = "\\d{10}";
        boolean f = Pattern.matches(exp,id);
        if(!f==false){
            if(num2==0){
                System.out.println("Ingrese dia");
                int a = sc.nextInt();
                System.out.println("Ingrese mes");
                int b = sc.nextInt();
                System.out.println("Ingrese año");
                int c = sc.nextInt();
                String h ="["+a+"/"+b+"/"+c+"]";
                num2 = comPersona(id,h);
                if(num2==1){
                    comidaCo(id,h); //Pendiente por realizar
                }else{
                    System.out.println("La comida digitada no se encuentra en la base de datos");
	            System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas");
                }
            }else{
                System.out.println("La cedula no se encuentra en la base de datos\n");
                System.out.println("Oprimir tecla enter para volver al Menu Gestion Personas\n");            
            }
        }else{
            modComida();
        }
    }
    
    public void vercomidas(){
        Set<String> com = jedis.keys("*");
        Object [] com2 = com.toArray();
        for (Object com21 : com2) {
            System.out.println("------------------------------------");
            System.out.println("Llave:" + com21);
            System.out.println(jedis.lrange(com21.toString(), 3, -1));
            System.out.println("------------------------------------");
        }
    }
    
    public void verfecha(){
        System.out.println("Ingrese dia");
        int a = sc.nextInt();
        System.out.println("Ingrese mes");
        int b = sc.nextInt();
        System.out.println("Ingrese año");
        int c = sc.nextInt();
        String h ="["+a+"/"+b+"/"+c+"]";
        Set<String> com = jedis.keys("*");
        Object [] com2 = com.toArray();
        System.out.println("__________________________");
        for (Object com21 : com2) {
            System.out.println("°°°°°°°°°°°°°°°°");
            System.out.println("KEY: " + com21);
            for (int y = 0; y < jedis.llen(com21.toString()); y++) {
                List<String> com32 = jedis.lrange(com21.toString(), y, y);
                String jkl=com32.toString();
                if (jkl.equals(h)) {
                    System.out.println("" + jedis.lrange(com21.toString(), y, y));
                    System.out.println("" + jedis.lrange(com21.toString(), y+1, y+1));
                    System.out.println("" + jedis.lrange(com21.toString(), y+2, y+2));
                }
            }   
        }
    }

    private void comidaCo(String id, String per) {
        System.out.println("°°°°°°°°°°°°°°°°");
            System.out.println("KEY: " + id);
            for (int y = 0; y < jedis.llen(id); y++) {
                List<String> com32 = jedis.lrange(id, y, y);
                String jkl=com32.toString();
                if (jkl.equals(per)) {
                    System.out.println("" + jedis.lrange(id, y, y));
                    System.out.println("" + jedis.lrange(id, y+1, y+1));
                    System.out.println("" + jedis.lrange(id, y+2, y+2));
                }
            }
    }
}
