NETXML="$(ls | grep .netxml | tail -1)" 
giskismet -x $NETXML giskismet -q "select * 
from wireless" -o ex1.kml
cp ex1.kml /mnt/sdcard1/Latest.kml
rm /root/ex1.kml
