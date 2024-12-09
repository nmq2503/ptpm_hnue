package com.nmq.foodninjaver2.utils;

import android.icu.text.SimpleDateFormat;

import androidx.core.net.ParseException;

import java.util.Date;
import java.util.Locale;

public class ValidateFunction {
    public static String cleanString(String input) {
        // Loại bỏ khoảng trắng đầu cuối và thay thế nhiều khoảng trắng liên tiếp bằng một khoảng trắng
        return input.trim().replaceAll("\\s+", " ");
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^0\\d{9,10}$";
        return phoneNumber.matches(phoneNumberRegex);
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password.matches(passwordRegex);
    }

    public static boolean validateTime(String time) {
        // Kiểm tra định dạng hh:mm a
        String timePattern = "^(0?[1-9]|1[0-2]):[0-5]\\d (AM|PM)$";
        return time.matches(timePattern);
    }

    public static boolean isTimeOrderValid(String openingHour, String closingHour) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            Date openingTime = sdf.parse(openingHour);
            Date closingTime = sdf.parse(closingHour);
            return openingTime != null && closingTime != null && openingTime.before(closingTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
