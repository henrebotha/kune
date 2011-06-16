/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.content;

import java.net.URL;

import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerWaveTool;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;
import cc.kune.wave.server.KuneWaveManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CreationServiceDefault implements CreationService {
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  private final ServerToolRegistry tools;

  @Inject
  public CreationServiceDefault(final ContainerManager containerManager,
      final ContentManager contentManager, final ServerToolRegistry toolRegistry) {
    this.containerManager = containerManager;
    this.contentManager = contentManager;
    this.tools = toolRegistry;
  }

  @Override
  public Content createContent(final String title, final String body, final User user,
      final Container container, final String typeId) {
    final String toolName = container.getToolName();
    final ServerTool tool = tools.get(toolName);
    tool.checkTypesBeforeContentCreation(container.getTypeId(), typeId);
    final URL gagdetUrl = tool instanceof ServerWaveTool ? ((ServerWaveTool) tool).getGadgetUrl()
        : KuneWaveManager.WITHOUT_GADGET;
    final Content content = contentManager.createContent(title, body, user, container, typeId, gagdetUrl);
    tool.onCreateContent(content, container);
    return content;
  }

  @Override
  public Container createFolder(final Group group, final Long parentFolderId, final String name,
      final I18nLanguage language, final String typeId) {
    final Container parent = containerManager.find(parentFolderId);
    final String toolName = parent.getToolName();
    tools.get(toolName).checkTypesBeforeContainerCreation(parent.getTypeId(), typeId);
    final Container child = containerManager.createFolder(group, parent, name, language, typeId);
    tools.get(toolName).onCreateContainer(child, parent);
    return child;
  }

  @Override
  public Content saveContent(final User editor, final Content content, final String body) {
    return contentManager.save(editor, content, body);
  }

}