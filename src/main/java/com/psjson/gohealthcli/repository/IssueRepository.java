package com.psjson.gohealthcli.repository;

import com.psjson.gohealthcli.model.Issue;

public interface IssueRepository {
    
    String createIssue(Issue issue) throws Exception;

    void closeIssue(String id) throws Exception;
    
}
