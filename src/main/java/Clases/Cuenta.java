package Clases;

import java.sql.CallableStatement;
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
    
    final public float saldoMensual(String mes, String cuenta) {
        float promMensual = 0;
        try {
            
            final Connection con = cnx.conexionmySQL();
            final Statement statement = con.createStatement();
            final ResultSet result;
            result = statement.executeQuery(
                    "SELECT prom FROM (SELECT cod_cuenta, AVG(saldo_mov) as prom \n" +
                    "FROM (SELECT cod_cuenta, fecha, saldo_mov FROM sistemabancario.movimiento\n" +
                    "HAVING FECHA BETWEEN '2017-"+mes+"-01' AND '2017-"+mes+"-29') as tb1 group by tb1.cod_cuenta) as tb \n" +
                    "WHERE tb.cod_cuenta = '"+cuenta+"';");
            while (result.next()) {
                float aux = 0;
                promMensual = Float.parseFloat(result.getString("prom"));
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex); }
        System.out.println(promMensual);
        return promMensual;
    }
    
    public int cuentaPrestamo(String cu) {
        int valor = 0;
        final Connection con = cnx.conexionmySQL();
        try{
            CallableStatement sentencia;
            sentencia = con.prepareCall("UPDATE `sistemabancario`.`cuenta` SET `PRESTAMO`='1' WHERE `COD_CUENTA`='"+cu+"';");
            valor = sentencia.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    final public int tipo(String cuenta) {
        int tipo = 0;
        try {
            final Connection con = cnx.conexionmySQL();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery(
                    "SELECT prestamo FROM sistemabancario.cuenta\n" +
                    "WHERE COD_CUENTA = '"+cuenta+"';");
            while (result.next()) {
                float aux = 0;
                tipo = Integer.parseInt(result.getString("PRESTAMO"));                       
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex); }
        System.out.println(tipo);
        return tipo;
    }
}
