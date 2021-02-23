package com.learnjava.parallelstream;

import com.learnjava.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue, boolean isParallel) {
        CommonUtil.startTimer();
        Stream<Integer> integerStream = inputList.stream(); //sequential
        if (isParallel) {
            integerStream = integerStream.parallel();
        }
        List<Integer> resultList = integerStream
                .map(integer -> integer * multiplyValue)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();
        return resultList;
    }
}
