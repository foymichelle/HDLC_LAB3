# HDLC_LAB3

# Test Case:

## Initiation of stations
Start PrimaryStation (station A)
Start SecondaryStation (station B)
Start SecondaryStation (station C)

## Communication
On station B console:

Is there any message to send? (y/n)
y
Please enter the destination address using 8-bits binary string (e.g. 00000001):
00000000
Please enter the message to send?
Frame 0 from B to A
recv msg -- control 10001000

Should see on station A console:

Message received from 00000001: Frame 0 from B to A