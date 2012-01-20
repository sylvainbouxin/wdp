package org.whtcorp.wdp.utils;

import java.util.concurrent.CountDownLatch;
import javax.management.*;
import com.ibm.websphere.management.*;
import com.ibm.websphere.management.application.AppNotification;

public class EventsNotificationListener implements NotificationListener {

	AdminClient adminClient;
	ObjectName on;
	String eventTypeToCheck;
	AppNotification notificationUserData;
	boolean _sleep = true;
	CountDownLatch latch;

	public boolean is_sleep() {
		return _sleep;
	}

	public EventsNotificationListener(AdminClient adminClient, NotificationFilterSupport notificationTypes, Object handback, String eventType, CountDownLatch latch) throws Exception
	{
		this.adminClient = adminClient;
		this.eventTypeToCheck = eventType;
		this.latch = latch;

		on = (ObjectName)adminClient.queryNames (new ObjectName("WebSphere:type=AppManagement,*"), null).iterator().next();

		adminClient.addNotificationListener(on, this, notificationTypes, handback);
		System.out.println("[INFO] ------------------------------------------------------------------------");
	}

	public void handleNotification (Notification notf, Object handback)
	{
         notificationUserData = (AppNotification) notf.getUserData();
         System.out.println("[INFO] " + handback+ " --- " + notificationUserData.taskName + " " + notificationUserData.subtaskName + " " + notificationUserData.taskStatus);
         
         removeListenerWhenEventFinished();
	}
	
	private void removeListenerWhenEventFinished() {
		
		if (notificationUserData.taskName.equals(eventTypeToCheck) && (notificationUserData.taskStatus.equals(AppNotification.STATUS_COMPLETED) || notificationUserData.taskStatus.equals(AppNotification.STATUS_FAILED))) {
			System.out.println("\n end of notification \n");
	       	try
	        {
	       		adminClient.removeNotificationListener (on, this);
	       		System.out.println("[INFO] ------------------------------------------------------------------------");
	       		latch.countDown();
	        } catch (Throwable th) {
	        	System.out.println ("[INFO] Error removing listener: " + th);
	        	System.out.println("[INFO] ------------------------------------------------------------------------");
	        	latch.countDown();
	        }
	    }
	}
}
