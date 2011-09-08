/**
 * 
 */
package org.eclipse.xtext.bpel;

import java.util.List;

import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Custom resource to store the information about Bpel variables
 * 
 * @author bettini
 * 
 */
public class BpelInformationResource extends ResourceImpl {

	public void replaceBpelVariables(List<Variable> variables) {
		getContents().clear();

		for (Variable variable : variables) {
			// crucial to clone variables
			// otherwise they're removed from the bpel model!
			getContents().add(EcoreUtil2.clone(variable));
		}
	}

	public List<Variable> getVariables() {
		EList<EObject> contents = getContents();
		return EcoreUtil2.typeSelect(contents, Variable.class);
	}

	/**
	 * We must make sure it does not return a null URI which may
	 * disturb xtext when it analyzes the resource set
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#getURI()
	 */
	@Override
	public URI getURI() {
		return URI.createURI("dummy:/foo.bar");
	}
	
}
