package ru.alfa.app.dto.giphy.data;

import lombok.Data;

@Data
public class User {
    private String avatar_url;
    private String banner_image;
    private String banner_url;
    private String profile_url;
    private String username;
    private String display_name;
    private String description;
    private String instagram_url;
    private String website_url;
    private boolean is_verified;
}
