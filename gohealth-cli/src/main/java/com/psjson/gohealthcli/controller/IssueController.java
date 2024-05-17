package com.psjson.gohealthcli.controller;

import com.psjson.gohealthcli.model.Issue;
import com.psjson.gohealthcli.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

public interface IssueController {

    String createIssue(Issue issue) throws Exception;

    void closeIssue(String id) throws Exception;
}
