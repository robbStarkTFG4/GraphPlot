/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing3d.util;

/**
 *
 * @author marcoisaac
 */
public class CurvaDatos {

    private String ecuacion;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int cMin;
    private int cMax;

    private String tipo;
    private double afinador;
    private double h;
    private double k;

    private String aRadius;
    private String bRadius;
    
    private double amplificador;
    private double a,b;

    public CurvaDatos() {
    }

    public CurvaDatos(double h, double k, double a, double b) {
        this.h = h;
        this.k = k;
        this.a = a;
        this.b = b;
    }

    public CurvaDatos(int cMin, int cMax, double afinador, double h, double k, String aRadius, String bRadius) {
        this.cMin = cMin;
        this.cMax = cMax;
        this.afinador = afinador;
        this.h = h;
        this.k = k;
        this.aRadius = aRadius;
        this.bRadius = bRadius;
    }

    public CurvaDatos(int cMin, int cMax, double afinador, double h, double k) {
        this.cMin = cMin;
        this.cMax = cMax;
        this.afinador = afinador;
        this.h = h;
        this.k = k;
    }

    public CurvaDatos(int xMin, int xMax, int yMin, int yMax, int cMin, int cMax, double afinador) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.cMin = cMin;
        this.cMax = cMax;
        this.tipo = tipo;
        this.afinador = afinador;
    }

    public CurvaDatos(String ecuacion, int xMin, int xMax, double afinador) {
        this.ecuacion = ecuacion;
        this.xMin = xMin;
        this.xMax = xMax;
        this.afinador = afinador;
    }

    public CurvaDatos(String ecuacion, int xMin, int xMax, int cMin, int cMax, double afinador) {
        this.ecuacion = ecuacion;
        this.xMin = xMin;
        this.xMax = xMax;
        this.cMin = cMin;
        this.cMax = cMax;
        this.afinador = afinador;
    }

    public String getEcuacion() {
        return ecuacion;
    }

    public void setEcuacion(String ecuacion) {
        this.ecuacion = ecuacion;
    }

    public int getxMin() {
        return xMin;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public int getcMin() {
        return cMin;
    }

    public void setcMin(int yMin) {
        this.cMin = yMin;
    }

    public int getcMax() {
        return cMax;
    }

    public void setcMax(int yMax) {
        this.cMax = yMax;
    }

    public double getAfinador() {
        return afinador;
    }

    public void setAfinador(double afinador) {
        this.afinador = afinador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getyMin() {
        return yMin;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public String getaRadius() {
        return aRadius;
    }

    public void setaRadius(String aRadius) {
        this.aRadius = aRadius;
    }

    public String getbRadius() {
        return bRadius;
    }

    public void setbRadius(String bRadius) {
        this.bRadius = bRadius;
    }

    public double getAmplificador() {
        return amplificador;
    }

    public void setAmplificador(double amplificador) {
        this.amplificador = amplificador;
    }

}
