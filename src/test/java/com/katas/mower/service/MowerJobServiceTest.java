package com.katas.mower.service;

import com.katas.mower.data.FieldDimensions;
import com.katas.mower.data.MowerPosition;
import com.katas.mower.data.Orientation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.katas.mower.service.MowerJobService.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MowerJobServiceTest {

    @Test
    public void executeMowersJobs_whenMowerJobFileWithSingleMower_thenItSucceed() throws Exception {
        //Given
        var singleMowerJobFilePath = "src/test/resources/single-mower-job-file.txt";

        //When
        var actualMowerJobResult = MowerJobService.executeMowersJobs(singleMowerJobFilePath);
        var expectedFieldDimensions = new FieldDimensions(5, 5);
        var expectedMowersFinalPositions = Arrays.asList(new MowerPosition(1, 3, Orientation.N));

        //Then
        var actualFieldDimensions = actualMowerJobResult.getFieldDimensions();
        var actualMowersFinalPositions = actualMowerJobResult.getMowersFinalPositions();

        assertThat(actualFieldDimensions).usingRecursiveComparison().isEqualTo(expectedFieldDimensions);
        assertThat(actualMowersFinalPositions).usingRecursiveComparison().isEqualTo(expectedMowersFinalPositions);
    }

    @Test
    public void executeMowersJobs_whenMowerJobFileWithMultipleMowers_thenItSucceed() throws Exception {
        //Given
        var multipleMowersJobFilePath = "src/test/resources/multiple-mowers-job-file.txt";

        //When
        var actualMowerJobResult = MowerJobService.executeMowersJobs(multipleMowersJobFilePath);
        var expectedFieldDimensions = new FieldDimensions(5, 5);
        var expectedMowersFinalPositions = new ArrayList<>();
        expectedMowersFinalPositions.add(new MowerPosition(1, 3, Orientation.N));
        expectedMowersFinalPositions.add(new MowerPosition(5, 1, Orientation.E));

        //Then
        var actualFieldDimensions = actualMowerJobResult.getFieldDimensions();
        var actualMowersFinalPositions = actualMowerJobResult.getMowersFinalPositions();

        assertThat(actualFieldDimensions).usingRecursiveComparison().isEqualTo(expectedFieldDimensions);
        assertThat(actualMowersFinalPositions).usingRecursiveComparison().isEqualTo(expectedMowersFinalPositions);
    }

    @Test
    public void executeMowersJobs_whenMowerJobFileDontExists_thenItFails() {
        //Given
        var noMowerJobFilePath = "src/test/resources/no-mower-job-file.txt";

        //Then
        var actualException = assertThrows(Exception.class, () -> MowerJobService.executeMowersJobs(noMowerJobFilePath));
        var expectedErrorMessage = format(FILE_NOT_FOUND_MESSAGE, "no-mower-job-file.txt");
        assertThat(actualException.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    public void executeMowersJobs_whenFieldDimensionsInputIsNotValid_thenItFails() throws Exception {
        //Given
        var invalidFieldDimensionsMowerJobInputFilePath = "src/test/resources/invalid-field-dimensions-input-mower-job.txt";

        //When
        var actulaMowerJobResult = MowerJobService.executeMowersJobs(invalidFieldDimensionsMowerJobInputFilePath);
        var expectedInvalidFieldDimensionsInputMessage = format(INVALID_FIELD_DIMENSIONS, "field dimensions");

        //Then
        var actualFailedJobsMessages = actulaMowerJobResult.getFailedJobsMessages();
        int actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);
        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedInvalidFieldDimensionsInputMessage);
    }

    @Test
    public void executeMowersJobs_whenMowerInitialPositionInputIsNotValid_thenItFails() throws Exception {
        //Given
        var invalidMowerInitialPositionMowerJobInputFilePath = "src/test/resources/invalid-mower-initial-position-input-mower-job.txt";

        //When
        var actulaMowerJobResult = MowerJobService.executeMowersJobs(invalidMowerInitialPositionMowerJobInputFilePath);
        var expectedInvalidMowerInitialPositionInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, 1, "mower initial position");

        //Then
        var actualFailedJobsMessages = actulaMowerJobResult.getFailedJobsMessages();
        int actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);
        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedInvalidMowerInitialPositionInputMessage);
    }

    @Test
    public void executeMowersJobs_whenMowerInstructionsInputIsNotValid_thenItFails() throws Exception {
        //Given
        var invalidInstructionsMowerJobInputFilePath = "src/test/resources/invalid-instructions-input-mower-job.txt";

        //When
        var actualMowerJobResult = MowerJobService.executeMowersJobs(invalidInstructionsMowerJobInputFilePath);
        var expectedInvalidInstructionsInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, 1, "instructions");

        //Then
        var actualFailedJobsMessages = actualMowerJobResult.getFailedJobsMessages();
        int actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);
        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedInvalidInstructionsInputMessage);
    }

    @Test
    public void executeMowersJobs_whenMowerInitialPositionIsOutOfFieldDimensions_thenItFails() throws Exception {
        //Given
        var mowerInitialPositionIsOutOfFieldDimensionsFilePath = "src/test/resources/out-of-field-dimensions-mower-job-file.txt";

        //When
        var actualMowerJobResult = MowerJobService.executeMowersJobs(mowerInitialPositionIsOutOfFieldDimensionsFilePath);
        var expectedOutOfFieldDimensionsMessage = format(OUT_OF_FIELD_DIMENSIONS_MESSAGE, 1, 6, 5, 5);

        //Then
        var actualFailedJobsMessages = actualMowerJobResult.getFailedJobsMessages();
        int actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);
        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedOutOfFieldDimensionsMessage);
    }

    @Test
    public void executeMowersJobs_whenFieldDimensionsInputIsNotValidForMultipleMowerJob_thenItFails() throws Exception {
        //Given
        var invalidFieldDimensionsInputForMultipleMowerJobFilePath = "src/test/resources/invalid-field-dimensions-input-mower-job.txt";

        //When
        var actulaMowerJobResult = MowerJobService.executeMowersJobs(invalidFieldDimensionsInputForMultipleMowerJobFilePath);
        var expectedInvalidFieldDimensionsInputMessage = format(INVALID_FIELD_DIMENSIONS, "field dimensions");

        //Then
        var actualMowersFinalPositions = actulaMowerJobResult.getMowersFinalPositions();
        var actualFailedJobsMessages = actulaMowerJobResult.getFailedJobsMessages();
        var actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);
        assertThat(actualMowersFinalPositions).isNull();
        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedInvalidFieldDimensionsInputMessage);
    }

    @Test
    public void executeMowersJobs_whenCombinedValidAndInvalidInstructionInput_thenOneFailAndTheOtherSucceed() throws Exception {
        //Given
        var combinedValidAndInvalidInstructionsMowerJobInputFilePath = "src/test/resources/combined-valid-and-invalid-instructions-input-mower-job.txt";

        //When
        var actualMowerJobResult = MowerJobService.executeMowersJobs(combinedValidAndInvalidInstructionsMowerJobInputFilePath);
        var expectedInvalidInstructionsInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, 1, "instructions");
        var expectedFieldDimensions = new FieldDimensions(5, 5);
        var expectedMowersFinalPositions = Arrays.asList(new MowerPosition(1, 3, Orientation.N));

        //Then
        var actualFieldDimensions = actualMowerJobResult.getFieldDimensions();
        var actualMowersFinalPositions = actualMowerJobResult.getMowersFinalPositions();
        var actualFailedJobsMessages = actualMowerJobResult.getFailedJobsMessages();
        int actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);

        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedInvalidInstructionsInputMessage);
        assertThat(actualFieldDimensions).usingRecursiveComparison().isEqualTo(expectedFieldDimensions);
        assertThat(actualMowersFinalPositions).usingRecursiveComparison().isEqualTo(expectedMowersFinalPositions);
    }

    @Test
    public void executeMowersJobs_whenCombinedValidAndInvalidMowerInitialPositionInput_thenOneFailAndTheOtherSucceed() throws Exception {
        //Given
        var combinedValidAndInvalidMowerInitialPositionMowerJobInputFilePath = "src/test/resources/combined-valid-and-invalid-mower-initial-position-input-mower-job.txt";

        //When
        var actualMowerJobResult = MowerJobService.executeMowersJobs(combinedValidAndInvalidMowerInitialPositionMowerJobInputFilePath);
        var expectedInvalidMowerInitialPositionInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, 1, "mower initial position");
        var expectedFieldDimensions = new FieldDimensions(5, 5);
        var expectedMowersFinalPositions = Arrays.asList(new MowerPosition(1, 3, Orientation.N));

        //Then
        var actualFieldDimensions = actualMowerJobResult.getFieldDimensions();
        var actualMowersFinalPositions = actualMowerJobResult.getMowersFinalPositions();
        var actualFailedJobsMessages = actualMowerJobResult.getFailedJobsMessages();
        int actualFailedJobsMessagesSize = actualFailedJobsMessages.size();
        var failedJobMessage = actualFailedJobsMessages.get(0);

        assertEquals(actualFailedJobsMessagesSize, 1);
        assertThat(failedJobMessage).isEqualTo(expectedInvalidMowerInitialPositionInputMessage);
        assertThat(actualFieldDimensions).usingRecursiveComparison().isEqualTo(expectedFieldDimensions);
        assertThat(actualMowersFinalPositions).usingRecursiveComparison().isEqualTo(expectedMowersFinalPositions);
    }
}
