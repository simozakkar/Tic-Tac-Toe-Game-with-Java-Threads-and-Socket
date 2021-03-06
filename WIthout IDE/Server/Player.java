import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static java.lang.System.exit;

public class Player extends PlayerTools implements Runnable{

    public final Socket socketPlayer;
    public Player(Socket socketPlayer,GameTools game, char token){
        super(game, token);
        this.socketPlayer = socketPlayer;
    }

    @Override
    public void run() {
        PrintStream playerPrint = null;
        BufferedReader bufferedReader = null;
        try {
            playerPrint = new PrintStream(socketPlayer.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socketPlayer.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            exit(-1);
        }
        playerPrint.println("The Game will be start right now be ready ...");
        playerPrint.println("Your Token is "+this.token+" Wait ... ");

        int index;
        synchronized (game){
            while(game.statu.equals("GAME")){
                System.out.print("\nPlayer "+this.token);
                playerPrint.println(game.showBoard());
                do {
                    playerPrint.println("TAKE_MOVE");
                    try {
                        index = Integer.parseInt(bufferedReader.readLine());
                    } catch (Exception e) {
                        e.printStackTrace();
                        index = 10;
                    }
                }while(!game.makeMove(this.token,index));
                System.out.println(" ===> "+index);
                game.notify();
                try {
                    playerPrint.println(game.showBoard());
                    playerPrint.println("Waiting ... for the second player to take move");
                    game.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            playerPrint.println(game.showBoard());
            playerPrint.println(game.statu);
            playerPrint.println("GAME_OVER");
            game.notifyAll();
        }
    }
}
