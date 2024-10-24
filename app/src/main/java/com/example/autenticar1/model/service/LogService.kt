package com.example.autenticar1.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}