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
package cc.kune.core.server;


import cc.kune.core.server.manager.UserManager;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserSession {
    private String userHash;
    private Long userId;
    private final UserManager manager;

    @Inject
    public UserSession(final UserManager manager) {
        this.manager = manager;
    }

    public String getHash() {
        return userHash;
    }

    public User getUser() {
        return manager.find(userId);
    }

    public boolean isUserLoggedIn() {
        return userId != null;
    }

    public boolean isUserNotLoggedIn() {
        return !isUserLoggedIn();
    }

    public void login(final User user, final String newUserHash) {
        setUser(user);
        setHash(newUserHash);
    }

    public void logout() {
        userId = null;
        userHash = null;
    }

    private void setHash(final String hash) {
        this.userHash = hash;
    }

    private void setUser(final User user) {
        userId = user.getId();
    }

}