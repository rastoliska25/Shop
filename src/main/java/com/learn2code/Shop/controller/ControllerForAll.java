package com.learn2code.Shop.controller;

import org.springframework.http.ResponseEntity;

public interface ControllerForAll<T> {
    public ResponseEntity add(T object);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();
}
