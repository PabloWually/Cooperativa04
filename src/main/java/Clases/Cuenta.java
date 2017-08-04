package Clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author solan
 */
public class Cuenta {

    private int codigo;
    private String ci;
    private String tipo;
    private float saldo;
    private String estado;
    private String nombre;
    final ConexionMysql cnx = new ConexionMysql();

    public Cuenta(final int codigo, final String ci, final String tipo, 
            final float saldo, final String estado, final String nombre) {
        this.codigo = codigo;
        this.ci = ci;
        this.tipo = tipo;
        this.saldo = saldo;
        this.estado = estado;
        this.nombre = nombre;
    }

    public Cuenta() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(final String ci) {
        this.ci = ci;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(final float saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
        final public ArrayList<Cuenta> buscarCuenta(final String cuent) {
        final ArrayList<Cuenta> cuentArray = new ArrayList<Cuenta>();
        try {
            final Connection con = cnx.conexionmySQL();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM cuenta WHERE cedula='"+cuent+"';");
            while (result.next()) {
                final Cuenta cuen = new Cuenta();
                cuen.setCodigo(Integer.parseInt(result.getString("cod_cuenta")));
                cuen.setCi(result.getString("cedula"));
                cuen.setTipo(result.getString("tipo"));
                cuen.setSaldo(Float.parseFloat(result.getString("saldo")));
                cuen.setEstado(result.getString("estado"));
                cuentArray.add(cuen);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex); }
        return cuentArray;
    }
}
