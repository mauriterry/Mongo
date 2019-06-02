/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Comidas.Menu_Comidas;
import Personas.Menu_Personas;
import java.util.Scanner;
import java.util.Set;
import redis.clients.jedis.Jedis;

/**
 *
 * @author equipo1
 */
public class Menu {
    Menu_Comidas comida = new Menu_Comidas();
    Menu_Personas per = new Menu_Personas();
    Scanner in = new Scanner(System.in);
    Jedis jedis = new Jedis("192.168.56.101"); // Realizamos conexion base de datos
    
    public void menu()
    {
        System.out.println("----MENU----");
        System.out.println("0 Salir");
        System.out.println("1 Gestion Personas");
        System.out.println("2 Gestion Comidas");
        System.out.println("3 Ver Todas"); 
    }
    
    public void menu1(){
        System.out.println("---- MENU GESTION PERSONAS ----");
        System.out.println("0 volver");
        System.out.println("1 Instertar");
        System.out.println("2 Modificar");
        System.out.println("3 Eliminar ");
        System.out.println("4 Consultar");
        System.out.println("5 Ver todas");
    }
    
    public void menu2(){
        System.out.println("---- MENU GESTION COMIDAS ----");
        System.out.println("0 volver");
        System.out.println("1 Instertar");
        System.out.println("2 Modificar");
        System.out.println("3 Eliminar ");
        System.out.println("4 Consultar");
        System.out.println("5 Ver todas");
        System.out.println("6 Consultar Fecha");
    }
    
    public void menu3(){
        
        int limit=10, expression;
        while(limit!=1){
            System.out.flush(); // Borrar contenido en Pantalla
            menu();
            expression = in.nextInt();
            switch(expression){  
                case 0:
                    System.exit(0);
                    limit=1;
                    break;
                case 1:
                    limit=10;
                    while(limit!=1){
                        System.out.flush(); // Borrar contenido en Pantalla
                        menu1();
                        expression = in.nextInt();
                        switch(expression){
                            case 0:
                                limit=1;
                                break;
                            case 1:
                                per.insPersona();
                                break;
                            case 2:
                                per.modPersona();
                                break;
                            case 3:
                                per.delPersona();
                                break;
                            case 4:
                                per.conPersona();
                                break;
                            case 5:
                                per.vertodos();
                                break;
                            default:
                                break;
                        }
                    }
                    menu3();
                    break;
                case 2:
                    limit=10;
                    while(limit!=1){
                        System.out.flush(); // Borrar contenido en Pantalla
                        menu2();
                        expression = in.nextInt();
                        switch(expression){
                            case 0:
                                limit=1;
                                break;
                            case 1:
                                comida.comidaIn();
                                break;
                            case 2:
                                comida.modComida();
                                break;
                            case 3:
                                comida.delComida();
                                break;
                            case 4:
                                comida.conComida();
                                break;
                            case 5:
                                comida.vercomidas();
                                break;
                            case 6:
                                comida.verfecha();
                            default:
                                break;
                        }
                    }
                    menu3();
                    break;
                case 3:
                    Set<String> com = jedis.keys("*");
                    Object [] com2 = com.toArray();
                    for (Object com21 : com2) {
                        System.out.println("------------------------------------");
                        System.out.println("Llave:" + com21);
                        System.out.println("------------------------------------");
                    }
                    break;
            }
        }
    }
}
