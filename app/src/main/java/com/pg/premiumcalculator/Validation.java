package com.pg.premiumcalculator;

public class Validation {
    String validateEmail(String email)
    {
        if(email.isEmpty())
            return  "email is required";
        if(!email.contains("@") || !email.contains(".com"))
            return "invalid email";
        return  null;
    }
    String validatePhone(String phone)
    {
        if(phone.isEmpty())
            return "Phone no. is required";
        if(phone.contains("+"))
            return "country code is not required";
        if(phone.length()!=10)
            return "invalid phone no.";
        return  null;
    }
    String validatePassword(String password)
    {
        if(password.isEmpty())
            return "password is required";
        if(password.length()<6)
            return "password is too short";
        boolean ok1 = false,ok2 = false;
        for(int i=0;i<password.length();i++)
        {
            if((password.charAt(i)>='a' && password.charAt(i)<='z')||(password.charAt(i)>='A' && password.charAt(i)<='Z'))
                ok1 = true;
            if(password.charAt(i)>='0' && password.charAt(i)<='9')
                ok2 = true;
        }
        if(!ok1 || !ok2)
            return "password should be alpha-numeric";
        return null;
    }
    String validateName(String name)
    {
        if(name.isEmpty())
            return "name is required";
        return null;
    }
    String validateConfirmPassword(String password,String confirmPassword)
    {
        if(confirmPassword.isEmpty())
            return "Enter Password once again";
        if(!confirmPassword.equals(password))
            return "password and confirm password should be same";
        return null;
    }
}
