package controller;

import model.Model;
import view.View;

public class Main {
    /**
     * Start point of application
     *
     * @param args arguments from os
     */
    public static void main(String[] args) {
        new Controller(new View(), new Model("ArP"));
    }
}