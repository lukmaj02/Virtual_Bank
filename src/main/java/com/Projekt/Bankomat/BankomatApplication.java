package com.Projekt.Bankomat;


import com.Projekt.Bankomat.Controller.CommandHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


@SpringBootApplication
public class BankomatApplication {
	private final static int SERVER_PORT = 6000;
	private static ServerSocket serverSocket;

	public static void main(String[] args) throws IOException {
		try{
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server started on port " + SERVER_PORT);
		}
		catch(IOException e){
			System.out.println("Server failed to start on port " + SERVER_PORT);
			System.exit(-1);
		}

		ConfigurableApplicationContext appContext = SpringApplication.run(BankomatApplication.class, args);
		ExecutorService executorService = appContext.getBean(ExecutorService.class);
		CommandHandler commandHandler = appContext.getBean(CommandHandler.class);

		while(true){
			Socket clientSocket = serverSocket.accept();
			commandHandler.initSocket(clientSocket);
			System.out.println("Client connected " + clientSocket.getInetAddress().getHostAddress());
			FutureTask<String> task = new FutureTask<>(commandHandler);
			executorService.submit(task);
		}
	}
}
