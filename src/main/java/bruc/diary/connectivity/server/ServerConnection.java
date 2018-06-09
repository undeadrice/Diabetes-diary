package bruc.diary.connectivity.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnection implements Runnable {

	private final String IP_ADRESS = "127.0.0.7";
	private final int PORT = 9999;

	private final ObjectInputStream in;
	private final ObjectOutputStream out;

	private final BlockingQueue<Object> queueIn = new ArrayBlockingQueue<>(10);
	private final BlockingQueue<Object> queueOut = new ArrayBlockingQueue<>(10);

	private ExecutorService threads = Executors.newFixedThreadPool(3);

	private final Socket s;

	public ServerConnection() throws UnknownHostException, IOException {
		s = new Socket(IP_ADRESS, PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
	}
	
	public void startConnection() {
		threads.execute(this);
	}

	@Override
	public void run() {
		send();
		read();
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

	}

	private void send() {
		Runnable r = () -> {
			try (s) {
				while (true) {
					Thread.sleep(50);
					out.writeObject(queueOut.take());
				}
			} catch (InterruptedException | IOException e) {
				threads.shutdownNow();
				e.printStackTrace();
			}
		};
		threads.execute(r);
	}

	private void read() {
		Runnable r = () -> {
			try (s) {
				while (true) {
					Thread.sleep(50);
					queueIn.put(in.readObject());
				}
			} catch (InterruptedException | ClassNotFoundException | IOException e) {
				threads.shutdownNow();
				e.printStackTrace();
			}
		};
		threads.execute(r);
	}

}
