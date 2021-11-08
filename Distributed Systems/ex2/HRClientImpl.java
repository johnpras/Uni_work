import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;


public class HRClientImpl extends java.rmi.server.UnicastRemoteObject implements HRClientInterface{

    public HRClientImpl() throws java.rmi.RemoteException{
        super();
    }
    

public void notifyme() throws RemoteException
{
        System.out.println("Anoixan dwmatia");

} 



}