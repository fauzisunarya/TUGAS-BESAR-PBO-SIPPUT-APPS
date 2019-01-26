
package gov.siput.library.main;

//import gov.siput.library.database.siputDatabase;

import gov.siput.library.view.RegisterView;
import gov.siput.library.view.SplashScreen;

public class Perpustakaan {


    public static void main(String[] args) {
        
        SplashScreen screen = new SplashScreen();
        RegisterView sign = new RegisterView();
        screen.setVisible(true);
        try {
            for (int row = 0; row <=100; row++) {
                Thread.sleep(100);
                screen.loading.setText(Integer.toString(row)+" %");
                if (row == 100) {
                    screen.setVisible(false);
                    sign.setVisible(true);
                }
            }
        } catch (Exception e) {
                
        }
    }
}
