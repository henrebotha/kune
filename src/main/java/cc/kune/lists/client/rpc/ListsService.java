/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.lists.client.rpc;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ListsService")
public interface ListsService extends RemoteService {

  StateContainerDTO createList(String hash, StateToken parentToken, String name, String description,
      boolean isPublic);

  public StateContentDTO newPost(final String userHash, final StateToken parentToken, final String title);

  StateContainerDTO setPublic(String hash, StateToken token, Boolean isPublic);

  StateContainerDTO subscribeAnUserToList(String hash, StateToken token, String newSubscriber,
      Boolean subscribe);

  StateContainerDTO subscribeMyselfToList(String hash, StateToken token, Boolean subscribe);

}
