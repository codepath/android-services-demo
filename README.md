# Services Demo

This is an Android demo for services and notifications including:

 * Using an IntentService
 * Communicating between IntentService and Activity using ResultReceiver
 * Using AlarmManager
 * Downloading Image Asynchronously with ImageDownloadService

<img src="http://i.imgur.com/4JbNV99.png" width="250" />&nbsp;
<img src="http://i.imgur.com/0E7ec12.png" width="250" height="359" />&nbsp;
<img src="http://i.imgur.com/PxPfWK0.png" width="250" height="359" />&nbsp;

Read more in our cliffnotes at [Services](https://github.com/thecodepath/android_guides/wiki/Starting-Background-Services) and [Notifications](https://github.com/thecodepath/android_guides/wiki/Notifications) guides and check out the source!

## Use Cases

* Use case #1: Demo a simple intent service, sleep, then toast using ResultReceiver
* Use case #2: Demo a simple intent service, sleep, then display in Notification Center (first builder example)
* Use case #3: Upon clicking on a notification, go to a particular activity and display the notification information

* Use case #4: Demo a long download with a 10 second sleep.  Close the app, display in Notification Center when done.
* Use case #5: Show the downloaded image in the expansion view within notification.

* Use case #6: An AlarmManager fires a message that will be displayed in Notification Center
* Use case #7: Demonstrated “stacked notifications” in a repeating alarm that updates the existing notification.
