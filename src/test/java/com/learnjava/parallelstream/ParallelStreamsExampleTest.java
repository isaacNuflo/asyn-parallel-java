package com.learnjava.parallelstream;

import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform() {
        List<String> namesList = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.stringTransform(namesList);
        CommonUtil.timeTaken();

        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    public void stringTransform_1(boolean isParallel) {
        List<String> namesList = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.stringTransform_1(namesList, isParallel);
        CommonUtil.timeTaken();

        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    public void namesToLowerCase(boolean isParallel) {
        List<String> namesList = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.namesToLowerCase(namesList, isParallel);
        CommonUtil.timeTaken();

        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(StringUtils.isAllLowerCase(name)));
    }
}
