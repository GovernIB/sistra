package org.ibit.rol.form.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un formulario con caracteristicas de seguridad.
 */
public class FormularioSeguro extends Formulario {

    /** Indica si el formulario debe ir sobre protocolo seguro https */
    private boolean https;

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    /** Indica si el formulario necesita un nombre de usuario para operar */
    private boolean requerirLogin;

    public boolean isRequerirLogin() {
        return requerirLogin;
    }

    public void setRequerirLogin(boolean requerirLogin) {
        this.requerirLogin = requerirLogin;
    }

    /** Indica si el formulario debe autenticar al usuario mediante un certificado cliente */
    private boolean requerirCertificado;

    public boolean isRequerirCertificado() {
        return requerirCertificado;
    }

    public void setRequerirCertificado(boolean requerirCertificado) {
        this.requerirCertificado = requerirCertificado;
    }

    /** Indica si el formulario requiere la firma del usuario */
    private boolean requerirFirma;

    public boolean isRequerirFirma() {
        return requerirFirma;
    }

    public void setRequerirFirma(boolean requerirFirma) {
        this.requerirFirma = requerirFirma;
    }

    /** Lista de roles de usuario que pueden acceder */
    private Set roles = new HashSet();

    public Set getRoles() {
        return roles;
    }

    protected void setRoles(Set roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public void removeRole(String role) {
        this.roles.remove(role);
    }

    public void removeAllRoles(){
        this.roles.clear();
    }

    /** En caso de requerir firma, lista de validadores de firma a usar */
    public Set validadores = new HashSet();

    public Set getValidadores() {
        return validadores;
    }

    protected void setValidadores(Set validadores) {
        this.validadores = validadores;
    }

    public void addValidador(ValidadorFirma validador) {
        this.validadores.add(validador);
    }

    public void removeValidador(ValidadorFirma validador) {
        this.validadores.remove(validador);
    }

    public void removeAllValidadores(){
        this.validadores.clear();
    }
}
