package com.example.domain;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public class ApiUtilsQ {

    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );
}
