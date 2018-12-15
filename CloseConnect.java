/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.asm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class CloseConnect {
    
    static void close(Connection cnn, PreparedStatement preparedStatement) {
        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CloseConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if ( preparedStatement!= null) {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CloseConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
