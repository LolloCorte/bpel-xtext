package org.xtext.example.mydsl.ui.document;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.validation.AnnotationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.ValidationJob;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MyDslExpressionDocumentProvider extends XtextDocumentProvider {
	
	@Inject
	private IResourceValidator resourceValidator;
	@Inject
	private IssueResolutionProvider issueResolutionProvider;
	
	private static Provider<XtextDocumentProvider>  provider=new Provider<XtextDocumentProvider>() {
		
		@Override
		public MyDslExpressionDocumentProvider get() {
			return new MyDslExpressionDocumentProvider();
		}
	};
	
	public MyDslExpressionDocumentProvider() {
		System.out.println();
	}
	
	
	public static Provider<XtextDocumentProvider> getProvider(){
		return provider;
	}
	@Override
	protected XtextDocument createEmptyDocument() {
		// TODO Auto-generated method stub
		return new MyDslExpressionDocument();
	}
	
//	@Override
//	protected ElementInfo createElementInfo(Object element)
//			throws CoreException {
//		ElementInfo info =new ElementInfo(createDocument(element), createAnnotationModel(element));
//		XtextDocument doc = (XtextDocument) info.fDocument;
//		AnnotationIssueProcessor annotationIssueProcessor = new AnnotationIssueProcessor(doc, info.fModel, issueResolutionProvider);
//		ValidationJob job = new ValidationJob(resourceValidator, doc, annotationIssueProcessor,CheckMode.FAST_ONLY);
//		doc.setValidationJob(job);
//		return info;
//	}
	
//	@Override
//	protected IDocument createDocument(Object element) throws CoreException {
//		if (element instanceof TextEditorInput) {
//			
//			TextEditorInput input = (TextEditorInput) element;
//			Document doc = new MyDocument( input );
//			input.setDocument ( doc );
//			
//			return doc;
//		}		
//		return null;
//	
//	}
//	
//	class MyDocument extends XtextDocument{
//		
////		private Document document;
//		
//		public MyDocument(TextEditorInput input) {
//			System.out.println(input);
////			setInput(input.getResource());
////			document=new Document(input);
//		}
//	}
}
