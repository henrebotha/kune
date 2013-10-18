/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ShareItemFactory {

  @Inject
  private static Provider<ShareToEntity> shareToEntity;
  @Inject
  private static Provider<ShareToEveryone> shareToEveryone;
  @Inject
  private static Provider<ShareToNobody> shareToNone;

  // @Inject
  // ShareItemFactory(final Provider<ShareToEveryone> shareToEveryone,
  // final Provider<ShareToNobody> shareToNone, final Provider<ShareToEntity>
  // shareToEntity) {
  // this.shareToEveryone = shareToEveryone;
  // this.shareToNone = shareToNone;
  // this.shareToEntity = shareToEntity;
  // }

  public static ShareToEntity getEntity() {
    return shareToEntity.get();
  }

  public static ShareToEveryone getShareToEveryone() {
    return shareToEveryone.get();
  }

  public static ShareToNobody getShareToNobody() {
    return shareToNone.get();
  }

  public ShareItemFactory() {
  }
}