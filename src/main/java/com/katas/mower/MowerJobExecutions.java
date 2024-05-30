package com.katas.mower;

public class MowerJobExecutions {
    public static void main(String[] args) throws Exception {
        MowerJobResult mowerJobResult = MowerJobService.executeMowersJobs("src/main/resources/mower-job.txt");
    }
}