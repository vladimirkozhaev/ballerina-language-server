package org.lsp;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.RenameFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

/**
 * WorkspaceService implementation for Ballerina.
 */
public class TestWorkSpaceService implements WorkspaceService {

    private TestLanguageServer languageServer;
    //LSClientLogger clientLogger;

    public TestWorkSpaceService(TestLanguageServer languageServer) {
        this.languageServer = languageServer;
        //this.clientLogger = LSClientLogger.getInstance();
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams didChangeConfigurationParams) {
        //this.clientLogger.logMessage("Operation 'workspace/didChangeConfiguration' Ack");
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams didChangeWatchedFilesParams) {
        //this.clientLogger.logMessage("Operation 'workspace/didChangeWatchedFiles' Ack");
    }

    @Override
    public void didRenameFiles(RenameFilesParams params) {
        //this.clientLogger.logMessage("Operation 'workspace/didRenameFiles' Ack");
    }
}
