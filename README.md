# Google Play Test Automation
A test automation system to download apps from Google Play and checks if they are installed.

Google Play's UI may vary between different Android versions, so the ID specified in line 90 ("com.android.vending: id / metadata") may need to change for some devices.

Flow of the test program follows:

1- Open Google Play

2- Waits until the searchbox is visible at the screen.

3- Taps the searchbox and enter the keys that specified in line 30(appName)

4- If Google Play suggests an app for the user, it checks if that app is installed or not.

5- If app is installed, driver will report success and close. If it is not installed it will download the app and wait until it is installed. Driver will report success and cloes after installation.

6- If Google Play doesn't suggests an app for the user, it takes every app in the search list and checks the name of the apps one by one. If the application name contains "appName", this application is selected and the same steps as in step 4 are followed.
