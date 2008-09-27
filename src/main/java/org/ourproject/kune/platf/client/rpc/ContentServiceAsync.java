/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.rpc;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {

    void addAuthor(String userHash, StateToken token, String authorShortName, AsyncCallback<?> asyncCallback);

    void addComment(String userHash, StateToken token, Long parentCommentId, String commentText,
	    AsyncCallback<CommentDTO> asyncCallback);

    void addComment(String userHash, StateToken token, String commentText, AsyncCallback<CommentDTO> asyncCallback);

    void addContent(String user, StateToken parentToken, String name, AsyncCallback<StateDTO> callback);

    void addFolder(String hash, StateToken parentToken, String title, String typeId, AsyncCallback<StateDTO> callback);

    void addRoom(String user, StateToken parentToken, String name, AsyncCallback<StateDTO> callback);

    void delContent(String userHash, StateToken token, AsyncCallback<?> asyncCallback);

    void getContent(String user, StateToken newState, AsyncCallback<StateDTO> callback);

    void getSummaryTags(String userHash, StateToken groupToken, AsyncCallback<List<TagResultDTO>> asyncCallback);

    void markCommentAsAbuse(String userHash, StateToken token, Long commentId, AsyncCallback<CommentDTO> asyncCallback);

    void rateContent(String userHash, StateToken token, Double value, AsyncCallback<?> asyncCallback);

    void removeAuthor(String userHash, StateToken token, String authorShortName, AsyncCallback<?> asyncCallback);

    void renameContainer(String userHash, StateToken token, String newName, AsyncCallback<String> asyncCallback);

    void renameContent(String userHash, StateToken token, String newName, AsyncCallback<String> asyncCallback);

    void save(String user, StateToken token, String content, AsyncCallback<Integer> asyncCallback);

    void setAsDefaultContent(String userHash, StateToken token, AsyncCallback<ContentSimpleDTO> asyncCallback);

    void setLanguage(String userHash, StateToken token, String languageCode,
	    AsyncCallback<I18nLanguageDTO> asyncCallback);

    void setPublishedOn(String userHash, StateToken token, Date publishedOn, AsyncCallback<?> asyncCallback);

    void setStatus(String userHash, StateToken stateToken, ContentStatusDTO status, AsyncCallback<?> asyncCallback);

    void setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatusDTO status,
	    AsyncCallback<?> asyncCallback);

    void setTags(String userHash, StateToken token, String tags, AsyncCallback<List<TagResultDTO>> asyncCallback);

    void voteComment(String userHash, StateToken token, Long commentId, boolean votePositive,
	    AsyncCallback<CommentDTO> asyncCallback);
}
