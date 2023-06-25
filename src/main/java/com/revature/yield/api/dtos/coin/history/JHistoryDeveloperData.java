package com.revature.yield.api.dtos.coin.history;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class JHistoryDeveloperData {

    public String forks;
    public String stars;
    public String subscribers;
    public String total_issues;
    public String closed_issues;
    public String pull_requests_merged;
    public String pull_request_contributors;
    public Map<String, String> code_additions_deletions_4_weeks;
    public String commit_count_4_weeks;

}
