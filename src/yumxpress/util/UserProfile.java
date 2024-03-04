/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yumxpress.util;

/**
 *
 * @author HP
 */
public class UserProfile {
    private static String name;
    private static String id;
    private static  String email;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserProfile.name = name;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        UserProfile.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserProfile.email = email;
    }

    
    
}
