package cz.osu.gamingblog.requests;

import cz.osu.gamingblog.responses.AuthorResponse;
import cz.osu.gamingblog.responses.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequest {
    private String text;
}
