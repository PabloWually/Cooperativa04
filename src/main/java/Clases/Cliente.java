package Clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jhona
 */
public class Cliente {

    private String cedula;
    private String genero;
    private float ingMensuales;
    final ConexionMysql cnx = new ConexionMysql();

    public Cliente(final String cedula, final String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public Cliente() {
    }
    
    private String nombre;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(final String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public float getIngMensuales() {
        return ingMensuales;
    }

    public void setIngMensuales(float ingMensuales) {
        this.ingMensuales = ingMensuales;
    }
    
    final public float saldoMensual(String cedula) {
        float promMensual = 0;
        try {
            final Connection con = cnx.conexionmySQL();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT ing_mesuales FROM sistemabancario.cliente\n" +
                                                            "WHERE cedula = '"+cedula+"';");
            while (result.next()) {
                float aux = 0;
                promMensual = Float.parseFloat(result.getString("ing_mesuales"));
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        System.out.println(promMensual);
        return promMensual;
    }
    
}
