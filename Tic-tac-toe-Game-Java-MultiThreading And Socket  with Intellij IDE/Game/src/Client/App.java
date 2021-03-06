package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) {
        try {
            Scanner scanner= new Scanner(System.in);
            Socket socket = new Socket( "localhost" , 13) ;

            BufferedReader flux = new BufferedReader ( new InputStreamReader( socket.getInputStream( ) ) ) ;
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            System.out.print("Welcome to X/O Game\n");
            String choix = null;
            String data = flux.readLine();
            while(data.equals("CHOIX")){
                System.out.print("\t1- Player VS Player\n\t2- Player VS CPU\n\t====> : ");

                do {
                    choix = scanner.nextLine();
                }while(!choix.equals("1") && !choix.equals("2"));
                printStream.println(choix);
                data = flux.readLine();
            }
            System.out.println(data);
            System.out.println("\n 1 | 2 | 3 \n---+---+---\n 4 | 5 | 6 \n---+---+---\n 7 | 8 | 9 ");
            while(true){
                try{
                    data = flux.readLine();
                    if (data.equals("TAKE_MOVE")){
                        System.out.print(" : ");
                        try{
                            printStream.println(scanner.nextLine());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println(data);
                        if (data.equals("GAME_OVER")) exit(1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
}
