package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;



public class App {

    public static void main(String[] args) throws IOException {
        GameTools game = new GameTools();

        ServerSocket server = new ServerSocket(13);
        System.out.println("Server ready ....");
        Socket socketPlayer1 = server.accept();
        PrintStream p1Print = new PrintStream(socketPlayer1.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketPlayer1.getInputStream())) ;
        String choix;
        do {
            p1Print.println("CHOIX");
            choix = bufferedReader.readLine();
        }while(!choix.equals("1") && !choix.equals("2"));

        if (choix.equals("1")){
            System.out.println("Player VS Player");
            p1Print.println("Waiting for the second player ....");
            Socket socketPlayer2 = server.accept();
            Thread player1 = new Thread(new Player(socketPlayer1, game,'X'));
            Thread player2 = new Thread(new Player(socketPlayer2, game,'O'));
            player1.start();
            player2.start();
        }else{
            System.out.println("Player VS CPUPlayer");
            Thread player = new Thread(new Player(socketPlayer1, game,'X'));
            Thread cpuPlayer = new Thread(new CPUPlayer(game,'O'));
            player.start();
            cpuPlayer.start();
        }
    }
}