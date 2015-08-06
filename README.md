# Kismet_Wardriving_Suite
Android tool for automatization of kismet_server, gpsd and giskismet in a chrooted environment.

#Setup:

1) Copy the KismetWardrivingSuite.sh script to your chrooted Linux environment in /scripts/

2) Install the .apk that you can find under /app/build/outputs/apk

3) Make sure you installed kismet, gpsd, giskismet and BlueNMEA

#Changelog:

First Commit: This early release is meant to be used in conjunction with Linux Deploy by Meefik. The app connects back to the SSH server it starts and issues commands trough the Jsch Java library. Default password is set as "changeme", so don't change it in Linux Deploy.

Second Commit: The app has been updated, look at the changelog for more infos aout the features. This time, you will not be force to use only Linux Deploy's settings, however running SSH on port 22 is still a must. Still on the bleeding edge and may contain bugs. As a storing path, you must enter the path on your linux chrooted environment. To save on the sdcard, mount it on Linux Deploy or your chroot manager and insert the right path.
New Features:
-Better handling of the output that seems to crash the app when too big.
-Ability to change password, host, username and storing place.
-Ability of request statistics about the captures, for now only showing the number of total captured APs available.
-Error handling in case the connection went down or was not set up.
-All of this, in a brand new Tab called "Hub".

#Coming soon:

-Ability of cleaning /root from the Kismet logs and storing them.

-Ability to handle the output of the different commands in different ways.

-Ability to change the port to connect back to the SSH server.

-Ability to install the KismetWardrivingSuite script without exiting the application.
