import java.io.*;
import java.net.Socket;

public class TestClient {

    public static void main(String[] args) {

        try {

            Socket socket =
                    new Socket("localhost", 3333);

            DataInputStream dis =
                    new DataInputStream(socket.getInputStream());

            DataOutputStream dout =
                    new DataOutputStream(socket.getOutputStream());

            BufferedReader console =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            // ===== RECEIVER THREAD =====
            Thread receiver = new Thread(() -> {
                try {
                    while (true) {
                        String serverMsg = dis.readUTF();
                        System.out.println(serverMsg);
                    }
                } catch (Exception e) {
                    System.out.println("Disconnected from server");
                }
            });

            receiver.setDaemon(true);
            receiver.start();

            // ===== SENDER =====
            while (true) {
                String msg = console.readLine();
                dout.writeUTF(msg);
                dout.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}