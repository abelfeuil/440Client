package csc440client;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientCommunicationProtocol 
{
	private Socket theServer;
	
	public ClientCommunicationProtocol(Socket theServer)
	{
		this.theServer = theServer;
	}
	
	public void startCommunication() throws Exception
	{
		//ability to read from server
		Scanner serverInput = new Scanner(this.theServer.getInputStream());
				
		//ability to read from local client
		Scanner terminalInput = new Scanner(System.in);
		
		//ability to write to the server
		PrintWriter serverOutput = new PrintWriter(this.theServer.getOutputStream(),true);
		
		File myFilesDir = new File("./myFiles");
		String[] theFiles = myFilesDir.list();
		int pos = 0;
		for(String fn : theFiles)
		{
			System.out.println(pos + ": " + fn);
			pos++;
		}
		System.out.print("Which file would you like to share?");
		String theAnswer = terminalInput.nextLine();
		System.out.println("You chose to share: " + theFiles[Integer.parseInt(theAnswer)]);
		
		//Read the file from the client
		File theFile = new File("./myFiles/" + theFiles[Integer.parseInt(theAnswer)]);
		FileInputStream fis = new FileInputStream(theFile);
		
		//let the server know about the file we are about to send
		serverOutput.println(theFiles[Integer.parseInt(theAnswer)]);
		serverOutput.println(fis.available());
		while(fis.available() > 0)
		{
			this.theServer.getOutputStream().write(fis.read());
		}
		System.out.println("DONE");
		
			/*
		File theClone = new File("./myFiles/clone" + theFiles[Integer.parseInt(theAnswer)]);
		FileOutputStream fos = new FileOutputStream(theClone);
		System.out.println("num bytes: " + fis.available());

		//we want to read in all of the bytes and display them to the screen
		while(fis.available() > 0)
		{
			fos.write(fis.read());
		}
		System.out.println("DONE");
		fos.close();
		*/
		}
	}
}
