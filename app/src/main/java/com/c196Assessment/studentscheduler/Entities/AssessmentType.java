package com.c196Assessment.studentscheduler.Entities;

import androidx.annotation.NonNull;

//Enumerated class as there are only two types of assessments.

public enum AssessmentType {
    OBJECTIVE{
        @NonNull
        @Override
        public String toString() {
            return "Objective assessment";
        }
    },
    PERFORMANCE{
        @NonNull
        @Override
        public String toString() {
            return "Performance assessment";
        }
    }
}
