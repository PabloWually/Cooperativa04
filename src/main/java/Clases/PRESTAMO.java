/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.List;
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
 * @author pablo
 */
public class PRESTAMO {
    String cuenta;
    String fecha;
    float cuota;
    float amortizacion;
    float interes;
    float pendiente;
    final ConexionMysql cnx = new ConexionMysql();

    public PRESTAMO() {
    }

    public PRESTAMO(String cuenta, String fecha, float cuota, float amortizacion, float interes, float pendiente) {
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.cuota = cuota;
        this.amortizacion = amortizacion;
        this.interes = interes;
        this.pendiente = pendiente;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }

    public float getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(float amortizacion) {
        this.amortizacion = amortizacion;
    }

    public float getInteres() {
        return interes;
    }

    public void setInteres(float interes) {
        this.interes = interes;
    }

    public float getPendiente() {
        return pendiente;
    }

    public void setPendiente(float pendiente) {
        this.pendiente = pendiente;
    }
    
    public int ingresarPrestamo(PRESTAMO pres) {
        int valor = 0;
        final Connection con = cnx.conexionmySQL();
        try{
            CallableStatement sentencia;
            sentencia = con.prepareCall("INSERT INTO `sistemabancario`.`prestamos` (`CUENTA`, `FECHA`, `COUTA`, `AMORTIZACION`, `INTERES`, `PENDIENTE`) VALUES (?,?,?,?,?,?);");
            sentencia.setString(1, pres.cuenta);
            sentencia.setString(2, pres.fecha);
            sentencia.setString(3, Float.toString(pres.cuota));
            sentencia.setString(4, Float.toString(pres.amortizacion));
            sentencia.setString(5, Float.toString(pres.interes));
            sentencia.setString(6, Float.toString(pres.pendiente));
            valor = sentencia.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(PRESTAMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(PRESTAMO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    final public ArrayList<PRESTAMO> buscarPrestamo(final String cuent) {
        final ArrayList<PRESTAMO> presArray = new ArrayList<PRESTAMO>();
        try {
            final Connection con = cnx.conexionmySQL();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM sistemabancario.prestamos WHERE cuenta = '"+cuent+"';");
            while (result.next()) {
                final PRESTAMO pres = new PRESTAMO();
                pres.setFecha(result.getString("fecha"));
                pres.setCuota(Float.parseFloat(result.getString("couta")));
                pres.setAmortizacion(Float.parseFloat(result.getString("amortizacion")));
                pres.setInteres(Float.parseFloat(result.getString("interes")));
                pres.setPendiente(Float.parseFloat(result.getString("pendiente")));
                presArray.add(pres);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(PRESTAMO.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(PRESTAMO.class.getName()).log(Level.SEVERE, null, ex); }
        return presArray;
    }
}
