/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.docs.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

public class ContentSetPublishedOnAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onContentsetPublishedOn(services, (Date) value);
    }

    private void onContentsetPublishedOn(final Services services, final Date publishedOn) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = services.session.getCurrentState();
        server.setPublishedOn(services.session.getUserHash(), currentState.getGroup().getShortName(), currentState
                .getDocumentId(), publishedOn, new AsyncCallbackSimple<Object>() {
            public void onSuccess(final Object result) {
                Site.hideProgress();
                services.app.getWorkspace().getContentTitleComponent().setContentDate(publishedOn);
            }
        });
    }
}
