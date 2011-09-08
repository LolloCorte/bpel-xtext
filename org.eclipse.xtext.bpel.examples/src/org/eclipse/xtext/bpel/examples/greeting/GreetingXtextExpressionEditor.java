package org.eclipse.xtext.bpel.examples.greeting;

import org.eclipse.xtext.bpel.expression.editor.XtextBasedExpressionEditor;
import org.xtext.example.mydsl.ui.internal.MyDslActivator;

import com.google.inject.Injector;

@SuppressWarnings("nls")
public class GreetingXtextExpressionEditor extends XtextBasedExpressionEditor {

	private static final String GREETING_EDITOR_ID = "org.xtext.example.mydsl.ui.bridge.xtexteditor";

	@Override
	protected String getTextEditorId() {
		return GREETING_EDITOR_ID;
	}

	@Override
	protected Injector getInjector() {
		return MyDslActivator.getInstance().getInjector(
				"org.xtext.example.mydsl.MyDsl");
	}

}
