package hdlc;

import java.net.*;
import java.io.*;

public class SecondaryStation {
	
	public static void main(String[] args) {
//		 declaration section:
//		 os: output stream
//		 is: input stream
		Socket clientSocket = null;
		PrintStream os = null;
		DataInputStream is = null;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String id = null;
		String information = null;
		String control = null;
		String flag = "01111110";
		String address = null;  
		String response = null; // control field of the socket input
		int ns = -1; // send sequence number
		int nr = 0; //receive sequence number
		
		//
		String answer = null; // input using keyboard
		//
		
       
        //		
//		 Initialization section:
//		 Try to open a socket on port 4444
//		 Try to open input and output streams
		try {
			clientSocket = new Socket("127.0.0.1", 4444);
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} 
		catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} 
		catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: hostname");
		}
				
		if (clientSocket != null && os != null && is != null) {			
			
			try {				
					
				String responseLine;				
				responseLine = is.readLine();
				
				//receive client address from the primary station
				//
				id = responseLine;
				System.out.println("client address: " + id);
				
				responseLine = is.readLine();
				response = responseLine.substring(16, 24);

				// recv SNRM msg
				if(response.equals("11000001") || response.equals("11001001")) {
					//===========================================================
					// insert codes here to send the UA msg					
					//===========================================================
					String UAToSend = flag + "00000000" + "11000110";
					os.println(UAToSend);

					System.out.println("sent UA msg");
				}
				
				// main loop; recv and send data msgs
				while (true) {
					responseLine = is.readLine();
					response = responseLine.substring(16, 24);
					
					System.out.println("recv msg -- control " + response);				
					
					// recv ??RR,*,P?? msg
					if(response.substring(0,5).equals("10001")) {
						
						// enter data msg using keyboard 
						System.out.println("Is there any message to send? (y/n)");
						answer = in.readLine();						
						
						if(answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes")) {
							boolean validAddr = false;
							while (!validAddr) {
								System.out.println("Please enter the destination address using 8-bits binary string (e.g. 00000001):");
								address = in.readLine();
								validAddr = validateAddress(address);
							}
							
							System.out.println("Please enter the message to send?");
							answer = in.readLine();
							
							// convert to binary
							answer = toBytes(answer);
							
							//===========================================================
							// insert codes here to send an I msg;
							String IFrameToSend = flag + address + "01110000" + answer;
							os.println(IFrameToSend);
							//===========================================================
						
						}				
						else {
							//===========================================================
							// insert codes here to send ??RR,*,F??
							String SFrameToSend = flag + "00000000" + "10000000";
							os.println(SFrameToSend);
							//===========================================================
						}
					}
					
					// recv an I frame
					if(response.substring(0,1).equals("0")) {
						String data = responseLine.substring(24, responseLine.length());
						data = decodeBinary(data);
						System.out.println("");
						System.out.println("Received data: " + data);						
						
						nr = Integer.parseInt(response.substring(1,4), 2) + 1;
//						System.out.println("nr: " + nr);
					}
				}
			} 
			catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
			} 
			catch (IOException e) {
				System.err.println("IOException: " + e);
			}	
		}
	}
	
	/*
	 * Method to convert string of characters into bytes (cannot exceed 64 bytes)
	 */
	
	public static String toBytes(String message) {
		byte[] msgBytes = message.getBytes();
		
		if (msgBytes.length > 64) {
			System.out.println("Message exceeds 64 bytes! Try again.");
			return null;
		}
		
		StringBuilder msgBinary = new StringBuilder();
		
		for (byte b : msgBytes) {
			int value = b;
			
			for (int i=0; i<8; i++) {
				msgBinary.append((value & 128) == 0 ? 0 : 1);
		        value <<= 1;
			}
		}
		
		return msgBinary.toString();
	}
	
	/*
	 * Method to convert binary message to readable String
	 */
	
	public static String decodeBinary(String bin) {

		char[] result = bin.toCharArray();
		
		String conversionString = "";
		String resultString = "";
		int binChar = 0;
		char asciiChar = '0';
		
		for(int i = 0; i<result.length; i++)
		{
			conversionString = conversionString+result[i];
			if(i!= 0 && (i+1)%8 == 0)
			{
				binChar = Integer.parseInt(conversionString,2);
				asciiChar = (char)binChar;
				resultString = resultString+asciiChar;
				conversionString = "";
			}
		}
		
		return resultString;
	}
	
	/*
	 * Method to validate correct binary address
	 */
	
	public static boolean validateAddress(String address) {
		if (address.length() != 8) {
			System.out.println("Must enter address of 8-bit length.");
			return false;
		}
		
		for (int i=0; i<address.length(); i++) {
			if (address.charAt(i) != '0' && address.charAt(i) != '1') {
				System.out.println("Found invalid character in bit string.");
			}
		}
		
		return true;
	}

}// end of class SecondaryStation
