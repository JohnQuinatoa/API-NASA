package ec.edu.uce.Api_Mars_rover.Main;

import ec.edu.uce.Api_Mars_rover.view.Window;

import javax.swing.*;

/**
 * @author: John Steven Quinatoa Guerrero
 * @date: 08/12/2021
 * Tema: Api Mars Rover
 */

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
        });

    }

}