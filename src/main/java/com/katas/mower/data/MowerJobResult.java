package com.katas.mower.data;

import java.util.List;

public class MowerJobResult {

    private FieldDimensions fieldDimensions;

    private List<MowerPosition> mowersFinalPositions;

    private List<String> failedJobsMessages;

    public FieldDimensions getFieldDimensions() {
        return fieldDimensions;
    }

    public void setFieldDimensions(FieldDimensions fieldDimensions) {
        this.fieldDimensions = fieldDimensions;
    }

    public List<MowerPosition> getMowersFinalPositions() {
        return mowersFinalPositions;
    }

    public void setMowersFinalPositions(List<MowerPosition> mowersFinalPositions) {
        this.mowersFinalPositions = mowersFinalPositions;
    }

    public List<String> getFailedJobsMessages() {
        return failedJobsMessages;
    }

    public void setFailedJobsMessages(List<String> failedJobsMessages) {
        this.failedJobsMessages = failedJobsMessages;
    }
}
