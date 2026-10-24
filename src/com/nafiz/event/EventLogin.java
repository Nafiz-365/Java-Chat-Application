/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nafiz.event;

/**
 *
 * @author Nafiz_365
 */
public interface EventLogin {

    public void login(String username, char[] password);

    public void register(String username, char[] password);

    public void goRegister();

    public void goLogin();

}
