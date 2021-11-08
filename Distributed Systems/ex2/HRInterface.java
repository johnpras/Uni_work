import java.rmi.*;
import java.io.*;

public interface HRInterface extends java.rmi.Remote { 


    
public String list() throws java.rmi.RemoteException;
public String book(String a, String b, String c) throws java.rmi.RemoteException;
public String guests() throws java.rmi.RemoteException;
public String cancel(String a, String b, String c) throws java.rmi.RemoteException;

public void addtolist(HRClientInterface HRClientObject) throws java.rmi.RemoteException;
//public void doCallbacks() throws java.rmi.RemoteException;


} 