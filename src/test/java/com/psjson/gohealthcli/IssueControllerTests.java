package com.psjson.gohealthcli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.psjson.gohealthcli.controller.IssueController;
import com.psjson.gohealthcli.controller.IssueControllerImpl;
import com.psjson.gohealthcli.model.Issue;
import com.psjson.gohealthcli.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IssueControllerTests {
    
    @Mock
    private IssueRepository mockRepository;

    @InjectMocks
    private IssueControllerImpl controller;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIssueCreateSuccess() throws Exception {
        Issue testIssue = new Issue("I-4", "This is a test description", "http://www.google.com");
        String expectedId = "I-100";

        when(mockRepository.createIssue(testIssue)).thenReturn(expectedId);

        String newId = controller.createIssue(testIssue);

        assertEquals(expectedId, newId);
    }

    @Test
    void testIssueCreateFailure_badParentId() throws Exception {
        Issue testIssue = new Issue("NotAGoodParentId", "This is a good test description", "http://www.gohealth.com");

        when(mockRepository.createIssue(testIssue)).thenThrow(new Exception("Bad Parent ID"));

        Exception ex = assertThrows(Exception.class, () -> {
            controller.createIssue(testIssue);
        });

        assertEquals("Bad Parent ID", ex.getMessage());
    }

    @Test
    void testIssueCreateFailure_existingId() throws Exception {
        Issue testIssue = new Issue("This is a moderate test description", "http://localhost");

        testIssue.setId("I-100");

        Exception ex = assertThrows(Exception.class, () -> {
            controller.createIssue(testIssue);
        });

        assertEquals("Tried to create an issue that already had an ID.", ex.getMessage());
    }
}
