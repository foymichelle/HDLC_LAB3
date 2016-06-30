# HDLC_LAB3

# Test Case:

## Initiation of stations
Start PrimaryStation (station A)
Start SecondaryStation (station B)
Start SecondaryStation (station C)

## Communication procedure:
1. B sends Frame 0 to A
2. B sends Frame 0 to C
3. C sends Frame 0 to B
4. B sends Frame 1 to A
5. C sends Frame 0 to A
6. B sends Frame 1 to C
7. C sends Frame 1 to B

## Communication from B to A
####On station B console:

> Is there any message to send? (y/n)

> y 

> Please enter the destination address using 8-bits binary string (e.g. 00000001):

> 00000000

> Please enter the message to send?

> Frame 0 from B to A

> recv msg -- control 10001000

####Should see on station A console:

> Message received from 00000001: Frame 0 from B to A

## Comunication from B to C
####On station B console:

> recv msg -- control 10001000

> Is there any message to send? (y/n)

> y

> Please enter the destination address using 8-bits binary string (e.g. 00000001):

> 00000010

> Please enter the message to send?

> Frame 0 from B to C

NOTE: Will need to wait until C sends a frame to see message from B on C console.

## Communication from C to B
####On station C console
> Is there any message to send? (y/n)

> y

> Please enter the destination address using 8-bits binary string (e.g. 00000001):

> 00000001

> Please enter the message to send?

> Frame 0 from C to B

> recv msg -- control 00000000

> Received data: Frame 0 from B to C // Frame sent above from B

