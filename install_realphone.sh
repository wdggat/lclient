#!/bin/bash 

adb -s 0123456789ABCDEF push whoami.apk /sdcard/
adb -s 0123456789ABCDEF uninstall com.liu.activity
adb -s 0123456789ABCDEF install whoami.apk
