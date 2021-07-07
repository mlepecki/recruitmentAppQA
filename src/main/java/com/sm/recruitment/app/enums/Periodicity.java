package com.sm.recruitment.app.enums;

import java.time.Period;
import java.time.temporal.ChronoUnit;

public enum Periodicity {
    DAILY(ChronoUnit.DAYS),
    WEEKLY(ChronoUnit.WEEKS),
    MONTHLY(ChronoUnit.DAYS);

    Periodicity(ChronoUnit chronoUnit) {}
}
