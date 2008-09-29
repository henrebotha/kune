package org.ourproject.kune.workspace.client.sitebar.siteusermenu;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;

public class SiteUserMenuPresenter implements SiteUserMenu {

    private SiteUserMenuView view;
    private final MenuItemCollection<GroupDTO> participateInGroups;
    private final StateManager stateManager;
    private final Session session;

    public SiteUserMenuPresenter(final Session session, final StateManager stateManager) {
	this.session = session;
	this.stateManager = stateManager;
	participateInGroups = new MenuItemCollection<GroupDTO>();
	session.onUserSignIn(new Listener<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO userInfoDTO) {
		onUserSignIn(userInfoDTO);
	    }

	});
	session.onUserSignOut(new Listener0() {
	    public void onEvent() {
		view.setVisible(false);
		view.setLoggedUserName("");
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final SiteUserMenuView view) {
	this.view = view;
    }

    public void onUserHomePage() {
	stateManager.gotoToken(session.getCurrentUserInfo().getShortName());
    }

    public void onUserPreferences() {
	// TODO Auto-generated method stub
    }

    private void addPartipation(final GroupDTO group) {
	participateInGroups.add(new MenuItem<GroupDTO>("images/group-def-icon.gif", group.getShortName(),
		new Listener<GroupDTO>() {
		    public void onEvent(final GroupDTO param) {
			stateManager.gotoToken(group.getShortName());
		    }
		}));
    }

    private void onUserSignIn(final UserInfoDTO userInfoDTO) {
	view.setVisible(true);
	view.setLoggedUserName(userInfoDTO.getShortName());
	participateInGroups.clear();
	for (final GroupDTO group : userInfoDTO.getGroupsIsAdmin()) {
	    addPartipation(group);
	}
	for (final GroupDTO group : userInfoDTO.getGroupsIsCollab()) {
	    addPartipation(group);
	}
	view.setParticipation(participateInGroups);
    }

}