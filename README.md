# Kismet_Wardriving_Suite
Android tool for automatization of kismet_server, gpsd and giskismet in a chrooted environment.

Setup:

1) Copy the Giskismet.sh to your chrooted Linux environment in /scripts/

2) Install the .apk that you can find under /app/build/outputs/apk

3) Make sure you installed kismet, gpsd, giskismet and BlueNMEA

This early release is meant to be used in conjunction with Linux Deploy by Meefik. The app connects back to the SSH server it starts and issues commands trough the Jsch Java library. Default password is set as "changeme", so don't change it in Linux Deploy.

The app was built with Android Studio
