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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EntityOptGeneralPanel extends DefaultForm implements EntityOptGeneralView {
  private final MaskWidget maskWidget;
  private final IconLabel tabTitle;

  public EntityOptGeneralPanel(final I18nTranslationService i18n, final CoreResources res,
      final MaskWidget maskWidget) {
    this.maskWidget = maskWidget;
    tabTitle = new IconLabel(res.emblemSystem(), i18n.t("General"));
    super.setWidth(EntityOptionsView.WIDTH);
    super.setFrame(true);
    super.getFormPanel().setLabelWidth(100);
    super.addStyleName("k-overflow-y-auto");
    final Label label = new Label();
    label.setText(i18n.t("Here you can change this values:"));
    label.addStyleName("kune-Margin-10-tb");
    super.add(label);
  }

  @Override
  public Widget asWidget() {
    return super.getFormPanel();
  }

  @Override
  public void clear() {
    super.getFormPanel().removeAll();
    super.reset();
  }

  private void doLayoutIfNeeded() {
    if (super.getFormPanel().isRendered()) {
      super.getFormPanel().layout();
    }
  }

  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  @Override
  public void mask() {
    maskWidget.mask(this);
  }

  @Override
  public void setChangeHandler(final ChangeHandler changeHandler) {
    final Listener<BaseEvent> listener = new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        changeHandler.onChange(null);
      }
    };
    for (@SuppressWarnings("rawtypes")
    final Field field : super.getFormPanel().getFields()) {
      // field.addListener(Events.Change, listener);
      field.addListener(Events.Blur, listener);
      field.addListener(Events.OnKeyPress, new Listener<FieldEvent>() {
        @Override
        public void handleEvent(final FieldEvent fe) {
          if (fe.getEvent().getKeyCode() == 13) {
            changeHandler.onChange(null);
          }
        }
      });
    }
  }

  @Override
  public void unmask() {
    maskWidget.unMask();
  }
}