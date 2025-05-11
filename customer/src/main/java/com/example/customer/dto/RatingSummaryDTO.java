package com.example.customer.dto;

public class RatingSummaryDTO {
    private double averageScore;
    private long totalRatings;

    public RatingSummaryDTO() {}

    public RatingSummaryDTO(double averageScore, long totalRatings) {
        this.averageScore = averageScore;
        this.totalRatings = totalRatings;
    }

    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }

    public long getTotalRatings() { return totalRatings; }
    public void setTotalRatings(long totalRatings) { this.totalRatings = totalRatings; }
}
