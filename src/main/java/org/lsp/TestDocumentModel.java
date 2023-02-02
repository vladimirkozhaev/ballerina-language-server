package org.lsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDocumentModel {

	public class ProcessedLexeme {
		final int line;
		final String text;
		final int charOffset;

		protected ProcessedLexeme(int line, int charOffset, String text) {
			this.line = line;
			this.charOffset = charOffset;
			this.text = text;
		}
	}

	protected final List<ProcessedLexeme> lines = new ArrayList<>();

	public TestDocumentModel(String text) {
		try (Reader r = new StringReader(text); BufferedReader reader = new BufferedReader(r);) {
			Pattern pattern = Pattern.compile("basketball|futball", Pattern.CASE_INSENSITIVE);
			String lineText;
			int lineNumber = 0;

			while ((lineText = reader.readLine()) != null) {

				Matcher matcher = pattern.matcher(lineText);
				while (matcher.find()) {
					ProcessedLexeme lexeme = new ProcessedLexeme(lineNumber, matcher.start(),
							lineText.substring(matcher.start(), matcher.end()));
					lines.add(lexeme);

				}

				lineNumber++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ProcessedLexeme> getResolvedLines() {
		return Collections.unmodifiableList(this.lines);
	}

}
