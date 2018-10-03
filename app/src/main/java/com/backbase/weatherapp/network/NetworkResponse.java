package com.backbase.weatherapp.network;

public interface NetworkResponse
{
    void onSuccess(String response);
    void onError(String errorMessage);
}
