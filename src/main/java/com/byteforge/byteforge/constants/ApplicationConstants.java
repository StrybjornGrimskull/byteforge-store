package com.byteforge.byteforge.constants;

public final class ApplicationConstants {

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_BEARER_PREFIX = "Bearer ";
    public static final String FROM_EMAIL = "byteforge@mail.com";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    
    // Роли пользователей
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_MODERATOR = "MODERATOR";
    public static final String ROLE_PRODUCT_MANAGER = "PRODUCT_MANAGER";
    
    // Атрибуты модели
    public static final String LOGIN_ERROR_ATTRIBUTE = "loginError";
    public static final String ERROR_TYPE_ATTRIBUTE = "errorType";
    public static final String MESSAGE_ATTRIBUTE = "message";
    public static final String ERROR_ATTRIBUTE = "error";
    public static final String EMAIL_ATTRIBUTE = "email";
}
