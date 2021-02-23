package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GitHubJobsClient {

    private WebClient webClient;

    public GitHubJobsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGithubJobsAPI_withPageNumber(int pageNum, String description) {
        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNum)
                .buildAndExpand()
                .toUriString();

        LoggerUtil.log("uri: " + uri);

        List<GitHubPosition> gitHubPositions = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();
        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGithubJobsAPI_usingMultiplePageNumbers(List<Integer> pageNumbers, String description) {
        CommonUtil.startTimer();
        List<GitHubPosition> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> invokeGithubJobsAPI_withPageNumber(pageNumber, description))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();
        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGithubJobsAPI_usingMultiplePageNumbers_cf(List<Integer> pageNumbers, String description) {
        CommonUtil.startTimer();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> CompletableFuture.supplyAsync(
                        () -> invokeGithubJobsAPI_withPageNumber(pageNumber, description)))
                .collect(Collectors.toList());

        List<GitHubPosition> gitHubPositionList = gitHubPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        CommonUtil.timeTaken();
        return gitHubPositionList;
    }

    public List<GitHubPosition> invokeGithubJobsAPI_usingMultiplePageNumbers_cf_approach2(List<Integer> pageNumbers, String description) {
        CommonUtil.startTimer();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> CompletableFuture.supplyAsync(
                        () -> invokeGithubJobsAPI_withPageNumber(pageNumber, description)))
                .collect(Collectors.toList());

        CompletableFuture<Void> cfAallOf = CompletableFuture.allOf(gitHubPositions.toArray(new CompletableFuture[gitHubPositions.size()]));

        List<GitHubPosition> gitHubPositionList = cfAallOf.thenApply(v -> gitHubPositions.stream()
                .map(CompletableFuture::join) //Join is complete
                .flatMap(Collection::stream)
                .collect(Collectors.toList()))
                .join();

        CommonUtil.timeTaken();
        return gitHubPositionList;
    }


}
