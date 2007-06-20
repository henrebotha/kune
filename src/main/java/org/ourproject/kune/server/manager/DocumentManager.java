package org.ourproject.kune.server.manager;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.ourproject.kune.client.rpc.dto.KuneDoc;
import org.ourproject.kune.server.dao.DocumentDao;
import org.ourproject.kune.server.dao.DocumentDaoJCR;

public class DocumentManager {
    private final DocumentDao documentDao;

    public DocumentManager() {
	documentDao = new DocumentDaoJCR();
    }

    public KuneDoc readRootDocument(Session session) throws RepositoryException {
	return documentDao.getRoot(session);
    }

    public void saveDocument(KuneDoc doc, Session session) throws RepositoryException {
	documentDao.saveDocument(session, doc);
    }
}
