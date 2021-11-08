import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HRServer {
    

public HRServer(String hostname){

    HRImpl hotel;
    try { 
        LocateRegistry.createRegistry(1099); 
        //System.out.println("java RMI registry created.");

    } catch (RemoteException e) {
  //System.out.println("java RMI registry already exists.");
    }   
    try {
        hotel = new HRImpl();//Instantiate HRmpl 
        Naming.rebind("rmi://"+hostname+"/Hotel", hotel);
    } catch (RemoteException e) {
        e.printStackTrace();
    } 
    catch (MalformedURLException e) {
        e.printStackTrace();
    }


}  

public static void main (String[] args)
{

new HRServer(args[0]);

}

}