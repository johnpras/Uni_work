import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
import java.io.*;


public interface HRClientInterface extends java.rmi.Remote { 

   public void notifyme() throws RemoteException;

}