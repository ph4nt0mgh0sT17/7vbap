package cz.osu.gamingblog.requests;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePostRequest {
    private String title;
    private String description;
    private String htmlContent;
    private String imageName;
    private List<String> categories;
    private String authorUsername;
}
