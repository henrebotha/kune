/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.gspace.client.actions;

// TODO: Auto-generated Javadoc
/**
 * A group of actions that must be grouped and showed in some perspective (on
 * edit, etc).
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class ActionGroups {

  /**
   * The Constant BOTTOMBAR identifies actions in the bottom bar, below the
   * content.
   */
  public final static String GROUP_HEADER_BOTTOM_BAR = "bottombar";

  /** The Constant TOPBAR identifies actions in the top bar, above the content. */

  public final static String DOC_HEADER_BAR = "doc_header_bar";
  public final static String DOC_TOP_TOOLBAR = "doc_top_toolbar";
  public final static String ENTITY_BOTTOMBAR = "entity_bottombar";

  /**
   * The Constant ITEM_MENU identifies actions in the menu of each item of a
   * folder list.
   */
  public final static String ITEM_MENU = "menu-item";

  /**
   * Instantiates a new action groups.
   */
  private ActionGroups() {
  }
}
