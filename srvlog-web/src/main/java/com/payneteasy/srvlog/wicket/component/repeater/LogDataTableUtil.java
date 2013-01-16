package com.payneteasy.srvlog.wicket.component.repeater;

import com.payneteasy.srvlog.data.LogLevel;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * Date: 15.01.13 Time: 17:38
 */
public class LogDataTableUtil {

    public static void setHighlightCssClass(String logLevel, ListItem listItem) {
        switch (LogLevel.valueOf(logLevel)) {
            case WARN:
                listItem.add(new AttributeAppender("class", new AbstractReadOnlyModel<Object>() {
                    @Override
                    public Object getObject() {
                        return "warning";
                    }
                }));
                break;
            case ERROR:
                listItem.add(new AttributeAppender("class", new AbstractReadOnlyModel<Object>() {
                    @Override
                    public Object getObject() {
                        return "error";
                    }
                }));
                break;
            case EMERGENCY:
                listItem.add(new AttributeAppender("class", new AbstractReadOnlyModel<Object>() {
                    @Override
                    public Object getObject() {
                        return "main-emergency";
                    }
                }));
                break;
            case CRITICAL:
                listItem.add(new AttributeAppender("class", new AbstractReadOnlyModel<Object>() {
                    @Override
                    public Object getObject() {
                        return "main-critical";
                    }
                }));
                break;
            case ALERT:
                listItem.add(new AttributeAppender("class", new AbstractReadOnlyModel<Object>() {
                    @Override
                    public Object getObject() {
                        return "warning";
                    }
                }));
                break;
            case INFO:
                listItem.add(new AttributeAppender("class", new AbstractReadOnlyModel<Object>() {
                    @Override
                    public Object getObject() {
                        return "info";
                    }
                }));
                break;

        }
    }
}