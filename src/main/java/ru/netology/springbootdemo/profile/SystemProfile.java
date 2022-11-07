package ru.netology.springbootdemo.profile;

import org.springframework.stereotype.Component;

@Component
public interface SystemProfile {
    String getProfile();
}