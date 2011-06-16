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
package cc.kune.core.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.sn.actions.RolComparator;
import cc.kune.core.client.state.AccessRightsChangedEvent;
import cc.kune.core.client.state.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

/**
 * This is a RolAction (a Action that store which permissions are needed to
 * permit its execution) but that auto refresh its status depending on the state
 * of the application (if we are authenticated, and so on)
 * 
 */
public abstract class RolActionAutoUpdated extends AbstractExtendedAction {
  protected final Session session;
  protected final StateManager stateManager;

  @Inject
  public RolActionAutoUpdated(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager, final AccessRolDTO rolRequired,
      final boolean authNeed, final boolean visibleForNonMemb, final boolean visibleForMembers) {
    this.stateManager = stateManager;
    this.session = session;
    rightsManager.onRightsChanged(true, new AccessRightsChangedHandler() {
      @Override
      public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
        refreshStatus(rolRequired, authNeed, session.isLogged(), visibleForMembers, visibleForNonMemb,
            event.getCurrentRights());

      }
    });
  }

  public void refreshStatus(final AccessRolDTO rolRequired, final boolean authNeed,
      final boolean isLogged, final boolean visibleForMembers, final boolean visibleForNonMemb,
      final AccessRights newRights) {
    boolean newVisibility = false;
    boolean newEnabled = false;
    if (authNeed && !isLogged) {
      newVisibility = newEnabled = false;
    } else {
      // Auth ok
      newEnabled = RolComparator.isEnabled(rolRequired, newRights);
      if (newEnabled) {
        final boolean isMember = RolComparator.isMember(newRights);
        newEnabled = newVisibility = isMember && visibleForMembers || !isMember && visibleForNonMemb;
      } else {
        newVisibility = false;
      }
    }
    setEnabled(newEnabled);
    // Workaround to force change ...
    putValue(GuiActionDescrip.VISIBLE, !newVisibility);
    putValue(GuiActionDescrip.VISIBLE, newVisibility);
    // NotifyUser.info("Set '" + getValue(Action.NAME) + "' visible: " +
    // newVisibility, true);
  }
}