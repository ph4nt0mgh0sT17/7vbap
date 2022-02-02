package cz.osu.gamingblog.requests;

import cz.osu.gamingblog.models.PostReactionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostReactionRequest {
    private PostReactionItem postReactionItem;
}
