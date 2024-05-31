package com.katas.mower;

import com.katas.mower.data.MowerJobResult;
import com.katas.mower.service.MowerJobService;

public class MowerJobExecutions {
    public static void main(String[] args) throws Exception {
        MowerJobResult mowerJobResult = MowerJobService.executeMowersJobs("src/main/resources/mower-job.txt");
    }
}