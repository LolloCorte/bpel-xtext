package org.eclipse.xtext.bpel.expression.editor;

import java.io.File;
import java.io.IOException;

import org.eclipse.bpel.ui.expressions.AbstractExpressionEditor;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.properties.TextSection;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.bpel.util.BpelModelUtil;
import org.eclipse.xtext.bpel.util.ProjectUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * This is the base class for an ExpressionEditor for bpel, based on a language
 * implemented in Xtext.
 * 
 * @author bettini
 * 
 */
@SuppressWarnings("nls")
public abstract class XtextBasedExpressionEditor extends
		AbstractExpressionEditor {

	protected Composite fEditorComposite;

	protected IPropertyListener fPropertyListener;

	protected boolean updating = false;

	private XtextEditor xtextEditor;

	private File tempResourceFile;

	/**
	 * 
	 * @see org.eclipse.bpel.ui.expressions.AbstractExpressionEditor#getEditorContent()
	 */
	@Override
	public String getEditorContent() {
		if (xtextEditor != null) {
			return xtextEditor.getDocument().get();
		}
		return null;
	}

	/**
	 * Must return the editor id as specified in the extension point
	 * org.eclipse.bpel.common.ui.embeddedEditors
	 * 
	 * @return
	 */
	protected abstract String getTextEditorId();

	/**
	 * Must return the {@link Injector} of your dsl implemented in Xtext. For
	 * instance, if your dsl is called org.xtext.example.mydsl.MyDsl, you can
	 * obtain the injector from the dsl ui plugin as follows
	 * 
	 * <pre>
	 * MyDslActivator.getInstance().getInjector(&quot;org.xtext.example.mydsl.MyDsl&quot;);
	 * </pre>
	 * 
	 * @return
	 */
	protected abstract Injector getInjector();

	/**
	 * 
	 * @see org.eclipse.bpel.ui.expressions.AbstractExpressionEditor#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.bpel.ui.properties.BPELPropertySection)
	 */
	@Override
	public void createControls(Composite parent, BPELPropertySection aSection) {
		super.createControls(parent, aSection);
		createEditor(parent);
	}

	/**
	 * @see org.eclipse.bpel.ui.expressions.AbstractExpressionEditor#setEditorContent(java.lang.String)
	 */
	@Override
	public void setEditorContent(String contents) {
		updating = true;
		contents = ensureNotNullContents(contents);
		if (editorContentsNeedToBeUpdated(contents)) {
			try {
				FileEditorInput fInput = createEditorInput(contents);
				if (xtextEditor == null) {
					xtextEditor = (XtextEditor) createEditor(getTextEditorId(),
							fInput, fEditorComposite);
				} else {
					xtextEditor.setInput(fInput);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		updating = false;
	}

	protected boolean editorContentsNeedToBeUpdated(String contents) {
		contents = ensureNotNullContents(contents);
		return xtextEditor == null || !contents.equals(getEditorContent());
	}

	private String ensureNotNullContents(String contents) {
		if (contents == null) {
			String defaultContent = getDefaultContent();
			contents = (defaultContent != null ? defaultContent : "");
		}
		return contents;
	}

	protected FileEditorInput createEditorInput(String contents)
			throws IOException {
		if (tempResourceFile != null)
			tempResourceFile.delete();
		tempResourceFile = ProjectUtils.createTemporaryFileInCurrentProject(
				getCurrentFileExtension(), contents);
		IFile iFile = ProjectUtils.getIFileFromFile(tempResourceFile);
		FileEditorInput fInput = new FileEditorInput(iFile);
		return fInput;
	}

	protected String getCurrentFileExtension() {
		Injector injector = getInjector();
		String instance = injector.getInstance(Key.get(String.class,
				Names.named(Constants.FILE_EXTENSIONS)));
		if (instance.indexOf(',') == -1)
			return instance;
		return instance.split(",")[0];
	}

	@SuppressWarnings("nls")
	protected void createEditor(Composite parent) {
		TabbedPropertySheetWidgetFactory wf = getWidgetFactory();

		fEditorComposite = wf.createComposite(parent, SWT.BORDER);
		fEditorComposite.setLayout(new FillLayout());
	}

	protected XtextResource getXtextResource() {
		if (xtextEditor == null)
			return null;
		
		IXtextDocument document = xtextEditor.getDocument();
		XtextResource resource = document
				.readOnly(new IUnitOfWork<XtextResource, XtextResource>() {

					@Override
					public XtextResource exec(XtextResource state)
							throws Exception {
						return state;
					}
				});

		return resource;
	}
	
	protected IXtextDocument getXtextDocument() {
		return xtextEditor.getDocument();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#dispose()
	 */
	public void dispose() {
		disposeEditor();
	}

	protected void disposeEditor() {

		disposeXtextEditor();

		if (fEditorComposite != null && !fEditorComposite.isDisposed()) {
			fEditorComposite.dispose();
			fEditorComposite = null;
		}
	}

	protected void disposeXtextEditor() {
		if (xtextEditor != null) {
			getEditorManager().disposeEditor(xtextEditor);
			xtextEditor = null;
			tempResourceFile.delete();
		}
	}

	/**
	 * About to be Hidden.
	 */

	public void aboutToBeHidden() {
		if (xtextEditor != null) {
			xtextEditor.removePropertyListener(getPropertyListener());
		}
	}

	/**
	 * Editor is about to be shown.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#aboutToBeShown()
	 */

	public void aboutToBeShown() {
		if (xtextEditor != null) {
			xtextEditor.addPropertyListener(getPropertyListener());
		}
	}

	/**
	 * If the editor is dirty it registers an ongoing change.
	 */
	protected IPropertyListener getPropertyListener() {

		if (fPropertyListener == null) {
			fPropertyListener = new IPropertyListener() {
				@SuppressWarnings("synthetic-access")
				public void propertyChanged(Object source, int propId) {
					if (!updating
							&& propId == IEditorPart.PROP_DIRTY
							&& xtextEditor.isDirty()
							&& !((TextSection) fSection)
									.isExecutingStoreCommand()) {
						notifyChanged();
					}
				}
			};
		}
		return fPropertyListener;
	}

	/**
	 * Get the user context to remember for next invocation.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#getUserContext()
	 */
	public Object getUserContext() {
		return null;
	}

	/**
	 * Restore the user context.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#restoreUserContext(java.lang.Object)
	 */

	public void restoreUserContext(Object userContext) {
		xtextEditor.setFocus();
	}

	/**
	 * Return the default body for this type of expression. Since the editor is
	 * not aware of any syntax for any particular language, the empty string is
	 * returned.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#getDefaultContent()
	 */
	public String getDefaultContent() {
		return ""; //$NON-NLS-1$
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#gotoTextMarker(org.eclipse.core.resources.IMarker,
	 *      java.lang.String, java.lang.Object)
	 */
	public void gotoTextMarker(IMarker marker, String codeType,
			Object modelObject) {
		// TODO: Goto text marker in default text editor.
	}

	/**
	 * Answer true, because a generic text editor will simply do everything.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#supportsExpressionType(java.lang.String)
	 */

	public boolean supportsExpressionType(String exprType) {
		return true;
	}

	/**
	 * Mark it clean.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#markAsClean()
	 */
	public void markAsClean() {
		if (xtextEditor != null) {
			xtextEditor.doSave(null);
		}
	}

	/**
	 * Call this method if you want the Bpel variables available in this context
	 * to be put in the resource set of the model of the xtext DSL.
	 */
	protected void updateBpelVariables() {
		XtextResource resource = getXtextResource();

		EObject modelObject = (EObject) getModelObject();
		addBpelVariablesToXtextResource(resource, modelObject);
	}

	protected void addBpelVariablesToXtextResource(XtextResource resource,
			EObject modelObject) {
		BpelModelUtil.addBpelVariablesToXtextResource(resource, modelObject);
	}

}
