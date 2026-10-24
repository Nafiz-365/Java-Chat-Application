/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nafiz.event;

/**
 *
 * @author Nafiz_365
 */
public class PublicEvent {

    private static final PublicEvent INSTANCE = new PublicEvent();
    private EventMain eventMain;
    private EventImageView eventImageView;
    private EventChat eventChat;
    private EventLogin eventLogin;

    public static PublicEvent getInstance() {
        return INSTANCE;
    }

    private PublicEvent() {

    }

    public synchronized void addEventMain(EventMain event){
        this.eventMain = event;
    }
    
    public synchronized void addEventImageView(EventImageView event) {
        this.eventImageView = event;
    }

    public synchronized void addEventChat(EventChat event) {
        this.eventChat = event;
    }

    public synchronized void addEventLogin(EventLogin event) {
        this.eventLogin = event;
    }
    
    public synchronized EventMain getEventMain(){
        return eventMain;
    }

    public synchronized EventImageView getEventImageView() {
        return eventImageView;
    }

    public synchronized EventChat getEventChat() {
        return eventChat;
    }

    public synchronized EventLogin getEventLogin() {
        return eventLogin;
    }

}
