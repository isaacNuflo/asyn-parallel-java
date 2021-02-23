package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import com.learnjava.util.LoggerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubJobsClientTest {

    WebClient webClient = WebClient.create("https://jobs.github.com/");
    GitHubJobsClient gitHubJobsClient = new GitHubJobsClient(webClient);

    @Test
    void invokeGithubJobsAPI_withPageNumber() {
        int pageNum = 1;
        String description = "ruby";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_withPageNumber(pageNum, description);
        LoggerUtil.log("gitHubPositions: " + gitHubPositions);
        assertNotNull(gitHubPositions);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGithubJobsAPI_usingMultiplePageNumbers() {
        List<Integer> pageNumList = Arrays.asList(1, 2, 3);
        String description = "Java";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_usingMultiplePageNumbers(pageNumList, description);
        assertNotNull(gitHubPositions);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGithubJobsAPI_usingMultiplePageNumbers_cf() {
        List<Integer> pageNumList = Arrays.asList(1, 2, 3);
        String description = "Java";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_usingMultiplePageNumbers_cf(pageNumList, description);
        assertNotNull(gitHubPositions);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGithubJobsAPI_usingMultiplePageNumbers_cf_approach2() {
        List<Integer> pageNumList = Arrays.asList(1, 2, 3);
        String description = "Java";
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGithubJobsAPI_usingMultiplePageNumbers_cf_approach2(pageNumList, description);
        assertNotNull(gitHubPositions);
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions
                .forEach(Assertions::assertNotNull);
    }
}
