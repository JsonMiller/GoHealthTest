package com.psjson.gohealthcli.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.psjson.gohealthcli.model.Issue;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVWriter;

@Repository
public class CsvIssueRepository implements IssueRepository {

    private static final String filePath = "storage.csv";

    private static int nextId = 1;

    /**
     * Constructor will delete the output file if it exists from a previous run.
     */
    CsvIssueRepository() {
        File file = new File(filePath);
        if(file.delete()) {
            System.out.println("Deleted file, hooray!");
        } else {
            System.out.println("File wasn't deleted, results might be sketchy!");
        }
    }

    /**
     * Create a new issue by appending it to the end of the output file.
     * @param issue - issue to save to the file
     * @return - The new id
     * @throws Exception - probably an error writing to the file.
     */
    @Override
    public String createIssue(Issue issue) throws Exception {
        if(issue.getId() != null) {
            throw new Exception("Tried to create issue that was already issued an ID");
        }
        issue.setId(String.format("I-%d", nextId++));
        try (Writer writer = new FileWriter(filePath, true)) {
            StatefulBeanToCsv<Issue> beanWriter = new StatefulBeanToCsvBuilder<Issue>(writer).build();
            beanWriter.write(issue);
            return issue.getId();
        }
    }

    /**
     * So... this would be so much easier with a database. I don't like the performance implications
     * of reading the whole file looping over the results and then writing it back to the file.
     * But honestly it's probably going to be much cleaner code for me to do it this way.
     *
     * Also look at that beautiful functional programming!
     * @param id - Id of the issue to close
     * @throws Exception - Probably an exception with reading or writing to the file.
     */
    @Override
    public void closeIssue(String id) throws Exception {
        List<Issue> issues = new CsvToBeanBuilder<Issue>(new FileReader(filePath))
                .withType(Issue.class).build().parse();

        issues.parallelStream().filter(issue -> id.equals(issue.getId())).forEach(Issue::close);

        try (Writer writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<Issue> beanWriter = new StatefulBeanToCsvBuilder<Issue>(writer).build();
            beanWriter.write(issues);
        }
    }
    
}
