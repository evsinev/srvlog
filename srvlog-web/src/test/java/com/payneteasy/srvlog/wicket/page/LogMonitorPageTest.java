package com.payneteasy.srvlog.wicket.page;

import com.payneteasy.srvlog.data.HostData;
import com.payneteasy.srvlog.data.LogData;
import com.payneteasy.srvlog.data.LogFacility;
import com.payneteasy.srvlog.data.LogLevel;
import com.payneteasy.srvlog.service.ILogCollector;
import com.payneteasy.srvlog.service.IndexerServiceException;
import com.payneteasy.srvlog.util.DateRange;
import junit.framework.Assert;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Date: 14.01.13 Time: 21:18
 */
public class LogMonitorPageTest extends AbstractWicketTester {
    private ILogCollector logCollector;

    @Override
    protected void setupTest() {
        logCollector = EasyMock.createMock(ILogCollector.class);
        addBean("logCollector", logCollector);
    }

    @Test
    public void testDateRangeFilter() throws IndexerServiceException {
        WicketTester wicketTester = getWicketTester();

        DateRange today = DateRange.today();
        List<LogData> searchLogData = getTestLogData();
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(searchLogData);
        EasyMock.expect(logCollector.loadHosts()).andReturn(new ArrayList<HostData>());
        DateRange thisMonth = DateRange.thisMonth();
        EasyMock.expect(logCollector.search(thisMonth.getFromDate(), thisMonth.getToDate(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Integer>(), null, 0, 26)).andReturn(searchLogData);
        EasyMock.replay(logCollector);

        wicketTester.startPage(LogMonitorPage.class);

        //TODO add check for default load page

        FormTester formTester = wicketTester.newFormTester("form");
        formTester.select("date-range-type", 4);

        formTester.submit("search-button");

        EasyMock.verify(logCollector);
    }

    @Ignore
    @Test
    public void testPager() throws IndexerServiceException {
        WicketTester wicketTester = getWicketTester();
        DateRange today = DateRange.today();
        List<LogData> testLogData = getTestLogData();
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(testLogData);
        EasyMock.expect(logCollector.loadHosts()).andReturn(new ArrayList<HostData>());
        // this call is necessary to expect because wicketTester checks for component visibility
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(testLogData);
//        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 25, 26)).andReturn(testLogData);
//        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(getTestLogData);
//        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 50, 26)).andReturn(getTestLogData);
//        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(getTestLogData);
        EasyMock.replay(logCollector);
        wicketTester.startPage(LogMonitorPage.class);
        Component component = wicketTester.getComponentFromLastRenderedPage("table-logs:pager:paging-next", false);
        wicketTester.clickLink(component);

        //wicketTester.clickLink("table-logs:pager:paging-next");
//        wicketTester.clickLink("table-logs:pager:paging-next");
//        wicketTester.clickLink("table-logs:pager:paging-first");
        EasyMock.verify(logCollector);
    }

    @Test
    public void testDefaultParameter() throws IndexerServiceException {
        WicketTester wicketTester = getWicketTester();
        DateRange today = DateRange.today();

        List<LogData> searchLogData = getTestLogData();
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(searchLogData);
        EasyMock.expect(logCollector.loadHosts()).andReturn(new ArrayList<HostData>());
        EasyMock.replay(logCollector);

        wicketTester.startPage(LogMonitorPage.class);

        EasyMock.verify(logCollector);

    }

    @Test
    public void testIndexingExceptionOccured() throws IndexerServiceException {
        WicketTester wicketTester = getWicketTester();
        DateRange today = DateRange.today();
        IndexerServiceException indexerServiceException = new IndexerServiceException("While calling indexing service", new Exception());
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andThrow(indexerServiceException);
        EasyMock.expect(logCollector.loadHosts()).andReturn(new ArrayList<HostData>());
        EasyMock.replay(logCollector);
        wicketTester.startPage(LogMonitorPage.class);
        wicketTester.assertComponent("feedback-panel", FeedbackPanel.class);
        wicketTester.assertErrorMessages("Error while retrieving log data");
        EasyMock.verify(logCollector);

    }

    @Test
    public void testListMultipleSearch() throws IndexerServiceException {
        WicketTester wicketTester = getWicketTester();

        DateRange today = DateRange.today();
        List<LogData> searchLogData = getTestLogData();
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), null, null, null, null, 0, 26)).andReturn(searchLogData);
        EasyMock.expect(logCollector.loadHosts()).andReturn(new ArrayList<HostData>());
        EasyMock.expect(logCollector.search(today.getFromDate(), today.getToDate(), Arrays.asList(LogFacility.kern.getValue(), LogFacility.user.getValue()), Arrays.asList(LogLevel.EMERGENCY.getValue()), new ArrayList<Integer>(), null, 0, 26)).andReturn(searchLogData);
        EasyMock.replay(logCollector);

        wicketTester.startPage(LogMonitorPage.class);
        wicketTester.assertRenderedPage(LogMonitorPage.class);

        ListMultipleChoice<LogLevel> severityListMultipleChoice = (ListMultipleChoice<LogLevel>) wicketTester.getComponentFromLastRenderedPage("form:severity-choice");
        Assert.assertEquals(LogLevel.values().length, severityListMultipleChoice.getChoices().size());

        ListMultipleChoice<LogLevel> facilityListMultipleChoice = (ListMultipleChoice<LogLevel>) wicketTester.getComponentFromLastRenderedPage("form:facility-choice");
        Assert.assertEquals(LogFacility.values().length, facilityListMultipleChoice.getChoices().size());

        FormTester formTester = wicketTester.newFormTester("form");
        formTester.select("severity-choice", 0);

        formTester.select("facility-choice", 0);
        formTester.select("facility-choice", 1);

        formTester.submit("search-button");

        EasyMock.verify(logCollector);

    }

    private List<LogData> getTestLogData() {
        ArrayList<LogData> listData = new ArrayList<LogData>();
        for (int i = 1; i <=30; i++) {
            LogData logData = new LogData();
            logData.setSeverity(1);
            logData.setFacility(1);
            logData.setHost("localhost");
            logData.setDate(new Date());
            logData.setId(Long.valueOf(i));
            listData.add(logData);
        }
        return listData;
    }
}