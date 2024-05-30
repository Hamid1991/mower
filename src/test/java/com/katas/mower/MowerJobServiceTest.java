package com.katas.mower;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.katas.mower.MowerJobService.FILE_NOT_FOUND_MESSAGE;
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
        String multipleMowersJobFilePath = "src/test/resources/multiple-mowers-job-file.txt";

        //When
        MowerJobResult actualMowerJobResult = MowerJobService.executeMowersJobs(multipleMowersJobFilePath);
        FieldDimensions expectedFieldDimensions = new FieldDimensions(5, 5);
        List<MowerPosition> expectedMowersFinalPositions = new ArrayList<>();
        expectedMowersFinalPositions.add(new MowerPosition(1, 3, Orientation.N));
        expectedMowersFinalPositions.add(new MowerPosition(5, 1, Orientation.E));

        //Then
        FieldDimensions actualFieldDimensions = actualMowerJobResult.getFieldDimensions();
        List<MowerPosition> actualMowersFinalPositions = actualMowerJobResult.getMowersFinalPositions();

        assertThat(actualFieldDimensions).usingRecursiveComparison().isEqualTo(expectedFieldDimensions);
        assertThat(actualMowersFinalPositions).usingRecursiveComparison().isEqualTo(expectedMowersFinalPositions);
    }

    @Test
    public void executeMowersJobs_whenMowerJobFileDontExists_thenItFails() {
        //Given
        String noMowerJobFilePath = "src/test/resources/no-mower-job-file.txt";

        //Then
        Exception actualException = assertThrows(Exception.class, () -> MowerJobService.executeMowersJobs(noMowerJobFilePath));
        assertThat(actualException.getMessage()).isEqualTo(FILE_NOT_FOUND_MESSAGE);
    }
}
