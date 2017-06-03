package com.kevoroid.andhackernews;

import com.kevoroid.andhackernews.helpers.TimeHelper;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kevin on 6/3/17.
 */

public class HelpersUnitText {

    @Test
    public void returnActualDateTester() {
        TimeHelper timeHelper = new TimeHelper();
        String date = timeHelper.returnActualDateNonStatic("1496469527");
        Assert.assertEquals("06/03/2017", date);
    }
}
