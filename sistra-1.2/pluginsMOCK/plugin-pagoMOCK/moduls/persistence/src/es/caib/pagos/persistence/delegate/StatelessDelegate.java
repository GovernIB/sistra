package es.caib.pagos.persistence.delegate;

/**
 * Representa un delegate sin estado, i por tanto cacheable.
 */
public interface StatelessDelegate extends Delegate {
	// TODO Replantear si es posible en caso de cluster cachear homes 
}
