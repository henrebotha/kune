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
package org.ourproject.kune.platf.client.ui.dialogs;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class ExtendedDialog extends BasicDialog {

    private final Button cancel;
    private final Button firstButton;

    public ExtendedDialog(final String dialogId, final String title, final boolean modal,
            final boolean autoscroll, final int width, final int heigth, final String icon,
            final String firstButtonTitle, final String cancelButtonTitle, final Listener0 onFirstButtonClick,
            final Listener0 onCancelButtonClick, final int tabIndexStart) {
        this(dialogId, title, modal, autoscroll, width, heigth, icon, firstButtonTitle, Ext.generateId(),
                cancelButtonTitle, Ext.generateId(), onFirstButtonClick, onCancelButtonClick, tabIndexStart);
    }

    public ExtendedDialog(final String dialogId, final String title, final boolean modal,
            final boolean autoscroll, final int width, final int heigth, final String icon,
            final String firstButtonTitle, final String firstButtonId, final String cancelButtonTitle,
            final String cancelButtonId, final Listener0 onFirstButtonClick, final Listener0 onCancelButtonClick,
            final int tabIndexStart) {
        super(dialogId, title, modal, autoscroll, width, heigth);
        setLayout(new FitLayout());
        setCollapsible(false);
        setButtonAlign(Position.RIGHT);
        setIconCls(icon);

        firstButton = new Button(firstButtonTitle);
        firstButton.setId(firstButtonId);
        firstButton.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject event) {
                onFirstButtonClick.onEvent();
            }
        });

        firstButton.setTabIndex(tabIndexStart);

        cancel = new Button(cancelButtonTitle);
        cancel.setId(cancelButtonId);
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject event) {
                onCancelButtonClick.onEvent();
            }
        });
        cancel.setTabIndex(tabIndexStart + 1);
        addButton(firstButton);
        addButton(cancel);
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getFirstButton() {
        return firstButton;
    }

    public void mask(final String message) {
        getEl().mask(message, "x-mask-loading");
    }

    public void unMask() {
        getEl().unmask();
    }
}