package org.lsp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lsp.TestDocumentModel.ProcessedLexeme;

public class TestScanFolder {
	
	@Test
	public void testOneLineModel() {
		TestDocumentModel oneLineModel = new TestDocumentModel("basketball, futball, socker");
		assertEquals(2, oneLineModel.getResolvedLines().size());
		assertEquals("basketball", oneLineModel.getResolvedLines().get(0).text);
	}

	@Test
	public void testMultilineModel() {
		TestDocumentModel mutlilneModel = new TestDocumentModel("\nbasketball, futball, socker\r futball");
		assertEquals(3, mutlilneModel.getResolvedLines().size());
		ProcessedLexeme processedLexeme = mutlilneModel.getResolvedLines().get(2);
		assertEquals("futball", processedLexeme.text);
		assertEquals(2, processedLexeme.line);
		assertEquals(1, processedLexeme.charOffset);
	}
}
