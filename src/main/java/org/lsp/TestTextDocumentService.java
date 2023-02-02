package org.lsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.lsp.TestDocumentModel.ProcessedLexeme;

public class TestTextDocumentService implements TextDocumentService {

	private TestLanguageServer languageServer;
	// private LSClientLogger clientLogger;

	public TestTextDocumentService(TestLanguageServer languageServer) {
		this.languageServer = languageServer;

	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		TestDocumentModel model = new TestDocumentModel(params.getTextDocument().getText());

		CompletableFuture.runAsync(() -> languageServer.languageClient
				.publishDiagnostics(new PublishDiagnosticsParams(params.getTextDocument().getUri(), validate(model))));
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {

		for (TextDocumentContentChangeEvent changeEvent : params.getContentChanges()) {
			TestDocumentModel model = new TestDocumentModel(changeEvent.getText());
			languageServer.languageClient.publishDiagnostics(
					new PublishDiagnosticsParams(params.getTextDocument().getUri(), validate(model)));
		}
	}

	@Override
	public void didClose(DidCloseTextDocumentParams didCloseTextDocumentParams) {
//        this.clientLogger.logMessage("Operation '" + "text/didClose" +
//                "' {fileUri: '" + didCloseTextDocumentParams.getTextDocument().getUri() + "'} Closed");
	}

	@Override
	public void didSave(DidSaveTextDocumentParams didSaveTextDocumentParams) {
//        this.clientLogger.logMessage("Operation '" + "text/didSave" +
//                "' {fileUri: '" + didSaveTextDocumentParams.getTextDocument().getUri() + "'} Saved");
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams position) {
		return CompletableFuture.supplyAsync(() -> {
			// this.clientLogger.logMessage("Operation '" + "text/completion");
			CompletionItem completionItem = new CompletionItem();
			completionItem.setLabel("Test completion item");
			completionItem.setInsertText("Test");
			completionItem.setDetail("Snippet");
			completionItem.setKind(CompletionItemKind.Snippet);
			return Either.forLeft(Arrays.asList(completionItem));
		});
	}

	private List<Diagnostic> validate(TestDocumentModel model) {
		List<Diagnostic> res = new ArrayList<>();

		for (ProcessedLexeme lexeme : model.lines) {
			if (lexeme.text.equals("basketball")) {
				Diagnostic diagnostic = new Diagnostic();
				diagnostic.setSeverity(DiagnosticSeverity.Error);
				diagnostic.setMessage("I like basketball so mach");
				diagnostic.setRange(new Range(new Position(lexeme.line, lexeme.charOffset),
						new Position(lexeme.line, lexeme.charOffset + lexeme.text.length())));
				res.add(diagnostic);
			} else {
				Diagnostic diagnostic = new Diagnostic();
				diagnostic.setSeverity(DiagnosticSeverity.Warning);
				diagnostic.setMessage("I also like futball but something less");
				diagnostic.setRange(new Range(new Position(lexeme.line, lexeme.charOffset),
						new Position(lexeme.line, lexeme.charOffset + lexeme.text.length())));
				res.add(diagnostic);
			}

		}
		return res;
	}
}