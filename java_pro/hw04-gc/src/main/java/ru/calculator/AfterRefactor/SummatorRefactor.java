package ru.calculator.AfterRefactor;

import java.util.ArrayList;
import java.util.List;

public class SummatorRefactor {
    private int sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;
    private final List<DataRefactor> listValues = new ArrayList<>();

    //!!! сигнатуру метода менять нельзя
    public void calc(DataRefactor data) {
        listValues.add(data);
        if (listValues.size() % 6_600_000 == 0) {
            listValues.clear();
        }
        final int tmpData = data.getValue();
        sum += tmpData;

        sumLastThreeValues = tmpData + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = tmpData;

        for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (tmpData + 1) - sum);
            someValue = Math.abs(someValue) + listValues.size();
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}
