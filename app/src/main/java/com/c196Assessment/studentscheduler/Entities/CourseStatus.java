package com.c196Assessment.studentscheduler.Entities;

import androidx.annotation.NonNull;

//Enumerated class as there are only four types of course status's.

public enum CourseStatus {
    IN_PROGRESS{
        @NonNull
        @Override
        public String toString() {
            return "In progress";
        }
    },
    COMPLETED{
        @NonNull
        @Override
        public String toString() {
            return "Completed";
        }
    },
    DROPPED{
        @NonNull
        @Override
        public String toString() {
            return "Dropped";
        }
    },
    TO_TAKE{
        @NonNull
        @Override
        public String toString() {
            return "To take";
        }
    }

}
