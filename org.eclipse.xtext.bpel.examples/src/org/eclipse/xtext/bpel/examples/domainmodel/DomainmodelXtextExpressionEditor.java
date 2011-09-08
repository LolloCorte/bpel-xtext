package org.eclipse.xtext.bpel.examples.domainmodel;

import org.eclipse.xtext.bpel.expression.editor.XtextBasedExpressionEditor;
import org.eclipse.xtext.example.ui.internal.DomainmodelActivator;

import com.google.inject.Injector;

@SuppressWarnings("nls")
public class DomainmodelXtextExpressionEditor extends
		XtextBasedExpressionEditor {

	private static final String DOMAINMODEL_EDITOR_ID = "org.eclipse.xtext.example.domainmodel.xtexteditor";

	@Override
	protected String getTextEditorId() {
		return DOMAINMODEL_EDITOR_ID;
	}

	@Override
	protected Injector getInjector() {
		return DomainmodelActivator.getInstance().getInjector(
				"org.eclipse.xtext.example.Domainmodel");
	}

}
