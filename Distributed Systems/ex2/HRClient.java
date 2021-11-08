import java.rmi.Naming; 
import java.rmi.RemoteException; 
import java.net.MalformedURLException; 
import java.rmi.NotBoundException; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class HRClient {
    public static void main(String[] args){
        try {

            HRInterface h = (HRInterface)Naming.lookup("rmi://" + args[1] + "/Hotel"); 
            
            if(args.length == 0){
                System.out.println("run again with:");
                System.out.println("java HRClient list <hostname> or");
                System.out.println("java HRClient list <hostname> <type> <number> <name> or");
                System.out.println("java book <hostname> <type> <number> <name> or");
                System.out.println("java HRClient guests <hostname> or");
                System.out.println("java HRClient cancel <hostname> <type> <number> <name>");
            }else{

                if(args[0].equals("list")){
                    if(args.length == 2){
                       
                        String newString = h.list();
                        newString = newString.substring(1, newString.length()-1);
                        newString = newString.replaceAll("\\s+","");
                        String[] splitted = newString.split(",");
                        System.out.println(splitted[0]+" dwmatia tipou A me 50E th vradia");
                        System.out.println(splitted[1]+" dwmatia tipou B me 70E th vradia");
                        System.out.println(splitted[2]+" dwmatia tipou C me 80E th vradia");
                        System.out.println(splitted[3]+" dwmatia tipou D me 120E th vradia");
                        System.out.println(splitted[4]+" dwmatia tipou E me 150E th vradia");
                       
                         
                    }else{
                        System.out.println("wrong input...\nClosing program");
                        System.exit(0);
                    }
                }
                if(args[0].equals("book")){
                    if(args.length == 5){
                    

                            String returnvalue = h.book(args[2],args[3],args[4]);
                            String type = args[2];
                            String name = args[4];

                            if(returnvalue.equals("Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?")){
                                System.out.println("Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?(yes/no)");
                               
                                String choice = " ";  
                                Scanner scanIn = new Scanner(System.in);
                                choice = scanIn.nextLine();
                                //scanIn.close(); 

                                if(choice.equals("yes")){
                                    
                                    if(type.equals("A")){
                                        String newString = h.list();
                                        newString = newString.substring(1, newString.length()-1);
                                        String[] splitted = newString.split(",");
    
                                        System.out.println(h.book(type,splitted[0],name)) ;
                                    }
                                    if(type.equals("B")){
                                        String newString = h.list();
                                        newString = newString.substring(1, newString.length()-1);
                                        newString = newString.replaceAll("\\s+","");
                                        String[] splitted = newString.split(",");
                                        System.out.println(h.book(type,splitted[1],name)) ;
                                    }
                                    if(type.equals("C")){
                                        String newString = h.list();
                                        newString = newString.substring(1, newString.length()-1);
                                        newString = newString.replaceAll("\\s+","");
                                        String[] splitted = newString.split(",");
                                        System.out.println(h.book(type,splitted[2],name)) ;
                                    }
                                    if(type.equals("D")){
                                        String newString = h.list();
                                        newString = newString.substring(1, newString.length()-1);
                                        newString = newString.replaceAll("\\s+","");
                                        String[] splitted = newString.split(",");
                                        System.out.println(h.book(type,splitted[3],name)) ;
                                    }
                                    if(type.equals("E")){
                                        String newString = h.list();
                                        newString = newString.substring(1, newString.length()-1);
                                        newString = newString.replaceAll("\\s+","");
                                        String[] splitted = newString.split(",");
                                        System.out.println(h.book(type,splitted[4],name)) ;
                                    }
                                    
                                }else{

                                    System.out.println("Thes na grafteis stin lista?");
                                    String answer = " ";  
                                    answer = scanIn.nextLine();
                                    //scanIn.close(); 
            
                                    if(answer.equals("yes")){
                                        HRClientInterface HRClientObj = new HRClientImpl();
                                        h.addtolist(HRClientObj);
                                    }

                                }

                            }else if(returnvalue.equals("Apotixia kratisis")){
                                System.out.println("Apotixia kratisis");
                            }else{
                                System.out.println(returnvalue);
                            }
                         
                    }else{
                        System.out.println("wrong input...\nClosing program");
                        System.exit(0);
                    }
                }

                if(args[0].equals("guests")){
                    if(args.length == 2){
                        
                            System.out.println(h.guests());
                        
                    }
                }

                if(args[0].equals("cancel")){
                    if(args.length == 5){
                       
                            System.out.println(h.cancel(args[2],args[3],args[4]));
                        
                    }
                }


            }

		} catch (RemoteException e) {
			e.printStackTrace();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
        }
        
     
    }
}