package com.nmq.foodninjaver2.utils;

public class ValidateFunction {
    public static String cleanString(String input) {
        // Loại bỏ khoảng trắng đầu cuối và thay thế nhiều khoảng trắng liên tiếp bằng một khoảng trắng
        return input.trim().replaceAll("\\s+", " ");
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false; // Mật khẩu phải có ít nhất 8 ký tự
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password.matches(passwordRegex);
    }
}
