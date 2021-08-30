package seria;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        Seria();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AntiSeria();
    }

    public static void Seria(){
        User yubajin = new User("yubajin", 18);
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(new File("src/main/resources/User")))) {
            objectOutputStream.writeObject(yubajin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void AntiSeria(){
        try(ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(new File("src/main/resources/User")))) {
            User user = (User) objectInputStream.readObject();
            System.out.println("反序列化: " + user);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
