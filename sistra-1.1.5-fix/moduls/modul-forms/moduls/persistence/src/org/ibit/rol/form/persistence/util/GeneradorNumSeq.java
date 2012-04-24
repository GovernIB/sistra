/**
 * Singleton para ejecutar operaci�n at�mica de aumentar el n�mero
 * de sequ�ncia de un formulario.
 */
package org.ibit.rol.form.persistence.util;

import net.sf.hibernate.Session;
import net.sf.hibernate.HibernateException;
import org.ibit.rol.form.model.Formulario;

public class GeneradorNumSeq {

    private static GeneradorNumSeq instancia;

    public synchronized static GeneradorNumSeq getInstance() {
        if (instancia == null) {
            instancia = new GeneradorNumSeq();
        }
        return instancia;
    }

    private GeneradorNumSeq() {
    }

    public synchronized long generar(Session session, Formulario form) throws HibernateException {
        session.refresh(form);
        long numSeq = form.nextUltNumSeq();
        session.flush();
        return numSeq;
    }
}

