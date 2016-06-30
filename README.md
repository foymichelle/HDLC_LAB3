# HDLC_LAB3

# Test Case:

## Initiation of stations
Start PrimaryStation (station A)
Start SecondaryStation (station B)
Start SecondaryStation (station C)

## Communication procedure:
B sends Frame 0 to A
B sends Frame 0 to C

## Communication from B to A
####On station B console:

Is there any message to send? (y/n) \n
y \n
Please enter the destination address using 8-bits binary string (e.g. 00000001):
00000000
Please enter the message to send?
Frame 0 from B to A
recv msg -- control 10001000

####Should see on station A console:

Message received from 00000001: Frame 0 from B to A

## Comunication from B to C
