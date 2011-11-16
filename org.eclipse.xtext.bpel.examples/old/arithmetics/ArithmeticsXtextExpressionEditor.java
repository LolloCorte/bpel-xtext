package org.eclipse.xtext.bpel.examples.arithmetics;

import org.eclipse.xtext.bpel.expression.editor.XtextBasedExpressionEditor;
import org.eclipse.xtext.example.arithmetics.ui.internal.ArithmeticsActivator;

import com.google.inject.Injector;

@SuppressWarnings("nls")
public class ArithmeticsXtextExpressionEditor extends
		XtextBasedExpressionEditor {

	private static final String ARITHMETICS_EDITOR_ID = "org.eclipse.xtext.example.arithmetics.xtexteditor";

	@Override
	protected String getTextEditorId() {
		return ARITHMETICS_EDITOR_ID;
	}

	@Override
	protected Injector getInjector() {
		return ArithmeticsActivator.getInstance().getInjector(
				"org.eclipse.xtext.example.arithmetics.Arithmetics");
	}

}
