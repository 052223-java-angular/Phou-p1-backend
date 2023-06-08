package com.revature.yield.api.dtos.coin.history;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JHistoryCommunityDataDTO {

    public String facebook_likes;
    public String twitter_followers;
    public String reddit_average_posts_48h;
    public String reddit_average_comments_48h;
    public String reddit_subscribers;
    public String reddit_accounts_active_48h;


}
