package com.psjson.gohealthcli.controller;

import com.psjson.gohealthcli.model.Issue;
import com.psjson.gohealthcli.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * There's nothing too special in this file, I created it as a place to add business logic... I don't really have much
 * business logic at the moment. I like having this middle layer because the API layer (or CLI in this case) handles serializing
 * and deserializing of data. The repository layer handles the peristance of data and so this gives us a happy little place to
 * handle other logic!
 *
 * It also makes a nice place for me to write tests against
 */
@Controller
public class IssueControllerImpl implements IssueController {
    private static final Logger LOG = LoggerFactory.getLogger(IssueController.class);

    private final IssueRepository repository;

    IssueControllerImpl(IssueRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createIssue(Issue issue) throws Exception {
        if(issue.getId() != null) {
            throw new Exception("Tried to create an issue that already had an ID.");
        }
        Pattern pattern = Pattern.compile("I-\\d+");
        if(!pattern.matcher(issue.getParentId()).matches()) {
            throw new Exception("Bad Parent ID");
        }
        return repository.createIssue(issue);
    }

    @Override
    public void closeIssue(String id) throws Exception {
        repository.closeIssue(id);
    }
}
