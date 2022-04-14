package cartelesdecinajavafx;

import java.io.Serializable;

/**
 * Clase Cartel con un constructor formado por tres parámetros.
 *
 * @author Miguel Segovia Gil
 *
 */
public class Cartel implements Serializable {

    private String nombre;
    private int anyo;
    private String ruta;

    /**
     * Parámetros que acepta el constructor, nombre de cartel, año y ruta.
     *
     * @param nombre
     * @param anyo
     * @param ruta
     */
    public Cartel(String nombre, int anyo, String ruta) {
        this.nombre = nombre;
        this.anyo = anyo;
        this.ruta = ruta;
    }

    /**
     *
     * @return devuelve el nombre del cartel
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * setter para el nombre
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return getter para el año
     */
    public int getAnyo() {
        return anyo;
    }

    /**
     *
     * @param anyo setter para el año
     */
    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    /**
     *
     * @return getter para la ruta del cartel
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * setter para la ruta del cartel
     *
     * @param ruta
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Método toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Cartel{" + "nombre:" + nombre + ", año:" + anyo + ", ruta:" + ruta + '}';
    }

}
