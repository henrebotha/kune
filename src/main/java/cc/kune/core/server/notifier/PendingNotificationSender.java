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
package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import cc.kune.core.server.utils.FormattedString;
import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class PendingNotificationSender is used to store and send pending
 * notifications via cron tasks.
 */
@Singleton
public class PendingNotificationSender {

  /** The Constant NO_NEXT. */
  private static final Collection<PendingNotificationProvider> NO_NEXT = null;

  // FIXME this should be in DB so we'll don't lose them in server restarts
  /** The daily pend notif. list */
  private final ConcurrentLinkedQueue<PendingNotificationProvider> dailyPendNotif;

  // FIXME this should be in DB so we'll don't lose them in server restarts
  /** The houry pend notif. list */
  private final ConcurrentLinkedQueue<PendingNotificationProvider> hourlyPendNotif;

  /** The immediate pend notif. list */
  private final ConcurrentLinkedQueue<PendingNotificationProvider> immediatePendNotif;

  /** The sender. */
  private final NotificationSender sender;

  /**
   * Instantiates a new pending notifications manager.
   * 
   * @param sender
   *          the sender
   */
  @Inject
  public PendingNotificationSender(final NotificationSender sender) {
    this.sender = sender;
    immediatePendNotif = new ConcurrentLinkedQueue<PendingNotificationProvider>();
    hourlyPendNotif = new ConcurrentLinkedQueue<PendingNotificationProvider>();
    dailyPendNotif = new ConcurrentLinkedQueue<PendingNotificationProvider>();
    // immediatePendNotif = Collections.synchronizedSet(new
    // LinkedHashSet<PendingNotificationProvider>());
    // hourlyPendNotif = Collections.synchronizedSet(new
    // LinkedHashSet<PendingNotificationProvider>());
    // dailyPendNotif = Collections.synchronizedSet(new
    // LinkedHashSet<PendingNotificationProvider>());
  }

  public void add(final NotificationType type, final String subjectPrefix,
      final FormattedString subject, final FormattedString body, final boolean isHtml,
      final boolean forceSend, final User to) {
    add(new PendingNotificationProvider() {
      @Override
      public PendingNotification get() {
        return new PendingNotification(type, subjectPrefix, subject, body, isHtml, forceSend,
            new SimpleDestinationProvider(to));
      }
    });
  }

  /**
   * Adds a pending notification.
   * 
   * @param notification
   *          the notification
   */
  public void add(final PendingNotificationProvider notificationProv) {
    if (!immediatePendNotif.contains(notificationProv) && !hourlyPendNotif.contains(notificationProv)) {
      // If we have send a similar notification this hour just ignore
      immediatePendNotif.add(notificationProv);
    }
  }

  /**
   * Gets the daily pending notifications count.
   * 
   * @return the daily count
   */
  public int getDailyCount() {
    return dailyPendNotif.size();
  }

  /**
   * Gets the hourly pending notifications count.
   * 
   * @return the hourly count
   */
  public int getHourlyCount() {
    return hourlyPendNotif.size();
  }

  /**
   * Gets the immediate pending notifications count.
   * 
   * @return the immediate count
   */
  public int getImmediateCount() {
    return immediatePendNotif.size();
  }

  /**
   * Send some list of notifications (called vía cron).
   * 
   * @param list
   *          the list to process
   * @param nextList
   *          the next list to store (we have a list of Users some that wants an
   *          immediate notification and other that want periodical
   *          notifications instead.
   * @param currentFreq
   *          The current frequency of notifications, that is, we'll send
   *          notifications only to users with this frequency configured
   */
  private void send(final Collection<PendingNotificationProvider> list,
      final Collection<PendingNotificationProvider> nextList,
      final EmailNotificationFrequency currentFreq) {
    // http://en.wikipedia.org/wiki/Thread_pool_pattern
    while (!list.isEmpty()) {
      final PendingNotificationProvider notificationProv = ((ConcurrentLinkedQueue<PendingNotificationProvider>) list).poll();
      final PendingNotification notification = notificationProv.get();
      if (!notification.equals(PendingNotification.NONE)) {
        sender.send(notification, currentFreq);
        if (!notification.isForceSend() && nextList != NO_NEXT) {
          // Forced Send, are send immediately (independent of how User has its
          // notification configured)!
          if (!nextList.contains(notificationProv)) {
            // We only add to the next queue if does not exists
            nextList.add(notificationProv);
          }
        }
      }
    }
  }

  /**
   * Send daily notifications.
   */
  public void sendDailyNotifications() {
    send(dailyPendNotif, NO_NEXT, EmailNotificationFrequency.daily);
  }

  /**
   * Send hourly notifications.
   */
  public void sendHourlyNotifications() {
    send(hourlyPendNotif, dailyPendNotif, EmailNotificationFrequency.hourly);
  }

  /**
   * Send immediate notifications.
   */
  public void sendImmediateNotifications() {
    send(immediatePendNotif, hourlyPendNotif, EmailNotificationFrequency.immediately);
  }

  @Override
  public String toString() {
    return "pendingNotifications: [" + getImmediateCount() + ", " + getHourlyCount() + ", "
        + getDailyCount() + "]";
  }
}
