package com.katas.mower.service;

import com.katas.mower.data.FieldDimensions;
import com.katas.mower.data.MowerJobResult;
import com.katas.mower.data.MowerPosition;
import com.katas.mower.data.Orientation;
import com.katas.mower.exceptions.InvalidMowerJobFileInputException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.katas.mower.service.MowerJobService.FILE_NOT_FOUND_MESSAGE;
import static com.katas.mower.service.MowerJobService.INVALID_MOWER_JOB_FILE_INPUT;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MowerJobServiceTest {

    @Test
    public void executeMowersJobs_whenMowerJobFileWithSingleMower_thenItSucceed() throws Exception {
        //Given
        String singleMowerJobFilePath = "src/test/resources/single-mower-job-file.txt";

        //When
        MowerJobResult actualMowerJobResult = MowerJobService.executeMowersJobs(singleMowerJobFilePath);
        FieldDimensions expectedFieldDimensions = new FieldDimensions(5, 5);
        List<MowerPosition> expectedMowersFinalPositions = Arrays.asList(new MowerPosition(1, 3, Orientation.N));

        //Then
        FieldDimensions actualFieldDimensions = actualMowerJobResult.getFieldDimensions();
        List<MowerPosition> actualMowersFinalPositions = actualMowerJobResult.getMowersFinalPositions();

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
        assertThat(actualException.getMessage()).isEqualTo(FILE_NOT_FOUND_MESSAGE);
    }

    @Test
    public void executeMowersJobs_whenFieldDimensionsInputIsNotValid_thenItFails() {
        //Given
        var invalidFieldDimensionsMowerJobInputFilePath = "src/test/resources/invalid-field-dimensions-input-mower-job.txt";

        //Then
        var actualException = assertThrows(InvalidMowerJobFileInputException.class, () -> MowerJobService.executeMowersJobs(invalidFieldDimensionsMowerJobInputFilePath));
        var expectedInvalidFieldDimensionsInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, "field dimensions");
        assertThat(actualException.getMessage()).isEqualTo(expectedInvalidFieldDimensionsInputMessage);
    }

    @Test
    public void executeMowersJobs_whenMowerInitialPositionInputIsNotValid_thenItFails() {
        //Given
        var invalidMowerInitialPositionMowerJobInputFilePath = "src/test/resources/invalid-mower-initial-position-input-mower-job.txt";

        //Then
        var actualException = assertThrows(InvalidMowerJobFileInputException.class, () -> MowerJobService.executeMowersJobs(invalidMowerInitialPositionMowerJobInputFilePath));
        var expectedInvalidMowerInitialPositionInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, "mower initial position");
        assertThat(actualException.getMessage()).isEqualTo(expectedInvalidMowerInitialPositionInputMessage);
    }

    @Test
    public void executeMowersJobs_whenMowerInstructionsInputIsNotValid_thenItFails() {
        //Given
        var invalidInstructionsMowerJobInputFilePath = "src/test/resources/invalid-instructions-input-mower-job.txt";

        //Then
        var actualException = assertThrows(InvalidMowerJobFileInputException.class, () -> MowerJobService.executeMowersJobs(invalidInstructionsMowerJobInputFilePath));
        String expectedInvalidInstructionsInputMessage = format(INVALID_MOWER_JOB_FILE_INPUT, "instructions");
        assertThat(actualException.getMessage()).isEqualTo(expectedInvalidInstructionsInputMessage);
    }
}
