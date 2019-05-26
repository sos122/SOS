/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nader
 */
public class ServerThread extends Thread {
    Socket socket;
    DataOutputStream os;
    DataInputStream is;
    
    public ServerThread(Socket s){
        this.socket=s;
    }
    @Override
    public void run() {  
        try {
            Connection conn=DB.getconnection();
            PreparedStatement stmt;
            ResultSet rs;
            
            while(true){
                is=new DataInputStream(socket.getInputStream());
                os=new DataOutputStream(socket.getOutputStream());
                String sql="INSERT INTO `sos`(`sender`,`location`,`details`,`status`) values(?,?,?,0);";    
                try {
                    stmt=conn.prepareCall(sql);
                    stmt.setString(1,is.readUTF());
                    stmt.setString(2,is.readUTF());
                    stmt.setString(3,is.readUTF());
                    stmt.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
}
