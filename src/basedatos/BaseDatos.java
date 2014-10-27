/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import java.sql.*;

/**
 *
 * @author Dam09
 */
public class BaseDatos {
    static Connection conexion;
    static Statement sentencia;
    static DatabaseMetaData metadatos;
    static ResultSetMetaData rsmetadatos;
    
    public static void main(String[] args)throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        String driver ="oracle.jdbc.driver.OracleDriver";
        String cadena ="jdbc:oracle:thin:@localhost:1521:XE";
        String usuario ="bea";
        String clave ="bea";
        
        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());      
        conexion=DriverManager.getConnection(cadena,usuario,clave);
        metadatos = conexion.getMetaData();
        
        System.out.println("-----------------------------------------------");
        System.out.println("INFORMACION DE LA BASE DE DATOS.....");
        System.out.println("-----------------------------------------------");
        metadatos = conexion.getMetaData();
        //Nombre de producto
        System.out.println("  Nombre de Producto: "+metadatos.getDatabaseProductName());
        //Version de producto
        System.out.println("  Version de Producto: "+metadatos.getDatabaseProductVersion());
        //Nombre de driver
        System.out.println("  Nombre de Driver: "+metadatos.getDriverName());
        //Version de driver
        System.out.println("  Version de Driver: "+metadatos.getDriverVersion());
        System.out.println("  URL: "+metadatos.getURL());
        System.out.println("  Usuario: "+metadatos.getUserName());
        System.out.println("-----------------------------------------------");
        System.out.println("INFORMACION SOBRE TABLAS Y VISTAS DEL ESQUEMA");
        System.out.println("-----------------------------------------------");
        String [] a= {"TABLE","VIEW"};
        ResultSet rs = metadatos.getTables(null, null, "%", a);              
        while (rs.next()) { 
            String esquema = rs.getString(2);
            String tabla = rs.getString(3);
            String tipo = rs.getString(4);
            if (esquema.compareToIgnoreCase("BEA")==0){
               if (tipo.compareToIgnoreCase("TABLE")==0){
                   System.out.println("  Esquema: "+esquema + " TABLA= "  + tabla+ " tipo: "+tipo);
               } 
               else {
                   System.out.println("  Esquema: "+esquema + " VISTA= "  + tabla+ " tipo: "+tipo);
               }       
            }
        }
        System.out.println("-----------------------------------------------");
        System.out.println("INFORMACION SOBRE LAS TABLAS");
        System.out.println("-----------------------------------------------");
        ResultSet rs3 = metadatos.getTables(null, null, "%", a);
        while (rs3.next()) { 
            String catalogo= rs3.getString(1);
            String esquema = rs3.getString(2);
            String tabla = rs3.getString(3);
            String tipo = rs3.getString(4);
            if (esquema.compareToIgnoreCase("BEA")==0){
               if (tipo.compareToIgnoreCase("TABLE")==0){
                   ResultSet rs2 = metadatos.getColumns(catalogo, null, tabla, null);
                   while (rs2.next()) {
                        String nombreColumna = rs2.getString(4);
                        String tipoColumna = rs2.getString(6);
                        System.out.print("  TABLA= "  + tabla);
                        System.out.println("      Columna= " + nombreColumna+ "        tipo= " + tipoColumna);
                    }         
               } 
            }
        }
        System.out.println("-----------------------------------------------");
        System.out.println("INFORMACION SOBRE LAS VISTAS");
        System.out.println("-----------------------------------------------");
        ResultSet rs4 = metadatos.getTables(null, null, "%", a);
        while (rs4.next()) { 
            String catalogo= rs4.getString(1);
            String esquema = rs4.getString(2);
            String tabla = rs4.getString(3);
            String tipo = rs4.getString(4);
            if (esquema.compareToIgnoreCase("BEA")==0){
               if (tipo.compareToIgnoreCase("VIEW")==0){
                   ResultSet rs5 = metadatos.getColumns(catalogo, null, tabla, null);
                   while (rs5.next()) {
                        String nombreColumna = rs5.getString(4);
                        String tipoColumna = rs5.getString(6);
                        System.out.print("  VISTA= "  + tabla);
                        System.out.println("      Columna= " + nombreColumna+ "        tipo= " + tipoColumna);
                    }         
               } 
            }
        }       
        System.out.println("-----------------------------------------------");
        System.out.println("CONSULTAS VARIAS:");
        System.out.println("-----------------------------------------------");
        System.out.println("  LIBROS : ");
        
        sentencia = conexion.createStatement();
        ResultSet rset = sentencia.executeQuery("select * from BEA.Libros");
        
    while (rset.next()){
         System.out.println ("      "+rset.getString(5)+" - Autor: " + rset.getString(2)+ " - ISBN:"+ rset.getString(1)) ;
         System.out.println("          Paginas: "+ rset.getString(3) + "Biblioteca: "+rset.getString(4));      
    }
    sentencia.close();
}
}
