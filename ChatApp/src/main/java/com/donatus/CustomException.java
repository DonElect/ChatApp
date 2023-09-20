package com.donatus;

import java.io.IOException;
import java.net.ConnectException;

public class CustomException extends Exception{

    public static IOException throwIOException(String message){
        return new IOException(message);
    }

    public static NullPointerException throwNullPointerException(String message){
        return new NullPointerException(message);
    }

    public static ConnectException throwConnectException(String message){
        return new ConnectException(message);
    }
}
